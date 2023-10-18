package com.mufeng.social.entity.platform

import com.mufeng.social.config.PlatformType

/**
 * 支付宝的配置实体类
 */
data class AliPlatConfigBean (
    override val name: PlatformType, // 平台类型
    override var appkey:String?,          // 应用id
    var authToken: String   // 授权token
): PlatformConfig