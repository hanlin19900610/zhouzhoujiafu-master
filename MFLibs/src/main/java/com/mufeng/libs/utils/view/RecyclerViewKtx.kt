package com.mufeng.libs.utils.view

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.*
import com.blankj.utilcode.util.AdaptScreenUtils
import com.google.android.flexbox.FlexboxLayoutManager
import com.mufeng.libs.utils.RecyclerViewDivider
import com.mufeng.libs.utils.common.anim.DefaultItemNoAnimAnimator
import com.mufeng.libs.utils.common.view.SafeGridLayoutManager
import com.mufeng.libs.utils.common.view.SafeLinearLayoutManager
import com.mufeng.libs.utils.common.view.SafeStaggeredGridLayoutManager
import com.mufeng.libs.utils.ktx.dp

/**
 * Description: RecyclerView扩展
 */

/**
 * 设置分割线
 * @param color 分割线的颜色，默认是#DEDEDE
 * @param size 分割线的大小，默认是1px
 * @param isReplace 是否覆盖之前的ItemDecoration，默认是true
 *
 */
fun RecyclerView.divider(color: Int = Color.parseColor("#f5f5f5"), size: Int = 1f.dp, isReplace: Boolean = true): RecyclerView {
    val decoration = RecyclerViewDivider(context, orientation)
    decoration.setDrawable(GradientDrawable().apply {
        setColor(color)
        shape = GradientDrawable.RECTANGLE
        setSize(size, size)
    })
    if(isReplace && itemDecorationCount>0){
        removeItemDecorationAt(0)
    }
    addItemDecoration(decoration)
    return this
}

fun RecyclerView.flexbox(): RecyclerView {
    layoutManager = FlexboxLayoutManager(context)
    return this
}

fun RecyclerView.adapter(adapter: RecyclerView.Adapter<*>): RecyclerView{
    this.adapter = adapter
    return this
}

fun RecyclerView.vertical(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = SafeLinearLayoutManager(context, RecyclerView.VERTICAL, false)
    if (spanCount != 0) {
        layoutManager = SafeGridLayoutManager(context, spanCount)
    }
    if (isStaggered) {
        layoutManager = SafeStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }
    return this
}

fun RecyclerView.horizontal(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = SafeLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    if (spanCount != 0) {
        layoutManager = SafeGridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }
    if (isStaggered) {
        layoutManager = SafeStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
    }
    return this
}

inline val RecyclerView.orientation
    get() = if (layoutManager == null) -1 else layoutManager.run {
        when (this) {
            is LinearLayoutManager -> orientation
            is GridLayoutManager -> orientation
            is StaggeredGridLayoutManager -> orientation
            else -> -1
        }
    }

fun RecyclerView.smoothScrollToEnd(){
    if(adapter!=null && adapter!!.itemCount>0){
        smoothScrollToPosition(adapter!!.itemCount-1)
    }
}

fun RecyclerView.scrollToEnd(){
    if(adapter!=null && adapter!!.itemCount>0){
        scrollToPosition(adapter!!.itemCount-1)
    }
}

/**
 * 滚动置顶，只支持线性布局
 */
fun RecyclerView.scrollTop(position: Int){
    if(layoutManager is LinearLayoutManager){
        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
    }
}

fun RecyclerView.disableItemAnimation(): RecyclerView{
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    return this
}

/**
 * 边界模糊
 */
fun RecyclerView.fadeEdge(length: Int = AdaptScreenUtils.pt2Px(25f), isHorizontal: Boolean = false): RecyclerView{
    if(isHorizontal) isHorizontalFadingEdgeEnabled = true
    else isVerticalFadingEdgeEnabled = true
    overScrollMode = View.OVER_SCROLL_ALWAYS
    setFadingEdgeLength(length)
    return this
}


/**
 * 移除所有差异性计算引发的默认更新动画.
 */
fun RecyclerView.removeAllAnimation() {
    val itemAnimator = DefaultItemNoAnimAnimator()
    this.itemAnimator = itemAnimator
    itemAnimator.supportsChangeAnimations = false
    itemAnimator.addDuration = 0L
    itemAnimator.changeDuration = 0L
    itemAnimator.removeDuration = 0L
}


/**
 * Add item padding
 * @param padding the top, bottom, left, right is same
 */
fun RecyclerView.itemPadding(padding:Int) {
    addItemDecoration(PaddingItemDecoration(padding, padding, padding, padding))
}

/**
 * Add item padding for top, bottom, left, right
 */
fun RecyclerView.itemPadding(top: Int, bottom: Int, left: Int = 0, right: Int = 0) {
    addItemDecoration(PaddingItemDecoration(top, bottom, left, right))
}

class PaddingItemDecoration(top: Int, bottom: Int, left: Int, right: Int) : RecyclerView.ItemDecoration() {

    private val mTop = top
    private val mBottom = bottom
    private val mLeft = left
    private val mRight = right

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = view.dp2px(mBottom)
        outRect.top = view.dp2px(mTop)
        outRect.left = view.dp2px(mLeft)
        outRect.right = view.dp2px(mRight)
    }
}