package com.mufeng.social.callback

import com.mufeng.social.config.PlatformType

data class AuthCallback(
    var onSuccess:((type: PlatformType, data:Map<String, String?>?) -> Unit)? = null,
    override var onErrors: ((type: PlatformType, errorCode: Int, errorMsg:String?) -> Unit)? = null,
    var onCancel:((type: PlatformType) -> Unit)? = null
): OperationCallback