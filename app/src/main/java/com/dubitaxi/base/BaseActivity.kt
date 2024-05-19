package com.dubitaxi.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.SharedPreferenceUtil


abstract class BaseActivity : AppCompatActivity() {

    /*
        On Create
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


    }
    abstract fun initView()
    abstract fun initControl()
    abstract fun  mObserver()

    val SharedPreference by lazy {
        SharedPreferenceUtil.getInstance(this.applicationContext)
    }

    /*
     Hide Progress Bar
     */
    fun hideLoading() {
        ProgressDialogUtils.getInstance().hideProgress()
    }

    /*
     Show Progress Bar
     */
    fun showLoading() {
        hideLoading()
        ProgressDialogUtils.getInstance().showProgress(this, true)
    }

    /*
     Show Error
     */
    fun showError(context: Context?, throwable: Throwable) {
        ErrorUtil.handlerGeneralError(context, throwable)
    }

}