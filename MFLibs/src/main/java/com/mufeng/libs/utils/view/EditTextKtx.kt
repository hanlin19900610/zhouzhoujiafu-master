package com.mufeng.libs.utils.ktx

import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView

/**
 * 显示密码文本
 */
fun EditText.showPassword(){
    transformationMethod = HideReturnsTransformationMethod.getInstance()
    setSelection(text.length)
}

/**
 * 隐藏密码文本
 */
fun EditText.hidePassword(){
    transformationMethod = PasswordTransformationMethod.getInstance()
    setSelection(text.length)
}

/**
 * 动态设置最大长度限制
 */
fun EditText.maxLength(max: Int){
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
}

fun EditText.setTextWidthEndCursor(s: CharSequence){
    setText(s)
    setSelection(text.toString().length)
}


/**
 * 获取文本
 */
fun EditText.textString(): String {
    return this.text.toString()
}
/**
 * 获取去除空字符串的文本
 */
fun EditText.textStringTrim(): String {
    return this.textString().trim()
}
/**
 * 文本是否为空
 */
fun EditText.isEmpty(): Boolean {
    return this.textString().isEmpty()
}
/**
 * 去空字符串后文本是否为空
 */
fun EditText.isTrimEmpty(): Boolean {
    return this.textStringTrim().isEmpty()
}
/**
 * 获取文本
 */
fun TextView.textString(): String {
    return this.text.toString()
}
/**
 * 获取去除空字符串的文本
 */
fun TextView.textStringTrim(): String {
    return this.textString().trim()
}
/**
 * 文本是否为空
 */
fun TextView.isEmpty(): Boolean {
    return this.textString().isEmpty()
}
/**
 * 去空字符串后文本是否为空
 */
fun TextView.isTrimEmpty(): Boolean {
    return this.textStringTrim().isEmpty()
}

/**
 * 清空输入框
 * @receiver EditText
 */
fun EditText.clear(){
    this.setText("")
}

var EditText.txt: String
    set(value) {
        setText(value)
        setSelection(value.length)
    }
    get() = text.toString().trim()

/**
 * 设置密码是否明文显示
 */
var EditText.isPasswordPlaintext: Boolean
    set(value){
        transformationMethod = if (value){
            HideReturnsTransformationMethod.getInstance()
        }else{
            PasswordTransformationMethod.getInstance()
        }
        setSelection(text.length)
    }
    get() {
        return transformationMethod == HideReturnsTransformationMethod.getInstance()
    }