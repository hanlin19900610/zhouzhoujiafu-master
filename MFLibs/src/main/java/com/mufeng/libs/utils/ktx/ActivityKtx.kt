package com.mufeng.libs.utils.ktx

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.ActivityUtils

val activityList = ActivityUtils.getActivityList()

val launchActivity = ActivityUtils.getLauncherActivity()

val topActivity: Activity
    get() = ActivityUtils.getTopActivity()

val Context.isActivityAlive
    get() = ActivityUtils.isActivityAlive(this)

val Activity.isActivityAlive
    get() = ActivityUtils.isActivityAlive(this)

val Activity.isActivityExistsInStack
    get() = ActivityUtils.isActivityExistsInStack(this)

/**
 * 根据Context获取Activity
 * @receiver Context?
 * @return Activity?
 */
fun Context?.getActivity() = ActivityUtils.getActivityByContext(this)

fun isActivityExists(pkg: String, cls: String) = ActivityUtils.isActivityExists(pkg, cls)

fun Activity.getActivityIcon(): Drawable? = ActivityUtils.getActivityIcon(this)
fun Activity.getActivityLogo(): Drawable? = ActivityUtils.getActivityLogo(this)

