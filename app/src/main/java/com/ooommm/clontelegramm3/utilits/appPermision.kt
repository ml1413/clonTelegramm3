package com.ooommm.clontelegramm3.utilits

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permission

const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
const val PERMISSION_CODE = 200

fun checkPermission(permission: String): Boolean {
    return if (
        Build.VERSION.SDK_INT >= 29
        &&
        ContextCompat.checkSelfPermission(APP_ACTIVITY, permission)
        !=
        PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), PERMISSION_CODE)
        false
    } else true
}