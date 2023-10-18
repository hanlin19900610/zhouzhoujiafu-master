package com.mufeng.social.entity.platform

import com.mufeng.social.config.PlatformType

/**
 * 通用平台的信息配置
 */
data class CommPlatConfigBean(
    override val name: PlatformType, // 平台类型
    override var appkey:String?         // 应用id
): PlatformConfig