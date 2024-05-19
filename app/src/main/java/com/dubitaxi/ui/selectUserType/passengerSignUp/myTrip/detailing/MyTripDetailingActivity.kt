package com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.detailing

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityMyTripDetailingBinding
import com.dubitaxi.databinding.CalenderItemBinding
import com.dubitaxi.databinding.DialogRateReviewBinding

class MyTripDetailingActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityMyTripDetailingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTripDetailingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {
        binding.btnRateReviewPastDetails.setOnClickListener(this)
        binding.backpressMyTripDetails.setOnClickListener(this)
    }

    override fun initControl() {

    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRateReviewPastDetails -> {
                mReviewShowDialog()
            }
            R.id.backpressMyTripDetails -> {
               onBackPressed()
            }
        }
    }

    fun mReviewShowDialog() {
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_rate_review, null)
        val myBuilder = AlertDialog.Builder(this, 0).create()
        myBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        val binding = DialogRateReviewBinding.bind(dialogLayout)
        myBuilder.apply {
            setView(dialogLayout)
            setCancelable(false)

            binding.btnNotNow.setOnClickListener {
                myBuilder.dismiss()

            }
            binding.btnSubmit.setOnClickListener {

                myBuilder.dismiss()

            }
        }.show()
    }
}