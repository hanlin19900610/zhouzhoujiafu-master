package com.mufeng.libs.utils

import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout


fun SmartRefreshLayout.finishAll(){
    this.finishRefresh()
    this.finishLoadMore()
}

/**
 * @param list 接口数据源
 * @param data 定义adapter的数据源
 * @param
 */
fun <T>SmartRefreshLayout.setupData(list: List<T?>?, data: MutableList<T>, adapter: BaseQuickAdapter<*, *>, p: Int, pageSize: Int = 10){
    if ((list?.size ?: 0) < pageSize) {
        finishLoadMoreWithNoMoreData()
        setNoMoreData(true)
    } else {
        setNoMoreData(false)
    }

    if (p == 1) {
        finishRefresh()
        data.clear()
        if(list.isNullOrEmpty()){
            adapter.isEmptyViewEnable = true
        }else {
            list.forEach {
                data.add(it!!)
            }
        }
        adapter.notifyDataSetChanged()
    } else {
        finishLoadMore()
        if (list?.size == 0){
            return
        }
        list?.forEach {
            data.add(it!!)
        }
        adapter.notifyDataSetChanged()
    }
}