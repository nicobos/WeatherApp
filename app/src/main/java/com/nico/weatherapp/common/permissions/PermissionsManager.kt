package com.nico.weatherapp.common.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PermissionsManager @Inject constructor(
    @ActivityContext val context: Context
) : IPermissionsManager {

    override fun hasLocationPermissionGranted(
        permissionResult: (result: Boolean, showRationale: Boolean) -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val showMessage =
                (context as Activity).shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) || context.shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

            permissionResult(false, showMessage)
        } else {
            permissionResult(true, false)
        }
    }

}