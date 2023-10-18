package com.mufeng.libs.net

import android.app.Application
import com.mufeng.libs.MFLibs
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cache.CacheMode
import rxhttp.wrapper.param.Param
import java.io.File
import java.util.concurrent.TimeUnit

class RxHttpManager private constructor() {

    companion object {
        fun init(context: Application) {
            val build = OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ -> true }
            MFLibs.httpConfig?.initHttpInterceptor(build)
            val client = build.build()
            val cacheFile = File(context.externalCacheDir, "RxHttpCache")
            RxHttpPlugins.init(client)
                .setDebug(false, true)
                .setCache(cacheFile, 1000 * 100, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                .setExcludeCacheKeys("time") //设置一些key，不参与cacheKey的组拼
                .setOnParamAssembly { p: Param<*> ->
                    p.addAllHeader(MFLibs.httpConfig?.httpRequestHeader() ?: emptyMap())
                    MFLibs.httpConfig?.httpCommonParams()?.let { p.addAll(it) }
                }
        }
    }
}