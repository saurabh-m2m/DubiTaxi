package com.fluper.tiacabapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dubitaxi.utils.SharedPreferenceUtil

abstract class BaseFragment : Fragment() {


    val preference by lazy {
        SharedPreferenceUtil.getInstance(requireContext())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    abstract fun mInitView()
    abstract fun mInitControl()
    abstract fun mObserver()
}