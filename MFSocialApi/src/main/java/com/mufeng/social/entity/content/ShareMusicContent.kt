package com.mufeng.social.entity.content

import android.graphics.Bitmap

/**
 * 音乐分享
 */
data class ShareMusicContent(
    var img: Bitmap?, //缩略图
    var url: String? = null,      //音乐url
    var description: String? = null,  //描述
    var imageUrl: String? = null, // QQ分享音乐用到
    var title: String? = null,      //标题
    var musicUrl: String? = null,// 音频网页的URL地址, 必填不能为空, 限制长度不超过10KB
    var musicDataUrl: String? = null,// 音频数据的URL地址, 必填不能为空, 限制长度10KB
    var singerName: String? = null,// 歌手名,必填不能为空
    var duration: Int? = null,// 歌曲时长, 必填 单位毫秒
    var songLyric: String? = null,// 歌词, 建议填写, 限制长度32K
    var hbAlbumThumbFilePath: String? = null,// 高清专辑图本地文件路径, 选填, 文件限制长度不超过1MB
    var albumName: String? = null,// 音乐专辑名, 选填
    var musicGenre: String? = null,// 音乐流派, 选填
    var issueDate: Long? = null,// 发行时间 Unix时间戳, 选填, 单位秒
    var identification: String? = null,// 音乐标识符, 建议填写, 用户在微信音乐播放器调回应用时会携带该参数, 可用于唯一标识一首歌, 微信侧不理解
) : ShareContent()