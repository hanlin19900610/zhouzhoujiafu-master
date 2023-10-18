package com.mufeng.social.entity.content

import android.graphics.Bitmap
import com.mufeng.social.config.MiniprogramType

/**
 * 微信小程序
 */
data class ShareAppletContent(
    var webPageUrl: String? = null,   //兼容低版本的网页链接, 限制长度不超过10KB
    var userName: String? = null,   //小程序的原始id
    var path: String? = null,   //小程序页面路径
    var withShareTicket: Boolean? = null,   //是否使用带shareTicket的分享,
    var miniprogramType: Int? = MiniprogramType.MINIPTOGRAM_TYPE_RELEASE,   //小程序的类型, 默认正式版
    var title: String? = null,  //标题
    var description: String? = null, //描述
    var img: Bitmap? = null          //缩略图
) : ShareContent()