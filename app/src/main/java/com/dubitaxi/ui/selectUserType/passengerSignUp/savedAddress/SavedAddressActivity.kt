package com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityChoosePlaceBinding
import com.dubitaxi.databinding.ActivitySavedAddressBinding
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.ChoosePlaceAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo.GetSavedAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.request.UserRemoveAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.SetOnMapActivity
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent

class SavedAddressActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivitySavedAddressBinding
    lateinit var mUserSavedGetSavedAddressResponse: PassengerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {
        statusBarTransparent()
        mUserSavedGetSavedAddressResponse =
            ViewModelProvider(this).get(PassengerViewModel::class.java)
        mUserSavedGetSavedAddressResponse.mGetSavedAddressHitApi(SharedPreference.accessToken)
    }

    override fun initControl() {
        binding.backpressCP.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        mUserSavedGetSavedAddressResponse.mGetSavedAddressHitApi(SharedPreference.accessToken)
    }

    override fun mObserver() {
        mUserSavedGetSavedAddressResponse.mGetSavedAddressViewModel.observe(this) {
            binding.mUserSavedAddressRecyclerView.adapter = UserSavedAddressAdapter(
                this,
                it.response.result,
                object : UserSavedAddressAdapter.mEditAddress {
                    override fun mUserEditAddress(
                        position: Int,
                        listener: String,
                        mAddressList: GetSavedAddressResponse.Response.Result?
                    ) {
                        if (listener == "Edit") {
                            startActivity(
                                Intent(this@SavedAddressActivity, SetOnMapActivity::class.java)
                                    .putExtra("EditAddress", mAddressList)
                                    .putExtra("Edit", "Edit")
                            )
                        } else {
                            mUserSavedGetSavedAddressResponse.mUserRemoveAddressHitApi(
                                SharedPreference.accessToken,
                                request = UserRemoveAddressRequest(addressId = mAddressList!!.id),
                            )
                        }
                    }
                })
        }


        mUserSavedGetSavedAddressResponse.mUserRemoveAddressViewModel.observe(this) {
            mUserSavedGetSavedAddressResponse.mGetSavedAddressHitApi(SharedPreference.accessToken)
        }

        mUserSavedGetSavedAddressResponse.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mUserSavedGetSavedAddressResponse.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backpressCP -> {
                onBackPressed()
            }
        }
    }
}