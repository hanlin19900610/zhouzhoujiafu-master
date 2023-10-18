package com.mufeng.libs.base

import androidx.lifecycle.ViewModel
import com.rxjava.rxlife.Scope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel: ViewModel(), Scope {

    private var mDisposables: CompositeDisposable? = null

    override fun onScopeStart(d: Disposable) {
        addDisposable(d)
    }

    override fun onScopeEnd() {
        // 事件正常结束时回调
    }

    private fun addDisposable(disposable: Disposable){
        if(mDisposables == null){
            mDisposables = CompositeDisposable()
        }
        mDisposables?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose() {
        mDisposables?.dispose()
    }

}