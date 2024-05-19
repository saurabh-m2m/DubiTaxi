package com.dubitaxi.ui.selectUserType.driverSignUp.subscription

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityAddCardBinding
import com.dubitaxi.databinding.DialogLogoutBinding
import com.dubitaxi.ui.selectUserType.SelectUserTypeActivity
import com.dubitaxi.ui.selectUserType.home.HomeActivity
import com.dubitaxi.utils.statusBarTransparent

class AddCardActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityAddCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()
        statusBarTransparent()
    }

    override fun initView() {
    }

    override fun initControl() {
        binding.backpressAddCard.setOnClickListener(this)
        binding.btnSubmitAddCard.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backpressAddCard -> {
                onBackPressed()
            }
            R.id.btnSubmitAddCard->{
                mSubscriptionDailog()
            }
        }
    }

    fun mSubscriptionDailog() {
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dailog_subscription_successfully, null)
        val myBuilder = android.app.AlertDialog.Builder(this, 0).create()
        myBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myBuilder.apply {
            setView(dialogLayout)
            setCancelable(false)
        }.show()
        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java).apply {
                putExtra("AddCard","AddCard")
            })
        }, 3000)
    }
}