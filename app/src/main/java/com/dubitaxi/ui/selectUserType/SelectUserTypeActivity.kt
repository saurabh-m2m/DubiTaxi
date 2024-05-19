package com.dubitaxi.ui.selectUserType

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivitySelectUserTypeBinding
import com.dubitaxi.ui.selectUserType.createmobilenumber.CreateAccountActivity
import com.dubitaxi.utils.statusBarTransparent

class SelectUserTypeActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivitySelectUserTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectUserTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarTransparent()
        initView()
        initControl()
    }

    override fun initView() {

    }

    override fun initControl() {
        binding.imgDriverSelectUser.setOnClickListener(this)
        binding.imgPassengerSelect.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_passenger_select -> {
                SharedPreference.userType = "0"
                startActivity(Intent(this, CreateAccountActivity::class.java))

            }

            R.id.img_driver_select_user -> {
                SharedPreference.userType = "1"
                startActivity(Intent(this, CreateAccountActivity::class.java))

            }
        }
    }


}