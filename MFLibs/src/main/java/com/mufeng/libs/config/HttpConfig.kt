package com.mufeng.libs.config

import okhttp3.OkHttpClient

interface HttpConfig {

    // 接口请求地址
    val baseUrl: String

    // 请求的解析code
    val code: ArrayList<String>
    val msg: ArrayList<String>
    val data: String

    // 请求成功CODE
    val successCode: IntArray

    // 请求失败code
    val errorCode: IntArray

    // 请求失败code
    fun handlerNetworkError(code: Int)

    // 公共参数
    fun httpCommonParams(): Map<String, Any> = emptyMap()

    // 公共请求头
    fun httpRequestHeader(): Map<String, String> = emptyMap()

    fun initHttpInterceptor(builder: OkHttpClient.Builder)

}