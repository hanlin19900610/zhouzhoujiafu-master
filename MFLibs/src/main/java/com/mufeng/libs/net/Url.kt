package com.mufeng.libs.net

import com.mufeng.libs.MFLibs
import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

object Url {

    // 默认域名
    @JvmField
    @DefaultDomain()
    var baseUrl = MFLibs.httpConfig?.baseUrl

    /**
     * 随机网络图片地址
     * @param width Int
     * @param height Int
     * @param key String
     * @return String
     */
    fun randomImageUrl(width: Int = 100, height: Int = 100, key: String = "LNAndroidLibs"): String{
        return "http://placeimg.com/$width/$height/${key.hashCode().toString() + key.toString()}"
    }



}