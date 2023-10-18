package com.mufeng.libs.state

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * 页面状态 VM -> V
 */
open class UIState()

/**
 * V -> VM
 */
open class UIAction()

/**
 * 一次性 VM -> V
 */
open class UIEvent()

sealed class PageState<out T> {
    data class Success<T>(val data: T): PageState<T>()
    data class Error(val code: String, val msg: String): PageState<Nothing>()
    object Loading : PageState<Nothing>()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}

class ResultBuilder<T>() {
    var onLoading: () -> Unit = {}
    var onSuccess: (data: T?) -> Unit = {}
    var onError: (code: String, msg: String) -> Unit = {code, msg ->  }
}

typealias StatefulLiveData<T> = LiveData<PageState<T>>
typealias StatefulMutableLiveData<T> = MutableLiveData<PageState<T>>

@MainThread
inline fun <T> StatefulLiveData<T>.observeState(
    owner: LifecycleOwner,
    init: ResultBuilder<T>.() -> Unit
) {
    val result = ResultBuilder<T>().apply(init)

    observe(owner) { state ->
        when (state) {
            is PageState.Loading -> result.onLoading.invoke()
            is PageState.Success -> result.onSuccess(state.data)
            is PageState.Error -> result.onError(state.code, state.msg)
        }
    }
}