package com.mufeng.social.utils

import android.content.Context
import android.content.pm.PackageManager


object AppUtils {

    /**
     * 判断应用是否安装
     */
    fun isAppInstalled(name: String, context: Context): Boolean {
        val manager = context.packageManager
        return try {
            val applicationInfo =
                manager.getPackageInfo(name, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) ?: null
            applicationInfo != null
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }
}