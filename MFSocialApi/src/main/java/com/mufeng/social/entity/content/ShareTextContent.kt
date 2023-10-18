package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 * 文字分享
 */
data class ShareTextContent(
    var text: String? = null,   //分享文本
    var url: String? = null,       //网页url
    var title: String? = null,    //标题
    var description: String? = null,    //描述
    var thumb: Bitmap? = null,    //缩略图
) : ShareContent()