package com.mufeng.social.entity.content

/**
 * 微信支付的实体
 */
data class WXPayContent (
    var appid:String,
    var noncestr:String,
    var packageX:String? = "Sign=WXPay",
    var partnerid:String,
    var prepayid:String,
    var timestamp:String,
    var sign:String,
): PayContent()