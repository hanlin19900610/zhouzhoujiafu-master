package com.mufeng.libs.utils.ktx

import com.blankj.utilcode.util.DeviceUtils

val isDeviceRooted
    get() = DeviceUtils.isDeviceRooted()

val isAdbEnabled
    get() = DeviceUtils.isAdbEnabled()

val SDKVersionName
    get() = DeviceUtils.getSDKVersionName()

val SDKVersionCode
    get() = DeviceUtils.getSDKVersionCode()

// 是否是平板设备
val isTablet
    get() = DeviceUtils.isTablet()

// 是否为模拟器
val isEmulator
    get() = DeviceUtils.isEmulator()

// 是否开启了开发者模式
val isDevelopmentSettingsEnabled
    get() = DeviceUtils.isDevelopmentSettingsEnabled()