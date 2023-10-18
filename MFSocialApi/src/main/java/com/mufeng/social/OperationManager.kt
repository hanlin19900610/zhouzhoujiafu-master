package com.mufeng.social

import com.mufeng.social.config.OperationType
import com.mufeng.social.config.SocialConstants
import com.mufeng.social.entity.OperationBean
import com.mufeng.social.entity.content.AuthContent
import com.mufeng.social.entity.content.OperationContent
import com.mufeng.social.entity.content.PayContent
import com.mufeng.social.entity.content.ShareContent
import com.mufeng.social.handler.SSOHandler

/**
 * description: 操作管理类
 */
internal class OperationManager private constructor() {

  companion object {
    val instance = SingletonHolder.holer

  }

  private object SingletonHolder {
    val holer = OperationManager()
  }

  /***
   * 执行操作
   */
  fun perform(bean: OperationBean) {
    // 平台是否可用
    if (PlatformManager.availablePlatMap[bean.operationPlat] == null) {
      bean.operationCallback
        .onErrors
        ?.invoke(
          bean.operationPlat,
          SocialConstants.PLAT_NOT_INSTALL,
          "未安装${bean.operationPlat}的app"
        )
      return
    }

    // 平台是否支持该操作
    if (!PlatformManager
        .available4PlatAndOperation(bean.operationPlat, bean.operationType)
    ) {
      bean.operationCallback.onErrors
        ?.invoke(
          bean.operationPlat,
          SocialConstants.OPERATION_NOT_SUPPORT,
          "${bean.operationPlat} 不支持 ${bean.operationType}"
        )
      return
    }

    val handler = PlatformManager
      .getPlatHandler(bean.operationContext, bean.operationPlat)

    if (handler == null) {
      bean.operationCallback.onErrors
        ?.invoke(
          bean.operationPlat, SocialConstants.PLAT_HANDLER_ERROR,
          "获取 ${bean.operationPlat}的handler失败"
        )
      return
    }

    when (bean.operationType) {
      OperationType.SHARE -> {
        handler.share(
          bean.operationPlat,
          bean.operationContent as ShareContent,
          bean.operationCallback
        )
      }
      OperationType.AUTH -> {
        handler.authorize(
          bean.operationPlat,
          bean.operationCallback,
          bean.operationContent as AuthContent
        )
      }
      OperationType.PAY -> {
        handler.pay(
          bean.operationPlat,
          bean.operationContent as PayContent,
          bean.operationCallback
        )
      }
    }
  }

  /**
   * 部分sdk 需要回调activity的onActivityResult方法
   */
  fun performActivityResult(handler: SSOHandler, content: OperationContent) {
    handler.onActivityResult(content)
  }
}