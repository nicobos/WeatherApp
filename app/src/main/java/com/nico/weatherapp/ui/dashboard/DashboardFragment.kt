package com.nico.weatherapp.ui.dashboard

import android.Manifest
import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nico.weatherapp.R
import com.nico.weatherapp.common.extensions.doIfError
import com.nico.weatherapp.common.extensions.doIfSuccess
import com.nico.weatherapp.common.permissions.PermissionsManager
import com.nico.weatherapp.databinding.FragmentDashboardBinding
import com.nico.weatherapp.ui.dashboard.adapters.GeoCodeAdapter
import com.nico.weatherapp.ui.dashboard.adapters.HourlyDataAdapter
import com.nico.weatherapp.ui.dialogs.IDialogManager
import dagger.hilt.android.AndroidEntryPoint
import com.nico.weatherapp.ui.models.WeatherRanges
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var permissionsManager: PermissionsManager

    @Inject
    lateinit var dialogManager: IDialogManager

    private val dashBoardViewModel by viewModels<DashBoardViewModel>()

    private lateinit var geoCodeAdapter: GeoCodeAdapter
    private lateinit var hourlyDataAdapter: HourlyDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        hourlyDataAdapter = HourlyDataAdapter()
        binding.hourlyWeatherList.adapter = hourlyDataAdapter

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLastLocation()

        dashBoardViewModel.currentLocation.observe(viewLifecycleOwner) {
            binding.location.text = it.name
        }

        dashBoardViewModel.showLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.loading.show()
                    binding.content.animate().alpha(0f)
                }
                false -> {
                    binding.loading.hide()
                    binding.content.animate().alpha(1f)
                }
            }
        }

        dashBoardViewModel.weatherData.observe(viewLifecycleOwner) { result ->

            result.doIfSuccess {
                binding.temperature.text = getString(R.string.temperature, it?.temperature)
                binding.description.text = it?.description
                binding.weatherIcon.setImageResource(
                    WeatherRanges.getImageResource(it?.weatherConditionId)
                )
                it?.let {
                    hourlyDataAdapter.updateData(it.hourlyData)
                }

                binding.sunrise.text = it?.sunrise
                binding.sunset.text = it?.sunset
            }

            result.doIfError { error, _ ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val view = menu.findItem(R.id.app_bar_search).actionView
        val searchBar =
            view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_searchWidget)

        geoCodeAdapter = GeoCodeAdapter(
            requireContext(),
            dashBoardViewModel.getGeoCodeRepo(),
            android.R.layout.simple_expandable_list_item_2
        ) {
            dashBoardViewModel.setCurrentLocation(it)
            searchBar.dismissDropDown()
        }
        searchBar.setAdapter(geoCodeAdapter)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getLastLocation() {
        permissionsManager.hasLocationPermissionGranted { result, showRationale ->
            when (result) {
                true -> {
                    dashBoardViewModel.setWeatherDataToCurrentLocation()
                }
                false -> {
                    checkPermissions()
                }
            }

            if (showRationale) {
                dialogManager.showDialogPermissionRationale { }
            }

        }
    }

    private fun checkPermissions() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getLastLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getLastLocation()
                }
                else -> {

                }
            }
        }

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}