package com.dubitaxi.ui.selectUserType.passengerSignUp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityBankDetailBinding.inflate
import com.dubitaxi.databinding.ActivityDriverDetailBinding
import com.dubitaxi.databinding.ActivityTermAndConditionBinding
import com.dubitaxi.utils.statusBarTransparent

class TermAndConditionActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityTermAndConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermAndConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
        loadWebView()
    }

    private fun loadWebView() {
        binding.webview1.loadUrl("https://petsworld.pet/Politicas_Privacidad")
        binding.webview1.settings.javaScriptEnabled = true
        binding.webview1.webViewClient = WebViewClient()
    }

    override fun initView() {
        binding.btnReject.setOnClickListener {
            binding.btnReject.setBackgroundResource(R.drawable.terms_accept_bg)
            binding.btnReject.setTextColor(Color.parseColor("#FFFFFF"))
            binding.btnAccept.setBackgroundResource(R.drawable.terms_reject_bg)
            binding.btnAccept.setTextColor(Color.parseColor("#000000"))
            val returnIntent = Intent()
            returnIntent.putExtra("result", "false");
            setResult(RESULT_OK, returnIntent)
            onBackPressed()
        }
        binding.btnAccept.setOnClickListener {
            binding.btnReject.setBackgroundResource(R.drawable.terms_reject_bg)
            binding.btnReject.setTextColor(Color.parseColor("#000000"))
            binding.btnAccept.setBackgroundResource(R.drawable.terms_accept_bg)
            binding.btnAccept.setTextColor(Color.parseColor("#FFFFFF"))
            val returnIntent = Intent()
            returnIntent.putExtra("result", "true");
            setResult(RESULT_OK, returnIntent)
            onBackPressed()
        }
    }

    override fun initControl() {
        binding.ivBack.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

}