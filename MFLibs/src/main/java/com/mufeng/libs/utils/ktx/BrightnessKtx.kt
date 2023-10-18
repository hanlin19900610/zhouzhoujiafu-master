package com.mufeng.libs.utils.ktx

import android.view.Window
import com.blankj.utilcode.util.BrightnessUtils

/**
 * 亮度工具
 */

// 自动亮度是否开启
val isAutoBrightnessEnabled
    get() = BrightnessUtils.isAutoBrightnessEnabled()

/**
 * 设置自动亮度是否开启
 *  <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 * @param enabled Boolean
 * @return Boolean
 */
fun setAutoBrightnessEnabled(enabled: Boolean) = BrightnessUtils.setAutoBrightnessEnabled(enabled)

// 屏幕亮度
// 屏幕亮度: 0-255
var brightness: Int
    set(value) {
        BrightnessUtils.setBrightness(value)
    }
    get() = BrightnessUtils.getBrightness()

// 窗口亮度
var Window.windowBrightness: Int
    set(value) {
        BrightnessUtils.setWindowBrightness(this,value)
    }
    get() = BrightnessUtils.getWindowBrightness(this)
