package com.katyrin.testronasit.utils

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.katyrin.testronasit.R

private const val REQUEST_LOCATION_CODE = 332

fun Fragment.checkLocationPermission(onPermissionGranted: () -> Unit): Unit =
    when {
        isLocationPermissionGranted() -> onPermissionGranted()
        shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> showRationaleDialog()
        else -> requestLocationPermission()
    }

private fun Fragment.isLocationPermissionGranted(): Boolean = PackageManager.PERMISSION_GRANTED ==
        ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)

private fun Fragment.showRationaleDialog(): Unit =
    AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.access_to_location))
        .setMessage(getString(R.string.explanation_get_location))
        .setPositiveButton(getString(R.string.grant_access)) { _, _ -> requestLocationPermission() }
        .setNegativeButton(getString(R.string.do_not)) { dialog, _ -> dialog.dismiss() }
        .create()
        .show()

private fun Fragment.requestLocationPermission(): Unit =
    ActivityCompat.requestPermissions(
        requireActivity(),
        arrayOf(ACCESS_FINE_LOCATION),
        REQUEST_LOCATION_CODE
    )

fun Fragment.toast(message: String?): Unit =
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

fun Fragment.toast(resource: Int): Unit =
    Toast.makeText(requireContext(), resource, Toast.LENGTH_SHORT).show()