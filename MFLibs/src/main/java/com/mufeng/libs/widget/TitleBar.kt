package com.mufeng.libs.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.RelativeLayout
import com.mufeng.libs.R
import com.mufeng.libs.databinding.MfTitlebarBinding
import com.mufeng.libs.utils.ktx.dp
import com.mufeng.libs.utils.ktx.drawable
import com.mufeng.libs.utils.ktx.sp
import com.mufeng.libs.utils.view.*


/**
 * Description: 通用标题栏
 */
class TitleBar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attributeSet, defStyleAttr) {

    // 左边文本
    var leftText: CharSequence = ""
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var leftTextColor = Color.BLACK
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var leftTextSize = 16
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var leftTextDrawable: Drawable? = null
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var leftTextDrawableSize = 0
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    //左边图片
    var leftImage: Drawable? = null
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var leftImagePadding = 12
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    //中间标题
    var title: CharSequence = ""
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var titleSize = 18
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var titleColor = Color.BLACK
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var titleAlignLeft = false
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    var titleBold = false
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    //右边文字
    var rightText: CharSequence = ""
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var rightTextColor = Color.BLACK
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var rightTextSize = 16
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    var rightTextBold = false
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    //右边图片
    var rightImage: Drawable? = null
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var rightImagePadding = 12
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    //右边图片2
    var rightImage2: Drawable? = null
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var rightImage2Padding = 12
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    //右边图片3
    var rightImage3: Drawable? = null
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var rightImage3Padding = 12
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }
    var bottomLine = Color.TRANSPARENT
        set(value) {
            field = value
            applyAttr()
            applySelf()
        }

    private lateinit var binding: MfTitlebarBinding

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.TitleBar)
        leftText = ta.getString(R.styleable.TitleBar_leftText) ?: ""
        leftTextColor = ta.getColor(R.styleable.TitleBar_leftTextColor, leftTextColor)
        leftTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, leftTextSize.dp)
        leftTextDrawable = ta.getDrawable(R.styleable.TitleBar_leftTextDrawable)
        leftTextDrawableSize = ta.getDimensionPixelSize(R.styleable.TitleBar_leftTextDrawableSize, leftTextDrawableSize)
        bottomLine = ta.getColor(R.styleable.TitleBar_bottomLine, bottomLine)

        leftImage = ta.getDrawable(R.styleable.TitleBar_leftImageSrc)
        leftImagePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_leftImagePadding, leftImagePadding.dp)

        title = ta.getString(R.styleable.TitleBar_title) ?: ""
        titleColor = ta.getColor(R.styleable.TitleBar_titleColor, titleColor)
        titleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_titleSize, titleSize.sp)
        titleAlignLeft = ta.getBoolean(R.styleable.TitleBar_titleAlignLeft, titleAlignLeft)
        titleBold = ta.getBoolean(R.styleable.TitleBar_titleBold, titleBold)

        rightText = ta.getString(R.styleable.TitleBar_rightText) ?: ""
        rightTextColor = ta.getColor(R.styleable.TitleBar_rightTextColor, rightTextColor)
        rightTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, rightTextSize.sp)
        rightTextBold = ta.getBoolean(R.styleable.TitleBar_rightTextBold, rightTextBold)

        rightImage = ta.getDrawable(R.styleable.TitleBar_rightImageSrc)
        rightImagePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_rightImagePadding, rightImagePadding.dp)
        rightImage2 = ta.getDrawable(R.styleable.TitleBar_rightImage2Src)
        rightImage2Padding = ta.getDimensionPixelSize(R.styleable.TitleBar_rightImage2Padding, rightImage2Padding.dp)
        rightImage3 = ta.getDrawable(R.styleable.TitleBar_rightImage3Src)
        rightImage3Padding = ta.getDimensionPixelSize(R.styleable.TitleBar_rightImage3Padding, rightImage3Padding.dp)

        ta.recycle()
        val root = inflate(context, R.layout.mf_titlebar, this)
        binding = MfTitlebarBinding.bind(root)
        clipToOutline = true
        applyAttr()
        applySelf()
        initClick()
    }

    private fun applyAttr() {
        if(childCount==0)return
        applyLeftText()
        applyLeftImage()
        applyTitle()
        applyRightText()
        applyRightImage()
        applyRightImage2()
        applyRightImage3()
    }

    private fun applySelf() {
        if (background == null) setBackgroundColor(Color.WHITE)
        if (paddingLeft == 0) setPadding(1.dp, paddingTop, paddingRight, paddingBottom)
        if (paddingRight == 0) setPadding(paddingLeft, paddingTop, 1.dp, paddingBottom)
    }

    val paint = Paint()
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (bottomLine != 0) {
            paint.color = bottomLine
            canvas.drawRect(Rect(0, measuredHeight - 1.dp, measuredWidth, measuredHeight), paint)
        }
    }

    fun setup(leftImageRes: Int = 0, leftText: CharSequence = "", title: CharSequence = "",     rightImageRes: Int = 0,
              rightText: CharSequence = ""): TitleBar {
        if (leftImageRes != 0) this.leftImage = drawable(leftImageRes)
        if (rightImageRes != 0) this.rightImage = drawable(rightImageRes)
        if(title.isNotEmpty()) this.title = title
        if(leftText.isNotEmpty()) this.leftText = leftText
        if(rightText.isNotEmpty()) this.rightText = rightText
        applyAttr()
        return this
    }

    private fun applyLeftText() {
        if (leftText.isEmpty()) {
            binding.tvLeftText.gone()
            return
        }
        binding.tvLeftText.visible()
        binding.tvLeftText.text = leftText
        binding.tvLeftText.setTextColor(leftTextColor)
        binding.tvLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize.toFloat())
        if (leftTextDrawable != null) {
            binding.tvLeftText.setCompoundDrawablesWithIntrinsicBounds(leftTextDrawable, null, null, null)
            if (leftTextDrawableSize == 0 || leftTextDrawable!!.intrinsicHeight == 0) return
            val scale = leftTextDrawable!!.intrinsicWidth * 1f / leftTextDrawable!!.intrinsicHeight
            if (scale > 1) {
                binding.tvLeftText.sizeDrawable(width = leftTextDrawableSize,
                        height = (leftTextDrawableSize / scale).toInt())
            } else {
                binding.tvLeftText.sizeDrawable(width = (leftTextDrawableSize / scale).toInt(),
                        height = leftTextDrawableSize)
            }
        }
    }

    fun setupLeftText(text: CharSequence, textColor: Int = leftTextColor, textSize: Int = leftTextSize,
                      drawable: Drawable? = leftTextDrawable, drawableSize: Int = leftTextDrawableSize): TitleBar {
        this.leftText = text
        this.leftTextColor = textColor
        this.leftTextSize = textSize
        this.leftTextDrawable = drawable
        this.leftTextDrawableSize = drawableSize
        applyLeftText()
        return this
    }

    private fun applyLeftImage() {
        if (leftImage == null) {
            binding.ivLeftImage.gone()
            return
        }
        binding.ivLeftImage.visible()
        binding.ivLeftImage.setImageDrawable(leftImage)
        binding.ivLeftImage.setPadding(leftImagePadding, leftImagePadding, leftImagePadding, leftImagePadding)
    }

    fun setupLeftImage(imageRes: Int, imagePadding: Int = leftImagePadding): TitleBar {
        this.leftImage = drawable(imageRes)
        this.leftImagePadding = imagePadding
        applyLeftImage()
        return this
    }

    private fun applyTitle() {
        if (title.isEmpty()) {
            binding.tvTitle.gone()
            return
        }
        binding.tvTitle.visible()
        binding.tvTitle.text = title
        binding.tvTitle.setTextColor(titleColor)
        binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
        if (titleAlignLeft) {
            binding.tvTitle.gravity = Gravity.LEFT.or(Gravity.CENTER_VERTICAL)
        } else {
            binding.tvTitle.gravity = Gravity.CENTER
        }
        binding.tvTitle.isBold = titleBold
    }

    fun setupTitle(text: CharSequence, textColor: Int = titleColor, textSize: Int = titleSize, alignLeft: Boolean = titleAlignLeft): TitleBar {
        this.title = text
        this.titleColor = textColor
        this.titleSize = textSize
        this.titleAlignLeft = alignLeft
        applyTitle()
        return this
    }

    private fun applyRightText() {
        if (rightText.isEmpty()) {
            binding.tvRightText.gone()
            return
        }
        binding.tvRightText.visible()
        binding.tvRightText.text = rightText
        binding.tvRightText.setTextColor(rightTextColor)
        binding.tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize.toFloat())
        binding.tvRightText.isBold = rightTextBold
    }

    fun setupRightText(text: CharSequence, textColor: Int = rightTextColor, textSize: Int = rightTextSize): TitleBar {
        this.rightText = text
        this.rightTextColor = textColor
        this.rightTextSize = textSize
        applyRightText()
        return this
    }

    private fun applyRightImage() {
        if (rightImage == null) {
            binding.ivRightImage.gone()
            return
        }
        binding.ivRightImage.visible()
        binding.ivRightImage.setImageDrawable(rightImage)
        binding.ivRightImage.setPadding(rightImagePadding, rightImagePadding, rightImagePadding, rightImagePadding)
    }

    fun setupRightImage(imageRes: Int, imagePadding: Int = rightImagePadding): TitleBar {
        this.rightImage = drawable(imageRes)
        this.rightImagePadding = imagePadding
        applyRightImage()
        return this
    }

    private fun applyRightImage2() {
        if (rightImage2 == null) {
            binding.ivRightImage2.gone()
            return
        }
        binding.ivRightImage2.visible()
        binding.ivRightImage2.setImageDrawable(rightImage2)
        binding.ivRightImage2.setPadding(rightImage2Padding, rightImage2Padding, rightImage2Padding, rightImage2Padding)
    }

    fun setupRightImage2(imageRes: Int, imagePadding: Int = rightImage2Padding): TitleBar {
        this.rightImage2 = drawable(imageRes)
        this.rightImage2Padding = imagePadding
        applyRightImage2()
        return this
    }

    private fun applyRightImage3() {
        if (rightImage3 == null) {
            binding.ivRightImage3.gone()
            return
        }
        binding.ivRightImage3.visible()
        binding.ivRightImage3.setImageDrawable(rightImage3)
        binding.ivRightImage3.setPadding(rightImage3Padding, rightImage3Padding, rightImage3Padding, rightImage3Padding)
    }

    fun setupRightImage3(imageRes: Int, imagePadding: Int = rightImage3Padding): TitleBar {
        this.rightImage3 = drawable(imageRes)
        this.rightImage3Padding = imagePadding
        applyRightImage3()
        return this
    }

    private fun initClick() {
        binding.tvLeftText.click { clickListener?.leftTextClick() }
        binding.tvRightText.click { clickListener?.rightTextClick() }
        binding.ivLeftImage.click { clickListener?.leftImageClick() }
        binding.ivRightImage.click { clickListener?.rightImageClick() }
        binding.ivRightImage2.click { clickListener?.rightImage2Click() }
        binding.ivRightImage3.click { clickListener?.rightImage3Click() }
    }

    fun titleTextView() = binding.tvTitle
    fun leftTextView() = binding.tvLeftText
    fun rightTextView() = binding.tvRightText
    fun leftImageView() = binding.ivLeftImage
    fun rightImageView() = binding.ivRightImage
    fun rightImageView2() = binding.ivRightImage2
    fun rightImageView3() = binding.ivRightImage3

    private var clickListener: ClickListener? = null
    fun clickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun leftTextClick() {}
        fun rightTextClick() {}
        fun leftImageClick() {}
        fun rightImageClick() {}
        fun rightImage2Click() {}
        fun rightImage3Click() {}
    }

}

