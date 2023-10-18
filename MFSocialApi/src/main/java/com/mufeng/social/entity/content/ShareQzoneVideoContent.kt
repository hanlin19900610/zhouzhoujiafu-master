package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 * 发表说说, 上传图片
 */
data class ShareQzoneVideoContent(
    var description: String? = null,  //描述
    var videoPath: String? = null,// 发表视频, 只支持本地地址, 发表视频时必传, 100M以内
) : ShareContent()