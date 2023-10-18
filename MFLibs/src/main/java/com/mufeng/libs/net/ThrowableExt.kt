package com.mufeng.libs.net

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.TimeoutCancellationException
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

val Throwable.code: String
    get() {
        val errorCode = when (this) {
            is HttpStatusCodeException -> this.statusCode.toString() //Http状态码异常
            is ParseException -> this.errorCode     //业务code异常
            else -> "0"
        }
        return try {
            errorCode
        } catch (e: Exception) {
            "0"
        }
    }

val Throwable.msg: String
    get() {
        this.printStackTrace()
        return when (this) {
            is UnknownHostException -> { //网络异常
                "当前无网络，请检查你的网络设置"
            }
            is SocketTimeoutException, is TimeoutException, is TimeoutCancellationException -> {
                "连接超时,请稍后再试"
            }
            is ConnectException -> {
                "网络不给力，请稍候重试！"
            }
            is HttpStatusCodeException -> {               //请求失败异常
                "Http状态码异常"
            }
            is JsonSyntaxException -> {  //请求成功，但Json语法异常,导致解析失败
                "数据解析失败,请检查数据是否正确"
            }
            is ParseException -> {       // ParseException异常表明请求成功，但是数据不正确
                this.message ?: errorCode   //msg为空，显示code
            }
            else -> {
                "请求失败，请稍后再试"
            }
        }
    }
