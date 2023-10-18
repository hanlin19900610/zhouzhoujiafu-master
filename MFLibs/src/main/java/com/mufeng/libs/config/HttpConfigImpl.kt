package com.mufeng.libs.config

import com.mufeng.libs.net.interceptor.CacheInterceptor
import com.mufeng.libs.net.interceptor.logging.LogInterceptor
import com.mufeng.libs.MFLibs
import okhttp3.OkHttpClient

class HttpConfigImpl: HttpConfig {
    override val baseUrl: String
        get() = ""

    override val code: ArrayList<String>
        get() = arrayListOf("code")
    override val msg: ArrayList<String>
        get() = arrayListOf("msg")
    override val data: String
        get() = "data"

    override val successCode: IntArray
        get() = intArrayOf(1)
    override val errorCode: IntArray
        get() = intArrayOf(0)

    override fun handlerNetworkError(code: Int) {

    }

    override fun initHttpInterceptor(builder: OkHttpClient.Builder) {
            builder.addInterceptor(CacheInterceptor())
                .addInterceptor(LogInterceptor())

    }
}