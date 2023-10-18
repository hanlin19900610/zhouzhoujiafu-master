package com.mufeng.social.callback

import com.mufeng.social.config.PlatformType

data class PayCallback(
    var onSuccess: ((type: PlatformType) -> Unit)? = null,
    override var onErrors: ((type: PlatformType, errorCode: Int, errorMsg: String?) -> Unit)? = null,
    var onCancel: ((type: PlatformType) -> Unit)? = null
) : OperationCallback