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
import com.nico.weatherapp.databinding.FragmentDashboardBinding
import com.nico.weatherapp.ui.dashboard.adapters.GeoCodeAdapter
import dagger.hilt.android.AndroidEntryPoint
import weather.android.dvt.co.za.weather.WeatherInfo.DataModels.WeatherRanges


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val dashBoardViewModel by viewModels<DashBoardViewModel>()

    private val binding get() = _binding!!

    lateinit var geoCodeAdapter: GeoCodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        checkPermissions()

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashBoardViewModel.currentLocation.observe(viewLifecycleOwner) {
            _binding?.location?.text = it.name
        }

        dashBoardViewModel.weatherData.observe(viewLifecycleOwner) { result ->

            result.doIfSuccess {
                _binding?.temperature?.text = getString(R.string.temperature, it?.temperature)
                _binding?.description?.text = it?.description
                _binding?.weatherIcon?.setImageResource(
                    WeatherRanges.getImageResource(it?.weatherConditionId)
                )
            }

            result.doIfError { error, throwable ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val view = menu.findItem(R.id.app_bar_search).actionView
        val searchBar = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_searchWidget)

        geoCodeAdapter = GeoCodeAdapter(
            requireContext(),
            dashBoardViewModel.geoCodeRepo,
            android.R.layout.simple_expandable_list_item_2
        ){
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

    fun checkPermissions(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Toast.makeText(requireContext(), "FINE LOCATION GRANTED", Toast.LENGTH_SHORT).show()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Toast.makeText(requireContext(), "COURSE LOCATION GRANTED", Toast.LENGTH_SHORT).show()
                } else -> {
                Toast.makeText(requireContext(), "NO LOCATION GRANTED", Toast.LENGTH_SHORT).show()
            }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}