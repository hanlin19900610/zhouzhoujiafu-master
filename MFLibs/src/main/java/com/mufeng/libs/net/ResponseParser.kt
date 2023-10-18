package com.mufeng.libs.net

import com.alibaba.fastjson.JSON
import com.mufeng.libs.MFLibs
import com.mufeng.libs.utils.ktx.GsonUtils
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import java.io.IOException
import java.lang.reflect.Type

@Parser(name = "Response")
class ResponseParser<T> : TypeParser<ResponseBean<T>> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): ResponseBean<T> {
        val json = response.body?.string()
        val jsonObject = JSON.parseObject(json)

        val code = MFLibs.httpConfig!!.code.firstNotNullOf {
            jsonObject.getInteger(it)
        }

        val msg = MFLibs.httpConfig!!.msg.firstNotNullOf {
            jsonObject.getString(it)
        }

        val data: ResponseBean<T>

        // 请求失败
        if (MFLibs.httpConfig?.successCode?.contains(code) != true) {
            MFLibs.httpConfig?.handlerNetworkError(code)
            throw ParseException(code.toString(), msg, response)
        }

        if (MFLibs.httpConfig?.successCode?.contains(code) == true && types[0] === Any::class.java) {
            data = ResponseBean(code, msg, Any() as T);
            return data
        }

        if (MFLibs.httpConfig?.successCode?.contains(code) == true && types[0] === String::class.java) {
            data = ResponseBean(code, msg, jsonObject.getString(MFLibs.httpConfig?.data) as T);
            return data
        }

        if (MFLibs.httpConfig?.successCode?.contains(code) == true && types[0] === Int::class.java) {
            data = ResponseBean(code, msg, jsonObject.getInteger(MFLibs.httpConfig?.data) as T);
            return data
        }

        if (MFLibs.httpConfig?.successCode?.contains(code) == true && types[0] === Double::class.java) {
            data = ResponseBean(code, msg, jsonObject.getDouble(MFLibs.httpConfig?.data) as T);
            return data
        }

        if (MFLibs.httpConfig?.successCode?.contains(code) == true && types[0] === Boolean::class.java) {
            data = ResponseBean(code, msg, jsonObject.getBoolean(MFLibs.httpConfig?.data) as T);
            return data
        }
        try {

            data = ResponseBean(code, msg, GsonUtils.INSTANCE.fromJson<T>(
                jsonObject.getString(MFLibs.httpConfig?.data),
                types[0]
            ))
            return data
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
