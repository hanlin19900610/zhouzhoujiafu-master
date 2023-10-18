package com.mufeng.libs.utils.view

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mufeng.libs.utils.GlideBlurTransformation

/**
 * Description: ImageView相关
 * Create by lxj, at 2018/12/5
 */

/**
 * Glide加载图片
 * @param url 可以是网络，可以是File，可以是资源id等等Glide支持的类型
 * @param placeholder 默认占位图
 * @param error 失败占位图
 * @param isCircle 是否是圆形，默认false，注意：isCircle和roundRadius两个只能有一个生效
 * @param isCenterCrop 是否设置scaleType为CenterCrop，你也可以在布局文件中设置
 * @param roundRadius 圆角角度，默认为0，不带圆角，注意：isCircle和roundRadius两个只能有一个生效
 * @param isCrossFade 是否有过渡动画，默认没有过渡动画
 * @param isForceOriginalSize 是否强制使用原图，默认false
 */
fun ImageView.load(
    url: Any?, placeholder: Int = 0, error: Int = 0,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = false,
    blurScale: Float = 0f,
    blurRadius: Float = 20f,
    roundRadius: Int = 0,
    isCrossFade: Boolean = false,
    isForceOriginalSize: Boolean = false,
    targetWidth: Int = 0,
    targetHeight: Int = 0,
    onImageLoad: ((resource: Drawable?) -> Unit)? = null,
    onImageFail: (() -> Unit)? = null
) {
    if (context == null) return
    if (context is Activity && ((context as Activity).isDestroyed || (context as Activity).isFinishing)) return
    val options = RequestOptions().placeholder(placeholder).error(error).apply {
        if (isCenterCrop && scaleType != ImageView.ScaleType.CENTER_CROP)
            scaleType = ImageView.ScaleType.CENTER_CROP
        if (isCircle) {
            transform(CircleCrop())
        } else if (roundRadius != 0) {
            if (isCenterCrop) {
                transform(CenterCrop(), RoundedCorners(roundRadius))
            } else {
                transform(RoundedCorners(roundRadius))
            }
        }
        if (blurScale > 0) transform(
            GlideBlurTransformation(
                scale = blurScale,
                blurRadius = blurRadius
            )
        )
        if (isForceOriginalSize) {
            override(Target.SIZE_ORIGINAL)
        }
        if (targetWidth != 0 && targetHeight != 0) {
            override(targetWidth, targetHeight)
        }
    }
    val glide = Glide.with(context).load(url)
        .apply(options)
        .apply {
            if (isCrossFade) transition(DrawableTransitionOptions.withCrossFade())
            if (onImageLoad != null || onImageFail != null) {
                listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onImageFail?.invoke()
                        return onImageFail != null
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onImageLoad?.invoke(resource)
                        return false
                    }
                })
            }

        }
    glide.into(this)
}

var ImageView.imageResource: Int
    get() = throw Exception("Property does not have a getter")
    set(value) {
        setImageResource(value)
    }