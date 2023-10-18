package com.mufeng.libs.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.mufeng.libs.utils.inflateBindingWithGeneric

abstract class BaseBindingQuickAdapter<T, VB : ViewBinding>(data: MutableList<T>) :
    BaseQuickAdapter<T, BaseBindingHolder>(data) {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ) = BaseBindingHolder(inflateBindingWithGeneric<VB>(parent))



}

class BaseBindingHolder(private val binding: ViewBinding) : QuickViewHolder(binding.root) {
    constructor(itemView: View) : this(ViewBinding { itemView })

    @Suppress("UNCHECKED_CAST")
    fun <VB : ViewBinding> getViewBinding() = binding as VB
}