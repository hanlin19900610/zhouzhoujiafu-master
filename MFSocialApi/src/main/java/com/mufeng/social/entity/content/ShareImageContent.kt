package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 * 图片分享实体类
 */
data class ShareImageContent(var imagePath: String?, var imageData: Bitmap?, var thumb: Bitmap?) : ShareContent()