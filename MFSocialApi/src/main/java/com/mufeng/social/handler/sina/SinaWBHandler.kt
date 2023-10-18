package com.mufeng.social.handler.sina

import android.app.Activity
import android.content.Context
import android.util.Log
import com.mufeng.social.PlatformManager
import com.mufeng.social.callback.AuthCallback
import com.mufeng.social.callback.OperationCallback
import com.mufeng.social.callback.ShareCallback
import com.mufeng.social.config.OperationType
import com.mufeng.social.config.PlatformType
import com.mufeng.social.config.SocialConstants
import com.mufeng.social.entity.content.*
import com.mufeng.social.entity.platform.PlatformConfig
import com.mufeng.social.entity.platform.SinaPlatConfigBean
import com.mufeng.social.extention.*
import com.mufeng.social.handler.SSOHandler
import com.mufeng.social.utils.AppUtils
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.openapi.WBAPIFactory



/**
 * 新浪微博处理
 */
class SinaWBHandler(context: Context, config: PlatformConfig) : SSOHandler() {

    companion object {
        const val TAG = "SinaWBHandler"
        private val opList = listOf(
            OperationType.AUTH,
            OperationType.SHARE
        )
    }

    private var mContext = context
    private lateinit var mShareCallback: ShareCallback
    private lateinit var mAuthCallback: AuthCallback
    private var mWBAPI: IWBAPI? = null


    private var mWbShareCallback: WbShareCallback? = object : WbShareCallback {

        override fun onComplete() {
            mShareCallback.onSuccess?.invoke(PlatformType.SINA_WEIBO)
        }

        override fun onError(p0: UiError?) {
            mShareCallback.onErrors?.invoke(
                PlatformType.SINA_WEIBO,
                SocialConstants.SHARE_ERROR,
                "$TAG : 微博分享失败"
            )
            release()
        }

        override fun onCancel() {
            mShareCallback.onCancel?.invoke(PlatformType.SINA_WEIBO)
        }
    }

    init {
        if (config is SinaPlatConfigBean) {
            val authInfo =
                AuthInfo(context, config.appkey, config.redirectUrl, config.scope)
            mWBAPI = WBAPIFactory.createWBAPI(context)
            mWBAPI?.registerApp(context, authInfo)
        }
    }

    override val isInstalled: Boolean
        get() {
            return AppUtils.isAppInstalled("com.sina.weibo", mContext)
        }

    override fun onActivityResult(content: OperationContent) {
        when (content) {
            is ActivityResultContent -> {
                if (mWBAPI != null) {
                    mWBAPI!!.authorizeCallback(mContext as Activity,content.request, content.result, content.data)
                } else {
                    Log.e("Social", "$TAG :授权回调的IWBAPI为null")
                }
            }
            is NewIntentContent -> {
                if (mWBAPI != null) {
                    mWBAPI?.doResultIntent(content.intent, mWbShareCallback)
                } else {
                    Log.e("Social", "$TAG :分享回调的mWBAPI为null")
                }
            }
        }
    }

    override fun share(
        type: PlatformType,
        content: ShareContent,
        callback: OperationCallback
    ) {
        if (mContext !is Activity) {
            callback.onErrors?.invoke(
                PlatformType.SINA_WEIBO,
                SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                "$TAG : context 不是activiy或者fragment"
            )
            return
        }
        if (callback !is ShareCallback) {
            callback.onErrors?.invoke(
                PlatformType.SINA_WEIBO,
                SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                "$TAG : callback 类型错误"
            )
            return
        }
        mShareCallback = callback

        val activity: Activity = mContext as Activity
        PlatformManager.currentHandler = this
//    PlatformManager.currentHandlerMap[this.hashCode()] = this

        if (mWBAPI == null) {
            callback.onErrors
                ?.invoke(
                    PlatformType.SINA_WEIBO,
                    SocialConstants.MEDIA_ERROR,
                    "$TAG : 分享时 mWBAPI 为 null"
                )
            return
        }
        val weiboMessage = WeiboMultiMessage()
        when (content) {
            //文字分享
            is ShareTextContent -> weiboMessage.setTextMsg(content)
            //图片分享
            is ShareImageContent -> weiboMessage.setImgMsg(content)
            // 视频
            is ShareVideoContent -> weiboMessage.setVideoMsg(content)
            // 网页
            is ShareWebContent -> weiboMessage.setWebMsg(content)
            // 多图
            is ShareWeiboImagesContent -> weiboMessage.setImagesMsg(content)
            else -> {
                callback.onErrors
                    ?.invoke(
                        PlatformType.SINA_WEIBO,
                        SocialConstants.MEDIA_ERROR,
                        "$TAG : content 类型错误"
                    )
                return
            }
        }

        if (!weiboMessage.verificateMsg()) {
            callback.onErrors
                ?.invoke(
                    PlatformType.SINA_WEIBO,
                    SocialConstants.MEDIA_ERROR,
                    "$TAG : 参数错误"
                )
            return
        }

        mWBAPI?.shareMessage(activity, weiboMessage, false)
    }

    override fun authorize(type: PlatformType, callback: OperationCallback, content: AuthContent?) {
        if (mContext !is Activity) {
            callback.onErrors?.invoke(
                PlatformType.SINA_WEIBO,
                SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                "$TAG : context 类型错误"
            )
            return
        }
        val activity: Activity = mContext as Activity
        if (callback !is AuthCallback) {
            mAuthCallback.onErrors
                ?.invoke(
                    PlatformType.SINA_WEIBO,
                    SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                    "$TAG : callback 类型错误"
                )
            return
        }

        PlatformManager.currentHandler = this
//    PlatformManager.currentHandlerMap[this.hashCode()] = this

        if (mWBAPI == null) {
            callback.onErrors
                ?.invoke(
                    PlatformType.SINA_WEIBO,
                    SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                    "$TAG : 授权时 mWBAPI 为null"
                )
            return
        }
        mAuthCallback = callback

        mWBAPI?.let {
            it.authorize(activity, object : WbAuthListener {

                override fun onComplete(accessToken: Oauth2AccessToken?) {
                    accessToken?.let { token ->
                        // 从 Bundle 中解析 Token
                        if (token.isSessionValid) {
                            val map = mutableMapOf<String, String?>()
                            map["uid"] = token.uid
                            map["access_token"] = token.accessToken
                            map["refresh_token"] = token.refreshToken
                            map["expire_time"] = "" + token.expiresTime
                            mAuthCallback.onSuccess?.invoke(type, map)
                        } else {
                            mAuthCallback.onErrors?.invoke(
                                PlatformType.SINA_WEIBO,
                                SocialConstants.ACCESS_TOKEN_ERROR,
                                "$TAG : 授权回调的isSessionValid 为 false"
                            )
                        }
                        release()
                    }
                }

                override fun onError(p0: UiError?) {
                    mAuthCallback.onErrors?.invoke(
                        PlatformType.SINA_WEIBO,
                        SocialConstants.ACCESS_TOKEN_ERROR,
                        "$TAG : 授权失败 $p0"
                    )
                    release()
                }

                override fun onCancel() {
                    mAuthCallback.onCancel?.invoke(type)
                    release()
                }
            })
        }
    }

    override fun release() {
        mWbShareCallback = null
        mWBAPI = null
//    PlatformManager.currentHandlerMap.remove(this.hashCode())
        PlatformManager.currentHandler = null
    }

    /**
     * 获取该平台支持的操作
     */
    fun getAvailableOperation(): List<OperationType> {
        return opList
    }

}