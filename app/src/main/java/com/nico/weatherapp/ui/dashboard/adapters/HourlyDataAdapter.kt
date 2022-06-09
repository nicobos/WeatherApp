package com.nico.weatherapp.ui.dashboard.adapters

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nico.weatherapp.R
import com.nico.weatherapp.common.extensions.inflateView
import com.nico.weatherapp.common.utils.DateTimeUtils
import com.nico.weatherapp.ui.models.HourlyDataItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hourly_weather_list_item_layout.view.*

class HourlyDataAdapter() : RecyclerView.Adapter<HourlyDataAdapter.HourlyDataViewHolder>() {

    val data: MutableList<HourlyDataItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyDataViewHolder {
        return HourlyDataViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: HourlyDataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<HourlyDataItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class HourlyDataViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: HourlyDataItem) {
            containerView.time.text = DateTimeUtils.parseUnixTime(data.time, data.timezone)
            containerView.temperature.text = containerView.context.getString(R.string.temperature,  data.temperature)
            val url = containerView.context.getString(R.string.icon_url, data.icon)
            containerView.icon.load(url) {
                size(containerView.context.resources.getDimensionPixelSize(R.dimen.weather_icon_size_small))
            }
            containerView.rain.text =
                containerView.context.getString(R.string.precipitation, data.precipitation?.times(100) ?: 0.0)
        }

        companion object {
            fun inflate(
                parent: ViewGroup,
                @LayoutRes layoutRes: Int = R.layout.hourly_weather_list_item_layout
            ): HourlyDataViewHolder {
                return HourlyDataViewHolder(inflateView(layoutRes, parent, false))
            }
        }

    }

}