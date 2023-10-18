package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 * 网页分享
 */
data class ShareWebContent(
    var webPageUrl: String? = null,   //待分享的网页url
    var title: String? = null,  //网页标题
    var description: String? = null, //网页描述
    var img: Bitmap? = null,          //网页缩略图
    var actionUrl: String? = null,
    var defaultText: String? = null,
) : ShareContent()