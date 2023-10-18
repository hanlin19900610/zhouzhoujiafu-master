package com.mufeng.libs.utils.ktx

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Description: 时间日期相关
 * Create by lxj, at 2018/12/7
 */

/**
 *  字符串日期格式（比如：2018-4-6)转为毫秒
 *  @param format 时间的格式，默认是按照yyyy-MM-dd HH:mm:ss来转换，如果您的格式不一样，则需要传入对应的格式
 */
fun String.toDateMills(format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.getDefault()).parse(this).time


/**
 * Long类型时间戳转为字符串的日期格式
 * @param format 时间的格式，默认是按照yyyy-MM-dd HH:mm:ss来转换，如果您的格式不一样，则需要传入对应的格式
 */
fun Long.toDateString(format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.getDefault()).format(Date(this))

fun Int.toDateString(format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.getDefault()).format(Date(this.toLong()))

@SuppressLint("SimpleDateFormat")
fun Date.toDateForm(pattern: String = "yyyy-MM-dd HH:mm:ss"): String{
    val dateFormat = SimpleDateFormat(pattern)
    return dateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.toDateForm(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date?{
    val dateFormat = SimpleDateFormat(pattern)
    return dateFormat.parse(this)
}

/**
 * 根据日期计算年龄
 */
val Date.age: Int
    get() {
        val currentTime = Date().time
        val difference = currentTime - time
        val year: Long = 1000 * 60 * 60 * 24 * 365L
        return (difference / year).toInt()
    }