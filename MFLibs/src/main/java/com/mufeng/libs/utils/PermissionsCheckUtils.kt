package com.mufeng.libs.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import androidx.core.app.NotificationManagerCompat

object PermissionsCheckUtils {

    /**
     * 判断是否开启定位服务
     * @return Boolean
     */
    fun locationEnable(): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnable = false
        var networkEnable = false
        gpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkEnable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gpsEnable || networkEnable
    }

    /**
     * 判断是否开启通知
     * @return Boolean
     */
    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun jumpNotificationsSettings(context: Context) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.fromParts("package", context.packageName, null);
        context.startActivity(intent)
    }

}