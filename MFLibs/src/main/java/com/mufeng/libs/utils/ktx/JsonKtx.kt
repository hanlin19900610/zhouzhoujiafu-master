package com.mufeng.libs.utils.ktx

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


object GsonUtils {

    val INSTANCE: Gson by lazy { Gson() }
}

fun <T> T.toJson(): String {
    return GsonUtils.INSTANCE.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return GsonUtils.INSTANCE.fromJson(this, T::class.java)
}

inline fun <reified T> String.fromJsonToList(): List<T> {
    return GsonUtils.INSTANCE.fromJson<List<T>>(this,
        ParameterizedTypeImpl(T::class.java)
    )
}

inline fun <reified T> Any.fromJson(): T{
    return GsonUtils.INSTANCE.fromJson(this.toJson(), T::class.java)
}

inline fun <reified T> Any.fromJsonToList(): List<T> {
    return GsonUtils.INSTANCE.fromJson<List<T>>(this.toJson(),
        ParameterizedTypeImpl(T::class.java)
    )
}

class ParameterizedTypeImpl(private val clzz: Class<*>) : ParameterizedType {
    override fun getRawType(): Type = List::class.java

    override fun getOwnerType(): Type? = null

    override fun getActualTypeArguments(): Array<Type> = arrayOf(clzz)
}