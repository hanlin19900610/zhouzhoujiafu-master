package com.mufeng.social.entity.platform

import com.mufeng.social.config.OperationType

/**
 * 平台信息
 */
data class Platform(
    var platConfig: PlatformConfig,                   // 平台配置
    var availableOperationType: List<OperationType>
)  // 平台操作