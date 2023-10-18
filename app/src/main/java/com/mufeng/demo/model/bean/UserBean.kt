package com.mufeng.demo.model.bean


import androidx.annotation.Keep

@Keep
data class UserBean(
    val avatar: String?,
    val disable: Int?,
    val distribution_code: String?,
    val id: Int?,
    val level: Int?,
    var nickname: String?,
    val token: String?,
    val sn: String?,
    val mobile: String?,
    val user_money: String?,
    val total_order_amount: String?,
    val total_recharge_amount: String?,
    val lang: String?,
    val next_level_tips: String?,
    val level_name: String?,
    val user_integral: Int?,
    val wait_pay: Int?,
    val wait_delivery: Int?,
    val wait_take: Int?,
    val wait_comment: Int?,
    val after_sale: Int?,
    val coupon: Int?,
    val distribution_setting: Int?,
    val notice_num: Int?,
    val collect: Int?,
    val vip: Int?,
    val hasPayPassword: Int?,
)