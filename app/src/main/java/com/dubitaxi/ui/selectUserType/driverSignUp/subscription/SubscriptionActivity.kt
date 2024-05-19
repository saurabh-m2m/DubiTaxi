package com.dubitaxi.ui.selectUserType.driverSignUp.subscription

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivitySubscriptionBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.ui.selectUserType.driverSignUp.subscription.request.DriverPurchaseSubscriptionRequest
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparentWithWhite

class SubscriptionActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivitySubscriptionBinding
    lateinit var mGetSubscriptionPlanViewModelResponse: DriverViewModel
    var mPlanId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()
        statusBarTransparentWithWhite()
    }

    override fun initView() {
        mGetSubscriptionPlanViewModelResponse =
            ViewModelProvider(this).get(DriverViewModel::class.java)
        mGetSubscriptionPlanViewModelResponse.mDriverSubscriptionPlanHitAPi(SharedPreference.accessToken)

    }

    override fun initControl() {
        binding.btnBuy.setOnClickListener(this)
        binding.backpressSubscription.setOnClickListener(this)
    }

    override fun mObserver() {
        mGetSubscriptionPlanViewModelResponse.mDriverGetSubscriptionPlan.observe(this) {
            binding.recyclerSubscription.adapter = AdapterSubscription(this, it.response!!.plans, object : AdapterSubscription.mSelectSubscriptionPlan {
                    override fun mPlanSelected(id: String, position: Int) {
                        mPlanId = id
                    }
                })
        }
        mGetSubscriptionPlanViewModelResponse.mDriverSubsriptionPurchasePlanViewModel.observe(this) {
            startActivity(Intent(this, PaymentMethodActivity::class.java))
        }
        mGetSubscriptionPlanViewModelResponse.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mGetSubscriptionPlanViewModelResponse.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBuy -> {
                mGetSubscriptionPlanViewModelResponse.mDriverSubscriptionPurchasePlanHitAPi(SharedPreference.accessToken, request = DriverPurchaseSubscriptionRequest(plan_id = mPlanId))
            }

            R.id.backpressSubscription -> {
                onBackPressed()
            }
        }
    }
}