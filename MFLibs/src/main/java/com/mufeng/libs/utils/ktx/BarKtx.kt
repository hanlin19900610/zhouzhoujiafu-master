package com.mufeng.libs.utils.ktx

import android.Manifest.permission
import android.app.Activity
import android.view.View
import android.view.Window
import androidx.annotation.ColorInt
import androidx.annotation.RequiresPermission
import com.blankj.utilcode.util.BarUtils

// 状态栏高度
val statusBarHeight
    get() = BarUtils.getStatusBarHeight()

// 判断状态栏是否可见
val Activity.isStatusBarVisible
    get() = BarUtils.isStatusBarVisible(this)

// 判断状态栏是否是黑暗模式
val Activity.isStatusBarLightMode
    get() = BarUtils.isStatusBarLightMode(this)

// Action bar 高度
val actionBarHeight
    get() = BarUtils.getActionBarHeight()

// 导航栏高度
val navBarHeight
    get() = BarUtils.getNavBarHeight()

// 判断底部导航栏是否显示
val Activity.isNavBarVisible
    get() = BarUtils.isNavBarVisible(this)

// 设置状态栏的可见性
fun Activity.setStatusBarVisibility(isVisible: Boolean = false) =
    BarUtils.setStatusBarVisibility(this, isVisible)

// 设置状态栏的黑暗模式
fun Activity.setStatusBarLightMode(isLightMode: Boolean = false) =
    BarUtils.setStatusBarLightMode(this, isLightMode)

// 给View添加一个状态栏高度一样的MarginTop
fun View.addMarginTopEqualStatusBarHeight() =
    BarUtils.addMarginTopEqualStatusBarHeight(this)

//
fun View.subtractMarginTopEqualStatusBarHeight() =
    BarUtils.subtractMarginTopEqualStatusBarHeight(this)

// 给状态栏设置颜色
fun Activity.setStatusBarColor(@ColorInt color: Int, isDecor: Boolean = false) =
    BarUtils.setStatusBarColor(this, color, isDecor)
fun Window.setStatusBarColor(@ColorInt color: Int, isDecor: Boolean = false) =
    BarUtils.setStatusBarColor(this, color, isDecor)
fun View.setStatusBarColor(@ColorInt color: Int, isDecor: Boolean = false) =
    BarUtils.setStatusBarColor(this, color)

// 透明状态栏
fun Activity.transparentStatusBar() = BarUtils.transparentStatusBar(this)

// 设置通知栏是否显示
@RequiresPermission(permission.EXPAND_STATUS_BAR)
fun setNotificationBarVisibility(isVisible: Boolean) = BarUtils.setNotificationBarVisibility(isVisible)

// 设置底部导航栏是否显示
fun Activity.setNavBarVisibility(isVisible: Boolean) = BarUtils.setNavBarVisibility(this,isVisible)

// 设置底部导航栏颜色
fun Activity.setNavBarColor(@ColorInt color: Int) = BarUtils.setNavBarColor(this, color)
fun Window.setNavBarColor(@ColorInt color: Int) = BarUtils.setNavBarColor(this, color)

// 导航栏是否可见
val isSupportNavBar
    get() = BarUtils.isSupportNavBar()

// 设置导航栏模式
fun Activity.setNavBarLightMode(isLightMode: Boolean) = BarUtils.setNavBarLightMode(this, isLightMode)

val Activity.isNavBarLightMode
    get() = BarUtils.isNavBarLightMode(this)