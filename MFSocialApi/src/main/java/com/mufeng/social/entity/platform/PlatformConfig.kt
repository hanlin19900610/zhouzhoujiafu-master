package com.mufeng.social.entity.platform

import com.mufeng.social.config.PlatformType

interface PlatformConfig {
    val name: PlatformType     // 平台类型
    var appkey:String?          // 应用id
}