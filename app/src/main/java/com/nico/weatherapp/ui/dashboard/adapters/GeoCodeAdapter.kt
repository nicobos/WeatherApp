/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.nico.weatherapp.ui.dashboard.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse
import com.nico.weatherapp.ui.models.Location
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@SuppressLint("ResourceType")
class GeoCodeAdapter(
    context: Context,
    val geoCodeRepository: IGeoCodeRepo,
    val listItemResId: Int,
    val onItemClick: (Location) -> Unit
) : ArrayAdapter<GeoCodeResponse>(context, android.R.id.text1), Filterable {

    private var mResultList: List<GeoCodeResponse>? = null

    override fun getCount(): Int {
        return mResultList?.size ?: 0
    }

    override fun getItem(position: Int): GeoCodeResponse? {
        return mResultList!![position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        var holder: viewHolder? = null
        val item = getItem(position)

        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(listItemResId, parent, false) as View
            holder = viewHolder()
            holder.text1 = view.findViewById<View>(android.R.id.text1) as TextView
            holder.text2 = view.findViewById<View>(android.R.id.text2) as TextView
            view.tag = holder
        } else {
            holder = view.tag as viewHolder
        }

        val primary = item!!.name
        val secondary = item.country

        holder.text1?.text = primary
        holder.text2?.text = secondary

        view.setOnClickListener {
            onItemClick.invoke(Location(
                name = item.name,
                lat = item.lat,
                lon = item.lon
            ))
        }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()

                var filterData: List<GeoCodeResponse>? = ArrayList()

                if (constraint != null) {
                    filterData = getAutocomplete(constraint)
                }

                results.values = filterData
                if (filterData != null) {
                    results.count = filterData.size
                } else {
                    results.count = 0
                }

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    mResultList = results.values as List<GeoCodeResponse>
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any): CharSequence {
                return (resultValue as? GeoCodeResponse)?.getFullText()
                    ?: super.convertResultToString(resultValue)
            }
        }
    }

    private fun getAutocomplete(constraint: CharSequence): List<GeoCodeResponse>? {
        Timber.i("Starting autocomplete query for: %s", constraint)

        val results = mutableListOf<GeoCodeResponse>()

        runBlocking {
            val request = geoCodeRepository.geocodeLocationName(
                constraint.toString()
            )

            results.addAll(request)
        }

        return results
    }

    data class viewHolder(
        var text1: TextView? = null,
        var text2: TextView? = null
    )

}