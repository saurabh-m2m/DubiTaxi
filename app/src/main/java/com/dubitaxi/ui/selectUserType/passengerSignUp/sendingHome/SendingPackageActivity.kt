package com.dubitaxi.ui.selectUserType.passengerSignUp.sendingHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivitySendingPackageBinding
import com.dubitaxi.utils.statusBarTransparent

class SendingPackageActivity  : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivitySendingPackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySendingPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {

    }

    override fun initControl() {
      binding.backpressSetDropMap.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.backpressSetDropMap->{
                onBackPressed()
            }
        }
    }
}