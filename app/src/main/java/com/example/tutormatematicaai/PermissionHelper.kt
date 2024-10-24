package com.example.tutormatematicaai

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {
  fun checkAndRequestPermission(
    activity: Activity,
    permission: String,
    requestCode: Int
  ): Boolean {
    return if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
      false
    } else {
      true
    }
  }
}