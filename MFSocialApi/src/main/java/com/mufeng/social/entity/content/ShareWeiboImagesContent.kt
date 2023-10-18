package com.mufeng.social.entity.content

/**
 * 网页分享
 */
data class ShareWeiboImagesContent(
    var imageList: List<String>? = null,   //多图分享
) : ShareContent()