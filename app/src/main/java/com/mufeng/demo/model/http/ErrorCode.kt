package com.mufeng.demo.model.http

object ErrorCode {
    const val AUTH_PARAMETER_ERROR = 20005 //人工实名认证 (参数错误)
    const val AUTH_APPLIED_ERROR = 20007 //人工实名认证 (已申请，正在审核)
    const val AUTH_PASSED_ERROR = 20008 //人工实名认证 (已通过实名认证)
    const val AUTH_BINDING_PHONE_ERROR = 20009 //人工实名认证 (请先绑定手机号)
    const val AUTH_CODE_ERROR = 20010 //人工实名认证 (短信验证码错误)
    const val AUTH_INSERT_DATABASE_ERROR = 90001 //人工实名认证 (插入数据库失败)
    const val AUTH_NOT_EXIT = 20013 //您还未提交实名认证
    const val UN_AUTHRIZATION = 10008 //登录失效
    const val DEMAND_ALREADY_OP = 40020 //请勿重复操作
    const val ROOM_PASSWORD = 40006 //请输入房间密码
}