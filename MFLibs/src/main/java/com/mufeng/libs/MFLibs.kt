package com.mufeng.libs

import android.app.Application
import android.graphics.Color
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.mufeng.libs.config.HttpConfig
import com.mufeng.libs.config.HttpConfigImpl
import com.mufeng.libs.config.ResConfig
import com.mufeng.libs.utils.DataStoreUtils
import com.mufeng.libs.utils.initContext
import com.tencent.mmkv.MMKV

object MFLibs {

    var isDebug = true
    var defaultLogTag = "MFLibs"
    var sharedPrefName = "MFLibs"

    var httpConfig: HttpConfig? = null
    var resConfig: ResConfig? = null

    fun init(
        application: Application, isDebug: Boolean = true,
        defaultLogTag: String = MFLibs.defaultLogTag,
        sharedPrefName: String = MFLibs.sharedPrefName,
        httpConfig: HttpConfig = HttpConfigImpl(),
        resConfig: ResConfig = object : ResConfig{},
    ) {
        this.isDebug = isDebug
        this.defaultLogTag = defaultLogTag
        this.sharedPrefName = sharedPrefName
        this.httpConfig = httpConfig
        this.resConfig = resConfig
        resConfig.initRefresh()
        initContext(application)
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.getDefaultMaker().setBgResource(R.drawable.mf_toast_bg)
        ToastUtils.getDefaultMaker().setTextColor(Color.WHITE)
        MMKV.initialize(application)
        DataStoreUtils.init(application)
    }

}