package com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip

import android.os.Bundle
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityMyTripBinding
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.MyTripPageAdapter


class MyTripActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityMyTripBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTripBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()

        val adapter = MyTripPageAdapter(supportFragmentManager)
        binding.viewPagerMyTrip.adapter = adapter
        binding.tabLayoutMyTrip.setupWithViewPager(binding.viewPagerMyTrip)
    }


    override fun initView() {
        binding.backpressMyTrip.setOnClickListener(this)
    }

    override fun initControl() {

    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backpressMyTrip -> {
                onBackPressed()
            }
        }
    }
}