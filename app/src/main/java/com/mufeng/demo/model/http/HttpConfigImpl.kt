package com.mufeng.demo.model.http

import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.mufeng.demo.config.URLConstants
import com.mufeng.libs.config.HttpConfig
import com.mufeng.libs.net.interceptor.CacheInterceptor
import com.mufeng.libs.net.interceptor.logging.LogInterceptor
import com.mufeng.libs.utils.context
import com.mufeng.libs.utils.ktx.newTask
import okhttp3.OkHttpClient

class HttpConfigImpl: HttpConfig {
    override val baseUrl: String
        get() = URLConstants.baseUrl

    override val code: ArrayList<String>
        get() = arrayListOf("code")
    override val msg: ArrayList<String>
        get() = arrayListOf("msg")
    override val data: String
        get() = "data"
    override val successCode: IntArray
        get() = intArrayOf(1,200)
    override val errorCode: IntArray
        get() = intArrayOf(0)

    override fun httpCommonParams(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()
//        val token = AccountUtils.token
//        if(token.isNotEmpty()){
//            params["token"] = token
//        }
        return params
    }

    override fun httpRequestHeader(): Map<String, String> {
        val handlers = mutableMapOf<String, String>()
//        val token = AccountUtils.token
//        if(token.isNotEmpty()){
//            handlers["token"] = token
//        }
        return handlers
    }

    override fun handlerNetworkError(code: Int) {
        when (code){
            -1 -> {
                //登录超时

            }
        }
    }

    override fun initHttpInterceptor(builder: OkHttpClient.Builder) {
        builder.addInterceptor(CacheInterceptor())
            .addInterceptor(LogInterceptor())
    }
}