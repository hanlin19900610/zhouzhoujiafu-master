package com.mufeng.social.entity.content

import android.content.Intent

/**
 * 回调操作实体类
 */
data class ActivityResultContent(
    var request: Int,
    var result: Int,
    var data: Intent?
) : OperationContent