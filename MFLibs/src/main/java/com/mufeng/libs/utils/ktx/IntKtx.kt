package com.mufeng.libs.utils.ktx

/**
 * 数字格式化  不能超过设置的最大值  例如99+
 * @receiver Int?
 * @param max Int
 * @return String
 */
fun Int?.formatMaxNum(max: Int): String{
    return if(this ?: 0 > max){
        "${max}+"
    }else{
        this.toString()
    }
}