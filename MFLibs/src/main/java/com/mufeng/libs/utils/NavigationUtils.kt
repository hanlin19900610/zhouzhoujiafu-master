package com.mufeng.libs.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.itxca.msa.IMsa
import com.mufeng.libs.utils.ktx.*

/**
 * Jump to the app info page
 */
fun Context.goToAppInfoPage(packageName: String = this.packageName) {
    startActivity(getAppInfoIntent(packageName))
}

/**
 * Jump to the data and time page
 */
fun Context.goToDateAndTimePage() {
    startActivity(getDateAndTimeIntent())
}

/**
 * Jump to the language page
 */
fun Context.goToLanguagePage() {
    startActivity(getLanguageIntent())
}

/**
 * Jump to the accessibility page
 */
fun Context.goToAccessibilitySetting() =
    Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).run { startActivity(this) }


fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.newTask()
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * Visit app in app store
 * @param packageName default value is current app
 */
fun Context.openInAppStore(packageName: String = this.packageName) {
    val intent = Intent(Intent.ACTION_VIEW)
    try {
        intent.data = Uri.parse("market://details?id=$packageName")
        startActivity(intent)
    } catch (ifPlayStoreNotInstalled: ActivityNotFoundException) {
        intent.data =
            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        startActivity(intent)
    }
}

/**
 * Open app by [packageName]
 */
fun Context.openApp(packageName: String) =
    packageManager.getLaunchIntentForPackage(packageName)?.run { startActivity(this) }


/**
 * Send email
 * @param email the email address be sent to
 * @param subject a constant string holding the desired subject line of a message, @see [Intent.EXTRA_SUBJECT]
 * @param text a constant CharSequence that is associated with the Intent, @see [Intent.EXTRA_TEXT]
 */
fun Context.sendEmail(email: String, subject: String?, text: String?) {
    Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")).run {
        subject?.let { putExtra(Intent.EXTRA_SUBJECT, subject) }
        text?.let { putExtra(Intent.EXTRA_TEXT, text) }
        startActivity(this)
    }
}

fun Context.startHomeActivity(){
    startActivity(Intent().apply {
        action = Intent.ACTION_MAIN
        addCategory(Intent.CATEGORY_HOME)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    })
}

// 打开一个App
fun launchApp(pkg: String = context.packageName) = AppUtils.launchApp(pkg)

// 重启
fun relaunchApp(isKillProcess: Boolean = false) = AppUtils.relaunchApp(isKillProcess)

// 打开App设置页
fun launchAppDetailsSettings(pkg: String = context.packageName) = AppUtils.launchAppDetailsSettings(pkg)

// 退出App
fun exitApp() = AppUtils.exitApp()

/**
 * 界面跳转
 * @receiver Context
 * @param params Array<out Pair<String, Any?>>
 */

inline fun <reified T : AppCompatActivity> Fragment.startPage(vararg params: Pair<String, Any?>) {
    val intent = Intent(this.activity, T::class.java).fillIntentArguments(*params)
    if (this !is AppCompatActivity) {
        intent.newTask()
    }
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Context.startPage(vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java).fillIntentArguments(*params)
    if (this !is AppCompatActivity) {
        intent.newTask()
    }
    startActivity(intent)
}

fun startPage(pkg: String, cls: String, vararg params: Pair<String, Any?>){
    ActivityUtils.startActivity(pkg, cls, Bundle().putExtras(*params))
}


inline fun <reified T : AppCompatActivity> IMsa.startPageForResult(
    vararg params: Pair<String, Any?>,
    crossinline onResult: (code: Int, data: Intent?) -> Unit
) {

    T::class.startForResult({
        fillIntentArguments(*params)
    }) { code, data ->
        onResult(code, data)
    }
}

/** 结束界面 **/
/**
 *  作用同[Activity.finish]
 *  示例：
 *  <pre>
 *      ActivityMessenger.finish(this, "Key" to "Value")
 *  </pre>
 *
 * @param src 发起的Activity
 * @param params extras键值对
 */
fun Activity.finishPage(code: Int = Activity.RESULT_OK, vararg params: Pair<String, Any>) =
    with(this) {
        setResult(code, Intent().fillIntentArguments(*params))
        finish()
    }

/**
 *  Fragment调用，作用同[Activity.finish]
 *  示例：
 *  <pre>
 *      finishPage("Key" to "Value")
 *  </pre>
 *
 * @param src 发起的Fragment
 * @param params extras键值对
 */
fun Fragment.finishPage(code: Int = Activity.RESULT_OK, vararg params: Pair<String, Any>) =
    with(this.activity) {
        this?.finishPage(code, *params)
    }

fun Activity.finishToActivity(isIncludeSelf: Boolean, isLoadAnim: Boolean = false){
    ActivityUtils.finishToActivity(this,isIncludeSelf,isLoadAnim)
}

fun<T : AppCompatActivity> finishOtherActivities(clz: Class<T>, isLoadAnim: Boolean = false){
    ActivityUtils.finishOtherActivities(clz,isLoadAnim)
}

fun finishAllActivities(isLoadAnim: Boolean = false) = ActivityUtils.finishAllActivities(isLoadAnim)

// 结束除最新活动外的所有活动
fun finishAllActivitiesExceptNewest(isLoadAnim: Boolean = false) = ActivityUtils.finishAllActivitiesExceptNewest(isLoadAnim)


