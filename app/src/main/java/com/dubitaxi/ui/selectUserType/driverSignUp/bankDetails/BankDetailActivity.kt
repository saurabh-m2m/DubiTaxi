package com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityBankDetailBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.ui.selectUserType.biometricAuthentication.BioMetricAuthenticationActivity
import com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails.request.AddBankDetailsRequest
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.NetworkUtils
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent

class BankDetailActivity : BaseActivity(), View.OnClickListener {
    lateinit var mAddDriverBankDetailsViewModel: DriverViewModel
    lateinit var binding: ActivityBankDetailBinding
    var TermsandConditions = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {
        mAddDriverBankDetailsViewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
    }

    override fun initControl() {
        binding.btnMBankSave.setOnClickListener(this)
    }

    override fun mObserver() {
        mAddDriverBankDetailsViewModel.mAddDriverBankDetailsViewModel.observe(this) {
            startActivity(Intent(this, BioMetricAuthenticationActivity::class.java))
            finish()
        }
        mAddDriverBankDetailsViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mAddDriverBankDetailsViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_mBankSave -> {
                mDriverBankDetailsHitApi()
            }

            R.id.checkBox -> {
                if (TermsandConditions == false) {
                    binding.checkBox.isChecked = true
                }

            }
        }
    }

    fun mDriverBankDetailsHitApi() {
        if (NetworkUtils.isInternetAvailable(this)) {
            when {
                binding.EnterAccountNumber.text.isNullOrEmpty() -> {
                    showToast("Enter Account Number CCI")
                }

                binding.EnterAccountNumber.text!!.length !in 8..20 -> {
                    showToast("Enter  valid Account Number CCI")
                }

                binding.EtDriverName.text.isNullOrEmpty() -> {
                    showToast("Enter Account Holder Name")
                }

                binding.EtBrancheName.text.isNullOrEmpty() -> {
                    showToast("Enter Bank Branch Name")
                }

                binding.YapeNumber.text.isNullOrEmpty() -> {
                    showToast("Please Enter Yape number")
                }

                binding.EtDriverPlin.text.isNullOrEmpty() -> {
                    showToast("Please Enter Plin number")
                }
              /*binding.EtDriverPlin.text!!.length !in 4..4 -> {
                    showToast("Please valid Pin number")
                }*/


                /*!binding.checkBox.isChecked -> {
                    showToast("Agree Terms and Conditions")
                }*/

                else -> {
                    mAddDriverBankDetailsViewModel.mAddDriverBankDetailsHitApi(
                        access_token = SharedPreference.accessToken,
                        request = AddBankDetailsRequest(
                            acc_holder_name = binding.EtDriverName.text.toString(),
                            acc_no = binding.EnterAccountNumber.text.toString(),
                            accepted_tnc = TermsandConditions,
                            bank_branch = binding.EtBrancheName.text.toString(),
                            plin_no = binding.EtDriverPlin.text.toString(),
                            yape_no = binding.YapeNumber.text.toString()
                        )
                    )
                }
            }
        }else{
            showToast(resources.getString(R.string.error_internet))
        }
    }

}