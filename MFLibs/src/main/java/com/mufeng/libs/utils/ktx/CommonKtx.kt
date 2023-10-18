package com.mufeng.libs.utils.ktx

import android.os.Build
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.TimeUtils
import com.mufeng.libs.utils.mainHandler
import java.util.*

/**
 * Description:  通用扩展
 */

/** dp和px转换 **/
inline val Float.dp
    get() = ConvertUtils.dp2px(this)
inline val Float.sp
    get() = ConvertUtils.sp2px(this)
inline val Float.pt
    get() = AdaptScreenUtils.pt2Px(this)
inline val Int.dp
    get() = ConvertUtils.dp2px(this.toFloat())
inline val Int.sp
    get() = ConvertUtils.sp2px(this.toFloat())
inline val Int.pt
    get() = AdaptScreenUtils.pt2Px(this.toFloat())

/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}

//一天只做一次
fun Any.doOnceInDay(actionName: String = "", action: () -> Unit, whenHasDone: (()->Unit)? = null) {
    val key = "once_in_day_last_check_${actionName}"
    val today = Date()
    val todayFormat = TimeUtils.date2String(today, "yyyy-MM-dd")
    val last = mmkv().getString(key, "")
    if (last != null && last.isNotEmpty() && last == todayFormat) {
        //说明执行过
        whenHasDone?.invoke()
        return
    }
    mmkv().putString(key, todayFormat)
    action()
}

//只执行一次的行为
fun Any.doOnlyOnce(actionName: String = "", action: () -> Unit, whenHasDone: (()->Unit)? = null) {
    val key = "has_done_${actionName}"
    val hasDone = mmkv().getBoolean(key, false)
    if (hasDone) {
        //说明执行过
        whenHasDone?.invoke()
        return
    }
    mmkv().putBoolean(key, true)
    action()
}

//500毫秒内只做一次
//val _innerHandler = Handler(Looper.getMainLooper())
val _actionCache = arrayListOf<String>()

/**
 * 事件节流，在指定时间内第一次事件有效
 * @param actionName 事件的名字
 * @param time 事件的节流时间
 * @param action 事件
 */
fun Any.doOnceIn( actionName: String, time: Long = 500, action: ()->Unit){
    if(_actionCache.contains(actionName)) return
    _actionCache.add(actionName)
    action() //执行行为
    mainHandler.postDelayed({
        if(_actionCache.contains(actionName)) _actionCache.remove(actionName)
    }, time)
}

fun fromM() = fromSpecificVersion(Build.VERSION_CODES.M)
fun beforeM() = beforeSpecificVersion(Build.VERSION_CODES.M)
fun fromN() = fromSpecificVersion(Build.VERSION_CODES.N)
fun beforeN() = beforeSpecificVersion(Build.VERSION_CODES.N)
fun fromO() = fromSpecificVersion(Build.VERSION_CODES.O)
fun beforeO() = beforeSpecificVersion(Build.VERSION_CODES.O)
fun fromP() = fromSpecificVersion(Build.VERSION_CODES.P)
fun beforeP() = beforeSpecificVersion(Build.VERSION_CODES.P)
fun fromSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT >= version
fun beforeSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT < version