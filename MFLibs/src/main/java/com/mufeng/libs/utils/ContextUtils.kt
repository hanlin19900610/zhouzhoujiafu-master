package com.mufeng.libs.utils

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils

private class GlobalContext(context: Context) : ContextWrapper(context)

private lateinit var sApplication: Application
private lateinit var sGlobalContext: GlobalContext
var mainHandler = Handler(Looper.getMainLooper())

fun initContext(application: Application) {
    if (!::sApplication.isInitialized) {
        sApplication = application
    }
    if (!::sGlobalContext.isInitialized) {
        sGlobalContext =
            GlobalContext(application.applicationContext)
    }
    Utils.init(application)
    DirManager.init()
}

val context: Context
    get() = sGlobalContext


val application: Application
    get() = sApplication

/**
 * 及时的、尽快的显示一个Toast，多次调用此方法的Toast会被后调用的覆盖
 * @param text String 要显示的文本
 */
fun toast(text: String) {
    ToastUtils.showShort(text)
}