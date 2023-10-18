package com.mufeng.libs.utils.common.livedata

import androidx.lifecycle.MutableLiveData

class BooleanLiveData : MutableLiveData<Boolean>() {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}

