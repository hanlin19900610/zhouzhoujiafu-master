package com.mufeng.libs.utils.ktx

import android.text.Html
import android.text.Spanned
import java.util.regex.Pattern

/**
 * 是否为手机号  0开头 12开头的不支持
 */
fun String?.isPhone(): Boolean {
    return this?.let {
        Pattern.matches(it, "0?(13|14|15|16|17|18|19)[0-9]{9}")
    }?:let {
        false
    }
}

/**
 * 是否为座机号
 */
fun String?.isTel(): Boolean {
    return this?.let {
        val matcher1 = Pattern.matches("^0(10|2[0-5|789]|[3-9]\\d{2})\\d{7,8}\$", this)
        val matcher2 = Pattern.matches("^0(10|2[0-5|789]|[3-9]\\d{2})-\\d{7,8}$", this)
        val matcher3 = Pattern.matches("^400\\d{7,8}$", this)
        val matcher4 = Pattern.matches("^400-\\d{7,8}$", this)
        val matcher5 = Pattern.matches("^800\\d{7,8}$", this)
        val matcher6 = Pattern.matches("^800-\\d{7,8}$", this)
        return matcher1 || matcher2 || matcher3 || matcher4 || matcher5 || matcher6
    }?:let {
        false
    }
}

/**
 * 是否为邮箱号
 */
fun String?.isEmail(): Boolean {
    return this?.let {
        Pattern.matches(this, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$")
    }?:let {
        false
    }
}

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}


/**
 * 解析url的查询参数
 */
fun String.parseQueryParams(): Map<String, String>{
    var index = lastIndexOf("?") + 1
    var queryParam = hashMapOf<String, String>()
    if(index>0){
        var query = substring(index, length)
        query.split("&").forEach {
            if(it.contains("=")){
                var arr = it.split("=")
                if(arr.size>1){
                    queryParam[arr[0]] = arr[1]
                }
            }
        }
    }
    return queryParam
}