package com.mufeng.libs.core

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.mufeng.libs.R
import com.mufeng.libs.utils.view.click
import com.mufeng.libs.utils.view.load


fun BaseQuickAdapter<*, *>.setEmptyData(
    imgRes: Int = R.mipmap.img_zwhy,
    tip: String = "暂无数据",
    reRefreshData: () -> Unit = {}
) {

    val emptyView = LayoutInflater.from(context).inflate(R.layout.state_empty_layout, null)
    val imageView = emptyView.findViewById<ImageView>(R.id.ivNoData)
    imageView.load(imgRes)
    val textView = emptyView.findViewById<TextView>(R.id.emptyStatusTextView)
    textView.text = tip

    imageView.click {
        reRefreshData.invoke()
    }

    this.emptyView = emptyView
    isEmptyViewEnable = false

}

// adapter点击事件， 500毫秒内只响应1次
// 默认可连续点击
fun <T>BaseQuickAdapter<T, *>.setOnItemClick(
    duration: Long = 0,
    action: (view: View, position: Int, item: T?) -> Unit
) {
    val _clickCache_ = hashMapOf<Int, Runnable>()
    this.setOnItemClickListener { _, view, position ->
        if (!_clickCache_.containsKey(view.id)) {
            //unclicked
            _clickCache_[view.id] = Runnable { _clickCache_.remove(view.id) }
            action(view, position, getItem(position))
        }
        view.removeCallbacks(_clickCache_[view.id])
        view.postDelayed(_clickCache_[view.id], duration)
    }
}

/**
 * 子控件点击事件 带延迟点击效果
 * @receiver BaseQuickAdapter<T, *>
 * @param duration Long
 * @param id Int
 * @param action Function3<[@kotlin.ParameterName] View, [@kotlin.ParameterName] Int, [@kotlin.ParameterName] T?, Unit>
 */
fun <T>BaseQuickAdapter<T, *>.setOnItemChildClick(
    duration: Long = 0,
    id: Int,
    action: (view: View, position: Int, item: T?) -> Unit
) {
    val _clickCache_ = hashMapOf<Int, Runnable>()

    this.addOnItemChildClickListener(id) { _, view, position ->
        if (!_clickCache_.containsKey(view.id)) {
            //unclicked
            _clickCache_[view.id] = Runnable { _clickCache_.remove(view.id) }
            action(view, position, getItem(position))
        }
        view.removeCallbacks(_clickCache_[view.id])
        view.postDelayed(_clickCache_[view.id], duration)
    }
}

@JvmName("bind")
fun <VB : ViewBinding> QuickViewHolder.withBinding(bind: (View) -> VB): QuickViewHolder =
    BaseViewHolderWithBinding(bind(itemView))

@JvmName("getBinding")
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> QuickViewHolder.getViewBinding(): VB {
    if (this is BaseViewHolderWithBinding<*>) {
        return binding as VB
    } else {
        throw IllegalStateException("The binding could not be found.")
    }
}

class BaseViewHolderWithBinding<VB : ViewBinding>(val binding: VB) : QuickViewHolder(binding.root)