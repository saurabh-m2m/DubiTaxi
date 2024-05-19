package com.dubitaxi.ui.selectUserType.passengerSignUp.scheduleBooking

import android.os.Bundle
import android.view.View
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityScheduleBookingBinding
import com.dubitaxi.utils.statusBarTransparent

class ScheduleBookingActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityScheduleBookingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {

    }

    override fun initControl() {

    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when(v?.id) {

        }

    }

}