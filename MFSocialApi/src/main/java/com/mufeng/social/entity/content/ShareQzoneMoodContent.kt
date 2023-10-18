package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 * 发表说说, 上传图片
 */
data class ShareQzoneMoodContent(
    var description: String? = null,  //描述
    var imageList: List<String>? = null,
) : ShareContent()