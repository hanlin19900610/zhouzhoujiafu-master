package com.mufeng.social.handler.wx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.mufeng.social.PlatformManager
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * description: 微信支付的回调activity
 * @date 2019/7/15
 * @author: yzy.
 */
class WXPayCallbackActivity : Activity(), IWXAPIEventHandler {

  private lateinit var mWXHandler: WXHandler

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )

    initAndPerformIntent()
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    initAndPerformIntent()
  }

  override fun onResp(resp: BaseResp?) {
    if (resp != null) {
      try {
        mWXHandler.callbackWXEventHandler(resp)
      } catch (var3: Exception) {
      }

    }
    finish()
    overridePendingTransition(0, 0)
  }

  override fun onReq(req: BaseReq?) {
    this.finish()
    overridePendingTransition(0, 0)
  }

  private fun initAndPerformIntent() {
    PlatformManager.currentHandler?.let {
      mWXHandler = it as WXHandler
      mWXHandler.wxAPI?.handleIntent(intent, this)
    }
  }


}