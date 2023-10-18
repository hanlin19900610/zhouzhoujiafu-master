package com.mufeng.libs.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseDifferAdapter
import com.mufeng.libs.utils.inflateBindingWithGeneric


abstract class BaseBindingDifferAdapter<T, VB : ViewBinding>(callback: DiffUtil.ItemCallback<T>) :
    BaseDifferAdapter<T, BaseBindingHolder>(callback) {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ) = BaseBindingHolder(inflateBindingWithGeneric<VB>(parent))

}