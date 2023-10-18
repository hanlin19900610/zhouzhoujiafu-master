package com.mufeng.social.extention

import android.net.Uri
import com.mufeng.social.config.MiniprogramType
import com.mufeng.social.config.PlatformType
import com.mufeng.social.entity.content.*
import com.mufeng.social.handler.qq.QQHandler
import com.mufeng.social.utils.BitmapUtils
import com.sina.weibo.sdk.api.*
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzonePublish
import com.tencent.connect.share.QzoneShare
import com.tencent.mm.opensdk.modelmsg.*
import java.io.File
import java.util.UUID


/**
 * 分享消息的扩展
 */
fun WXMediaMessage.setImgMsg(content: ShareImageContent): WXMediaMessage {
    content.thumb?.let {
        if (!it.isRecycled) {
            val imageObject = WXImageObject()
            imageObject.setImagePath(content.imagePath)
            mediaObject = imageObject
            thumbData = BitmapUtils.bmpToByteArray(content.thumb, false)
        }
    }
    return this
}

fun WXMediaMessage.setMusicMsg(content: ShareMusicContent): WXMediaMessage {
    content.img?.let {
        val musicObject = WXMusicVideoObject().apply {
            musicUrl = content.musicUrl
            musicDataUrl = content.musicDataUrl
            songLyric = content.songLyric
            hdAlbumThumbFilePath = content.hbAlbumThumbFilePath
            singerName = content.singerName
            albumName = content.albumName
            musicGenre = content.musicGenre
            issueDate = content.issueDate?:0L
            identification = content.identification
            duration = content.duration ?: 0

        }
        mediaObject = musicObject
        title = content.title
        description = content.description
        thumbData = BitmapUtils.bmpToByteArray(content.img, false)
    }
    return this
}

fun WXMediaMessage.setTextMsg(content: ShareTextContent): WXMediaMessage {
    //text object
    val textObject = WXTextObject().apply {
        text = content.text
    }
    mediaObject = textObject
    description = content.text
    return this
}

fun WXMediaMessage.setVideoMsg(content: ShareVideoContent): WXMediaMessage {
    content.img?.let {
        val videoObject = WXVideoObject()
        videoObject.videoUrl = content.videoUrl
        videoObject.videoLowBandUrl = content.videoLowBandUrl
        mediaObject = videoObject
        title = content.title
        description = content.description
        thumbData = BitmapUtils.bmpToByteArray(content.img, false)
    }
    return this
}

fun WXMediaMessage.setWebMsg(content: ShareWebContent): WXMediaMessage {
    content.img?.let {
        val webpageObject = WXWebpageObject().apply {
            webpageUrl = content.webPageUrl
        }
        mediaObject = webpageObject
        title = content.title
        description = content.description
        thumbData = BitmapUtils.bmpToByteArray(content.img, false)
    }
    return this
}

// 分享小程序消息
fun WXMediaMessage.setAppletMsg(content: ShareAppletContent): WXMediaMessage{
    content.img?.let {
        val miniProgramObj = WXMiniProgramObject()
        miniProgramObj.webpageUrl = content.webPageUrl
        miniProgramObj.miniprogramType = content.miniprogramType ?: MiniprogramType.MINIPTOGRAM_TYPE_RELEASE
        miniProgramObj.userName = content.userName
        miniProgramObj.path = content.path
        mediaObject = miniProgramObj
        title = content.title
        description = content.description
        thumbData = BitmapUtils.bmpToByteArray(content.img, false)
    }
    return this
}

// QQ
fun QQHandler.ShareParamBean.setTextImgParam(content: ShareTextImageContent): QQHandler.ShareParamBean{
    shareType = if(content.type == PlatformType.QQ_ZONE)QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT else QQShare.SHARE_TO_QQ_TYPE_DEFAULT
    title = content.title
    description = content.description
    url = content.url
    imageUrl = content.imageUrl
    imageList = content.imageList
    type = content.type
    return this
}

fun QQHandler.ShareParamBean.setImageParam(content: ShareImageContent): QQHandler.ShareParamBean{
    shareType = QQShare.SHARE_TO_QQ_TYPE_IMAGE
    imageUrl = content.imagePath
    return this
}

fun QQHandler.ShareParamBean.setMusicParam(content: ShareMusicContent): QQHandler.ShareParamBean{
    shareType = QQShare.SHARE_TO_QQ_TYPE_AUDIO
    title = content.title
    description = content.description
    url = content.url
    audioUrl = content.musicDataUrl
    imageUrl = content.imageUrl
    return this
}

fun QQHandler.ShareParamBean.setQZoneMoodParam(content: ShareQzoneMoodContent): QQHandler.ShareParamBean{
    shareType = QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD
    description = content.description
    imageList = content.imageList
    return this
}

fun QQHandler.ShareParamBean.setQZoneVideoParam(content: ShareQzoneVideoContent): QQHandler.ShareParamBean{
    shareType = QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO
    description = content.description
    videoPath = content.videoPath
    return this
}



fun WeiboMultiMessage.setTextMsg(content: ShareTextContent): WeiboMultiMessage {
    val textObject = TextObject()
    textObject.text = content.text
    this.textObject = textObject
    return this
}

fun WeiboMultiMessage.setImgMsg(content: ShareImageContent):WeiboMultiMessage{
    content.imageData?.let {
        if (!it.isRecycled) {
            val imageObject = ImageObject()
            imageObject.setImageData(content.imageData)
            this.imageObject = imageObject
        }
    }
    return this
}


fun WeiboMultiMessage.setImagesMsg(content: ShareWeiboImagesContent):WeiboMultiMessage{
    val multiImageObject = MultiImageObject()
    val list = arrayListOf<Uri>()
    content.imageList?.forEach{
        list.add(Uri.parse(it))
    }
    multiImageObject.imageList = list
    this.multiImageObject = multiImageObject
    return this
}

fun WeiboMultiMessage.setVideoMsg(content: ShareVideoContent):WeiboMultiMessage{
    content.img?.let {
        if (!it.isRecycled){
            val videoSourceObject = VideoSourceObject()
            videoSourceObject.videoPath = Uri.fromFile(File(content.videoUrl?:""))
            this.videoSourceObject = videoSourceObject
        }
    }

    return this
}

fun WeiboMultiMessage.setWebMsg(content: ShareWebContent):WeiboMultiMessage{
    content.img?.let {
        if(!it.isRecycled){
            val mediaObject = WebpageObject()
            mediaObject.identify = UUID.randomUUID().toString()
            mediaObject.thumbData = BitmapUtils.bmpToByteArray(content.img, false)
            mediaObject.title = content.title
            mediaObject.description = content.description
            mediaObject.actionUrl = content.actionUrl
            mediaObject.defaultText = content.defaultText
            this.mediaObject = mediaObject
        }
    }
    return this
}

/**
 * 检测消息中是否至少包含一种正确消息数据
 * @return true 包含, false 不包含
 */
fun WeiboMultiMessage.verificateMsg():Boolean{
    // 文本分享时，text不能为空
    if (textObject != null){
        return true
    }

    // 图片分享时, 图片数据不能为空
    if (imageObject != null && imageObject.imageData != null){
        return true
    }

    if(mediaObject != null
        && !mediaObject.actionUrl.isNullOrBlank()
        && mediaObject.thumbData != null){
        return true
    }

    if (videoSourceObject != null
        && !videoSourceObject.actionUrl.isNullOrBlank()
        && videoSourceObject.thumbData != null){
        return true
    }
    return false
}
