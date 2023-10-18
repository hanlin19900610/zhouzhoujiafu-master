package com.mufeng.social.handler.qq

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
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
import com.mufeng.social.extention.*
import com.mufeng.social.handler.SSOHandler
import com.mufeng.social.utils.AppUtils
import com.mufeng.social.utils.BitmapUtils
import com.mufeng.social.utils.FilePathUtils
import com.mufeng.social.utils.FileUtils
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzonePublish
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject
import java.io.File
import java.util.ArrayList

class QQHandler(context: Context, config: PlatformConfig) : SSOHandler() {

    companion object {
        const val TAG = "QQHandler"
        const val FILE_PROVIDER = "com.tencent.sample.fileprovider"
        private val opList = listOf(
            OperationType.AUTH,
            OperationType.SHARE
        )
    }

    private var mContext: Context = context
    private var mTencent: Tencent = Tencent.createInstance(config.appkey, context.applicationContext)
    private lateinit var mShareCallback: ShareCallback
    private lateinit var mAuthCallback: AuthCallback
    private lateinit var mSharePlatType: PlatformType
    private var mTempImgFilePath = ""

    private var mIUiListener = object : IUiListener {
        override fun onComplete(p0: Any?) {
            mShareCallback.onSuccess?.invoke(mSharePlatType)
            release()
        }

        /**
         * 分享到朋友圈调用的回调方法是onCancel
         */
        override fun onCancel() {
            mShareCallback.onCancel?.invoke(mSharePlatType)
            release()
        }

        override fun onWarning(p0: Int) {

        }

        override fun onError(p0: UiError?) {
            mShareCallback.onErrors
                ?.invoke(
                    mSharePlatType,
                    SocialConstants.SHARE_ERROR,
                    "$TAG : 分享失败 ${p0?.errorCode} ， ${p0?.errorMessage} ， ${p0?.errorDetail}"
                )
            release()
        }
    }

    override val isInstalled: Boolean
        get() {
            return (AppUtils.isAppInstalled("com.tencent.qqlite", mContext)      // qq轻聊
                    || AppUtils.isAppInstalled("com.tencent.mobileqq", mContext) // qq手机
                    || AppUtils.isAppInstalled("com.tencent.mobileqqi", mContext)// qq国际
                    || AppUtils.isAppInstalled("com.tencent.tim", mContext))     // tim
        }

