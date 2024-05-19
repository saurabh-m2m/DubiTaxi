package com.dubitaxi.ui.selectUserType.passengerSignUp.paymentOption

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityPaymentOptionBinding
import com.dubitaxi.databinding.ActivityVehicleListBinding
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.SetOnMapActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.VehicleListActivity
import com.dubitaxi.utils.statusBarTransparent
import com.google.android.material.bottomsheet.BottomSheetDialog

class PaymentOptionActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityPaymentOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {

    }

    override fun initControl() {
        binding.backpressPaymentOption.setOnClickListener(this)
        binding.btnSubmitPaymentOption.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backpressPaymentOption -> {
                onBackPressed()
            }

            R.id.btnSubmitPaymentOption -> {
                startActivity(Intent(this, VehicleListActivity::class.java))
            }
        }
    }
}