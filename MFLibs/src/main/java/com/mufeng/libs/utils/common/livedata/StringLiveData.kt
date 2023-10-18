package com.mufeng.libs.utils.common.livedata

import androidx.lifecycle.MutableLiveData

class StringLiveData : MutableLiveData<String>() {

    override fun getValue(): String {
        return super.getValue() ?: ""
    }

}