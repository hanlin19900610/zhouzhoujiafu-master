package com.mufeng.social.entity.content

import android.graphics.Bitmap
import com.mufeng.social.config.PlatformType

/**
 * 图片文字分享
 */
data class ShareTextImageContent(
    var url: String? = null,       //网页url
    var title: String? = null,         //标题
    var description: String? = null,    //描述
    var imageUrl: String? = null, // 分享图片的URL或本地路径
    var imageList: List<String>? = null,
    var type: PlatformType? = null,
) : ShareContent()