    override fun share(type: PlatformType, content: ShareContent, callback: OperationCallback) {
        if (callback !is ShareCallback) {
            callback.onErrors
                ?.invoke(
                    PlatformType.QQ,
                    SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                    "$TAG : callback 类型错误"
                )
            return
        }

        mShareCallback = callback
        // parameter as activity or fragment can be access only for mTencent's login()
        if (mContext !is Activity) {
            mShareCallback.onErrors
                ?.invoke(
                    PlatformType.QQ,
                    SocialConstants.AUTH_ERROR,
                    "$TAG :context 不是activity或者fragment"
                )
            return
        }

        val activity: Activity = mContext as Activity
        // 将平台维护的currentHandlerOfActivityResult设置为自己，用于activity回调
//    PlatformManager.currentHandlerMap[this.hashCode()] = this
        PlatformManager.currentHandler = this
        mSharePlatType = type

        val imagePath = (FilePathUtils.getAppPath(activity)
                + FilePathUtils.IMAGES
                + File.separator
                + "/socail_qq_img_tmp" + System.currentTimeMillis()
                + ".png")

        // 清除图片缓存
        deleTempImageFile(imagePath)
        mTempImgFilePath = imagePath

        val params = Bundle()
        val paramsBean = ShareParamBean()

        when (content) {
            is ShareImageContent -> paramsBean.setImageParam(content)
            is ShareMusicContent -> paramsBean.setMusicParam(content)
            is ShareTextImageContent -> paramsBean.setTextImgParam(content)
            is ShareQzoneVideoContent -> paramsBean.setQZoneVideoParam(content)
            is ShareQzoneMoodContent -> paramsBean.setQZoneMoodParam(content)
            else -> {
                callback.onErrors
                    ?.invoke(type, SocialConstants.MEDIA_ERROR, "$TAG : content 类型错误")
                return
            }
        }

        if (paramsBean.shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, paramsBean.title)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, paramsBean.description)
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, paramsBean.url)
        }

        if (type == PlatformType.QQ_ZONE) {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, paramsBean.shareType)
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, paramsBean.description);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, paramsBean.title);
            //!这里是大坑 不能用SHARE_TO_QQ_IMAGE_LOCAL_URL
            val pathArr = ArrayList<String?>()
            paramsBean.imageList?.let { pathArr.addAll(it) }

            params.putString(QzonePublish.PUBLISH_TO_QZONE_SUMMARY, paramsBean.description)
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, pathArr)// 图片地址ArrayList
            if (content is ShareTextImageContent) {
                mTencent.shareToQzone(activity, params, mIUiListener)
            } else {
                if(content is ShareQzoneVideoContent){
                    params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, content.videoPath)
                }
                mTencent.publishToQzone(activity, params, mIUiListener)
            }
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, paramsBean.shareType)
            if(content is ShareImageContent){
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, paramsBean.imageUrl)
            }else{
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, paramsBean.imageUrl)
            }
            if(content is ShareMusicContent){
                params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, paramsBean.audioUrl)
            }
            mTencent.shareToQQ(activity, params, mIUiListener)
        }
    }

    override fun authorize(type: PlatformType, callback: OperationCallback, content: AuthContent?) {
        if (callback !is AuthCallback) {
            mAuthCallback.onErrors
                ?.invoke(
                    PlatformType.QQ,
                    SocialConstants.CALLBACK_CLASSTYPE_ERROR,
                    "$TAG: callback 类型错误"
                )
            return
        }
        mAuthCallback = callback

        // parameter as activity or fragment can be access only for mTencent's login()
        if (mContext !is Activity) {
            mAuthCallback.onErrors
                ?.invoke(
                    PlatformType.QQ,
                    SocialConstants.AUTH_ERROR,
                    "$TAG: context 不是activity或者fragment"
                )
            return
        }

        // 设置当前handler 为currenthandler 用于授权回调
//    PlatformManager.currentHandlerMap[this.hashCode()] = this
        PlatformManager.currentHandler = this
        val activity: Activity = mContext as Activity
        mTencent.login(activity, "all", object : IUiListener {
            override fun onComplete(callbackMap: Any?) {
                if (null == callbackMap) {
                    Log.e(TAG, "onSuccess but response=null")
                    mAuthCallback.onErrors
                        ?.invoke(
                            PlatformType.QQ,
                            SocialConstants.AUTH_ERROR,
                            "$TAG :授权回调的map为null"
                        )
                    return
                }

                if (callbackMap is JSONObject) {
                    initOpenidAndToken(callbackMap)
                    mAuthCallback.onSuccess?.invoke(PlatformType.QQ, jsonToMap(callbackMap))
                } else {
                    mAuthCallback.onErrors
                        ?.invoke(
                            PlatformType.QQ,
                            SocialConstants.AUTH_ERROR,
                            "$TAG : 授权回调的数据转换为map失败"
                        )
                }
                release()
            }

            override fun onError(uiError: UiError) {
                mAuthCallback.onErrors
                    ?.invoke(
                        PlatformType.QQ,
                        SocialConstants.AUTH_ERROR,
                        "$TAG :授权失败${uiError.errorCode},${uiError.errorMessage},${uiError.errorDetail}"
                    )
                release()
            }

            override fun onCancel() {
                mAuthCallback.onCancel?.invoke(PlatformType.QQ)
                release()
            }

            override fun onWarning(p0: Int) {

            }
        })
    }

    override fun onActivityResult(content: OperationContent) {
        val activityResultContent = content as ActivityResultContent
        Tencent.onActivityResultData(
            activityResultContent.request
            , activityResultContent.result
            , activityResultContent.data
            , mIUiListener
        )
    }

    override fun release() {
        deleTempImageFile(mTempImgFilePath)
        Log.d("Social", "$TAG : currentHandler had set null")
        // 回调完成，重置currentHandler
//    PlatformManager.currentHandlerMap.remove(this.hashCode())
        PlatformManager.currentHandler = null
    }

    /**
     * 获取该平台支持的操作
     */
    fun getAvailableOperation(): List<OperationType> {
        return opList
    }

    /**
     * 要初始化open_id和token
     */
    private fun initOpenidAndToken(jsonObject: JSONObject) {
        try {
            val token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN)
            val expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN)
            val openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID)

            mTencent.setAccessToken(token, expires)
            mTencent.openId = openId
        } catch (e: Exception) {
            e.message
        }
    }

    private fun jsonToMap(jsonObj: JSONObject): MutableMap<String, String?> {
        val map = mutableMapOf<String, String?>()

        val iterator = jsonObj.keys()

        while (iterator.hasNext()) {
            val var4 = iterator.next()
            map[var4] = jsonObj.opt(var4) as String
        }
        return map
    }

    private fun deleTempImageFile(path: String) {
        val file = File(path)
        FileUtils.deleteFile(file)
    }

    /**
     * 封装了分享的参数
     */
    data class ShareParamBean(
        var shareType: Int = 0,
        var title: String? = null,
        var description: String? = null,
        var url: String? = null,
        var imageUrl: String? = null,
        var audioUrl: String? = null,
        var type: PlatformType? = null,
        var imageList: List<String>? = null,
        var videoPath: String? = null,
    )
}