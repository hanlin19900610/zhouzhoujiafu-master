package com.mufeng.social.config

import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject

/**
 * 小程序类型
 */
object MiniprogramType {

    // 正式版
    const val MINIPTOGRAM_TYPE_RELEASE = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE

    // 测试版
    const val MINIPROGRAM_TYPE_TEST = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST

    // 预览版
    const val MINIPROGRAM_TYPE_PREVIEW = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW

}