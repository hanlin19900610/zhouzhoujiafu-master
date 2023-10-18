package com.mufeng.libs.utils.ktx

import java.lang.IllegalArgumentException

/**
 * 把集合分成数量固定的几组
 */
fun <T> ArrayList<T>.groupByCount(count: Int = 1): List<List<T>>{
    if(count<1) throw IllegalArgumentException("count不能小于1")
    val list = arrayListOf<ArrayList<T>>()
    var subList = arrayListOf<T>()
    forEach { t->
        subList.add(t)
        if(subList.size==count){
            list.add(subList)
            subList = arrayListOf<T>()
        }
    }
    //遍历结束，subList可能不满count
    if(subList.isNotEmpty()) list.add(subList)
    return list
}

/**
 * 从列表中删除符合条件的
 * @receiver ArrayList<T>
 * @param filter Function1<[@kotlin.ParameterName] T, Boolean>
 */
fun<T> ArrayList<T>.deleteIf(filter: (t: T) -> Boolean){
    val item = filter { filter(it) }
    removeAll(item)
}

// 交集 中缀  intersect
// 并集 中缀  union
// 差集 中缀  subtract