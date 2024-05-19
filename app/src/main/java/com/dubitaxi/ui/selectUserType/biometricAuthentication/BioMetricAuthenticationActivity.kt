package com.dubitaxi.ui.selectUserType.biometricAuthentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityBioMetricAuthenticationBinding
import com.dubitaxi.ui.selectUserType.biometricAuthentication.face.FaceAuthenticationActivity
import com.dubitaxi.utils.statusBarTransparent
import com.dubitaxi.ui.selectUserType.home.HomeActivity


class BioMetricAuthenticationActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityBioMetricAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBioMetricAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()

        initView()
        initControl()
        mObserver()
    }

    override fun initView() {

    }

    override fun initControl() {
        binding.mFaceId.setOnClickListener(this)
        binding.mSkip.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mFaceId -> {
                startActivity(Intent(this, FaceAuthenticationActivity::class.java))
            }
            R.id.mSkip -> {
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra("Driver","Driver")
                })
            }
        }

    }
}