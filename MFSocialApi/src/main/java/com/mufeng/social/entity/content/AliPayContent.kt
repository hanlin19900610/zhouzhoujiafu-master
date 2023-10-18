package com.mufeng.social.entity.content

/**
 * 支付宝支付信息实体， 从服务器获取的orderInfo
 */
data class AliPayContent(var orderInfo:String): PayContent()