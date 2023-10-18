package com.mufeng.libs.utils.ktx

import android.app.Activity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.KeyboardUtils

fun Activity.hideSoftInput(){
    KeyboardUtils.hideSoftInput(this)
}

fun Fragment.hideSoftInput(){
    KeyboardUtils.hideSoftInput(activity)
}