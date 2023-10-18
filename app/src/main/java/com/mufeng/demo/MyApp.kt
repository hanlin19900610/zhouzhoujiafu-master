package com.mufeng.demo

import android.app.Application
import com.mufeng.demo.model.http.HttpConfigImpl
import com.mufeng.libs.MFLibs
import com.mufeng.libs.config.ResConfig
import com.mufeng.libs.net.RxHttpManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MFLibs.init(
            this,
            isDebug = true,
            defaultLogTag = "AndroidProjectTemplates",
            httpConfig = HttpConfigImpl(),
            resConfig = object : ResConfig {
                override fun initRefresh() {
                    //设置全局的Header构建器
                    SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                        ClassicsHeader(context)
                    }
                    //设置全局的Footer构建器
                    SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                        ClassicsFooter(context).setDrawableSize(20f)
                    }
                }
            }
        )
        RxHttpManager.init(this)
    }

}