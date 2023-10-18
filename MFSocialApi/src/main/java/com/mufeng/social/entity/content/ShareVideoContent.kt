package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 *视频分享
 */
data class ShareVideoContent(
    var img: Bitmap? = null,   //缩略图
    var url: String? = null,        //网页
    var description: String? = null,    //描述
    var title: String? = null,     //标题
    var videoUrl: String? = null,       //视频url
    var videoLowBandUrl: String? = null , //视频url
    var videoPath: String? = null, // 微博使用本地路径
) : ShareContent()