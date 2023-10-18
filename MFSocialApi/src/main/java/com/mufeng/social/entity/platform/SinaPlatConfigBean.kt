package com.mufeng.social.entity.platform

import com.mufeng.social.config.PlatformType

/**
 * 微博平台信息
 */
class SinaPlatConfigBean (
    override val name: PlatformType, // 平台类型
    override var appkey:String?,          // 应用id
    var redirectUrl: String,   // 微博回调url
    var scope: String   // 微博域
): PlatformConfig