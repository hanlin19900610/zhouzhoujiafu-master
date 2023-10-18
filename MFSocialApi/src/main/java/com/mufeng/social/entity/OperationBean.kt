package com.mufeng.social.entity

import android.content.Context
import com.mufeng.social.callback.OperationCallback
import com.mufeng.social.config.OperationType
import com.mufeng.social.config.PlatformType
import com.mufeng.social.entity.content.OperationContent

data class OperationBean(
    var operationContext: Context,        // 操作上下文
    var operationPlat: PlatformType,      // 平台类型
    var operationType: OperationType,     // 操作类型
    var operationCallback: OperationCallback,   // 回调
    var operationContent: OperationContent? = null  // 平台内容
)