package com.dubitaxi.ui.selectUserType.driverSignUp.subscription

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityPaymentMethodBinding
import com.dubitaxi.utils.statusBarTransparent

class PaymentMethodActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityPaymentMethodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()
        statusBarTransparent()
    }

    override fun initView() {

    }

    override fun initControl() {
        binding.btnNextPaymentMethod.setOnClickListener(this)
        binding.backpressPaymentOption.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNextPaymentMethod -> {
                startActivity(Intent(this, AddCardActivity::class.java))
            }

            R.id.backpressPaymentOption -> {
                onBackPressed()
            }
        }
    }
}