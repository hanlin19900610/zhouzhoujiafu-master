package com.mufeng.libs.net

/**
 *
 * @param T
 * @property code Int
 * @property msg String
 * @property data T?
 * @constructor
 */
data class ResponseBean<T>(
    val code: Int,
    val msg: String,
    val data: T?
)
