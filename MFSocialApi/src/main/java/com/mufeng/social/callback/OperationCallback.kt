package com.mufeng.social.callback

import com.mufeng.social.config.PlatformType

/**
 *
 */
interface OperationCallback {
    /**
     * type : 平台类型
     * errorCode : 错误码
     * data : 错误信息
     */
    var onErrors: ((type: PlatformType, errorCode: Int, errorMsg:String?) -> Unit)?
}