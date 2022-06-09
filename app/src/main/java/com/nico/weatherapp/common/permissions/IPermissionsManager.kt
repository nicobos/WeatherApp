package com.nico.weatherapp.common.permissions

interface IPermissionsManager {

    fun hasLocationPermissionGranted(permissionResult: (result: Boolean, showRationale: Boolean) -> Unit)

}