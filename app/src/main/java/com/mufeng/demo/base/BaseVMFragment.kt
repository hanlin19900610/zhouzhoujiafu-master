package com.mufeng.libs.base

import androidx.viewbinding.ViewBinding

abstract class BaseVMFragment<VM : BaseViewModel, VB : ViewBinding> : BaseFragment<VM, VB>() {

    var p = 1
    var isShowing = true

    override fun showLoading(msg: String) {
        if(isShowing){
            super.showLoading(msg)
        }

    }

    override fun dismissLoading() {
        if(isShowing) {
            super.dismissLoading()
        }
    }
//    fun isLogin(): Boolean{
//        val token = AccountUtils.token
//        val user = AccountUtils.userBean
//        if(token.isEmpty() || user == null){
//            return false
//        }
//        return true
//    }
}