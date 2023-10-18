package com.mufeng.libs.base

import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LanguageUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.mufeng.demo.R

abstract class BaseVMActivity<VM: BaseViewModel, VB: ViewBinding>: BaseActivity<VM, VB>(){

    var p = 1
    var isShowing = true

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtils.attachBaseContext(newBase))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initImmersionBar() {
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.white)
            titleBarMarginTop(binding.root)
        }
    }

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