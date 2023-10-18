package com.mufeng.libs.utils.ktx

import com.blankj.utilcode.util.AppUtils
import com.mufeng.libs.utils.context

// 安装
fun installApp(path: String) = AppUtils.installApp(path)

// 卸载
fun uninstallApp(packageName: String) = AppUtils.uninstallApp(packageName)

// 判断App是否安装
fun isAppInstalled(pkg: String) = AppUtils.isAppInstalled(pkg)

// 判断App是否有root权限
fun isAppRoot() = AppUtils.isAppRoot()

// 判断App是否处于Debug模式
fun isAppDebug(pkg: String = context.packageName) = AppUtils.isAppDebug(pkg)

// 判断App是否是系统应用
fun isAppSystem(pkg: String = context.packageName) = AppUtils.isAppSystem(pkg)

// 判断App是否处于前台
fun isAppForeground(pkg: String = context.packageName) = AppUtils.isAppForeground(pkg)

// 判断App是否在运行
fun isAppRunning(pkg: String = context.packageName) = AppUtils.isAppRunning(pkg)

// 获取应用图标
fun getAppIcon(pkg: String = context.packageName) = AppUtils.getAppIcon(pkg)

// 获取应用名称
fun getAppName(pkg: String = context.packageName) = AppUtils.getAppName(pkg)

fun getAppVersionName(pkg: String = context.packageName) = AppUtils.getAppVersionName(pkg)
fun getAppVersionCode(pkg: String = context.packageName) = AppUtils.getAppVersionCode(pkg)
fun getAppSignatures(pkg: String = context.packageName) = AppUtils.getAppSignatures(pkg)
fun getAppUid(pkg: String = context.packageName) = AppUtils.getAppUid(pkg)

fun getAppInfo(pkg: String = context.packageName) = AppUtils.getAppInfo(pkg)
