package com.dubitaxi.base

import androidx.lifecycle.ViewModel
import com.dubitaxi.webservice.RetrofitUtil
import com.dubitaxi.webservice.mApiInterface


open class BaseViewModel : ViewModel() {
    val mApiInterface: mApiInterface by lazy {
        RetrofitUtil.createBaseApiService()

    }
}