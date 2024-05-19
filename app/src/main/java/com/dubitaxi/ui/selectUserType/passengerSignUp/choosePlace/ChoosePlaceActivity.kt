package com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityChoosePlaceBinding
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.ChoosePlaceAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.SetOnMapActivity
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent



class ChoosePlaceActivity : BaseActivity(), View.OnClickListener, ChoosePlaceAdapter.onAddressSelection {
    lateinit var binding: ActivityChoosePlaceBinding
    lateinit var mGetSavedAddressResponse: PassengerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoosePlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun onResume() {
        super.onResume()
        mGetSavedAddressResponse.mGetSavedAddressHitApi(SharedPreference.accessToken)
    }

    override fun initView() {
        mGetSavedAddressResponse = ViewModelProvider(this).get(PassengerViewModel::class.java)

    }

    override fun initControl() {
        binding.backpressCP.setOnClickListener(this)
        binding.tvAddSavePlace.setOnClickListener(this)
    }

    override fun mObserver() {
        mGetSavedAddressResponse.mGetSavedAddressViewModel.observe(this) {
            val mSavedAddressList = it.response.result

            binding.recyclerChoosePlace.adapter = ChoosePlaceAdapter(this, this, mSavedAddressList)
        }
        mGetSavedAddressResponse.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mGetSavedAddressResponse.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backpressCP -> {
                onBackPressed()
            }

            R.id.tvAddSavePlace -> {
                startActivity(Intent(this, SetOnMapActivity::class.java).apply {
                    putExtra("newAddress","newAddress")
                })
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onSelectAddress(officialName: String, addressName: String, lat: Double, long: Double, id: String) {
        val distance = FloatArray(1)
          showToast("Done")

    }
}

data class AddressDetails(
    var officialName: String,
    var userLatitude: Double,
    var userLongitude: Double,
    var AddressId: String,
    var addressName: String)

