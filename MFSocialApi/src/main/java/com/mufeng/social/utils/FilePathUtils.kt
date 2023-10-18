package com.mufeng.social.utils

import android.content.Context

import java.io.File

/**
 * description :
 * @date 2019/7/15
 * @author: yzy.
 */
object FilePathUtils {
  /**
   * 公司文件夹名称
   */
  private val COMPANY_FOLDER = "social"
  /**
   * APP文件夹名称
   */
  private val APP_FOLDER = "social_path"
  /**
   * 图片
   */
  val IMAGES = "images"

  /**
   * 获取自定义的app的主目录
   */
  fun getAppPath(context: Context): String {
    SDCardUtils.initSDCardPaths(context)
    return ((if (SDCardUtils.paths.isNotEmpty())
      SDCardUtils.paths[0] + File.separator
    else
      context.cacheDir.path + File.separator)
      + COMPANY_FOLDER
      + File.separator
      + APP_FOLDER
      + File.separator)
  }
}
