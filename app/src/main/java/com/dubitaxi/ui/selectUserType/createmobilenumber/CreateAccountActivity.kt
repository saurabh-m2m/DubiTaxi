package com.dubitaxi.ui.selectUserType.createmobilenumber

import android.content.Intent
import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityPassengerPhoneNoBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.driverSignUp.request.DriverCreateAccountRequest
import com.dubitaxi.ui.selectUserType.otpVerifiction.OTPVerificatonActivity
import com.dubitaxi.ui.selectUserType.home.HomeActivity
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.GPSTracker
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.SharedPreferenceUtil
import com.dubitaxi.utils.statusBarTransparent
import com.dubitaxi.utils.Validation
import com.dubitaxi.utils.checkGpsPermission
import com.dubitaxi.utils.checkGpsPermission2
import com.dubitaxi.utils.checkLocationPermission
import com.dubitaxi.utils.enableDeviceGPS
import com.dubitaxi.utils.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.util.Locale


class CreateAccountActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityPassengerPhoneNoBinding
    private var mlatitude:Double= 0.0
    private var mlongitude:Double= 0.0

    var mAddresses = ""
    lateinit var location: Location
    private var gpsTracker: GPSTracker? = null
    lateinit var mUseCreateAccountViewModel: PassengerViewModel
    lateinit var mDriverCreateAccountViewModel: DriverViewModel
    var mPhoneNumber = ""
    var mCuntryCode = ""
    var isUserProfileComplete = 0
    var mDriverProfileCompleted = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerPhoneNoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // openBottomSheet()
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
        //loadMap()


    }

    override fun initView() {
        gpsTracker= GPSTracker(this)
        mlatitude=gpsTracker?.latitude?:0.0
        mlongitude=gpsTracker?.longitude?:0.0
        SharedPreference.userPickLatitude = gpsTracker?.latitude.toString()
        SharedPreference.userPickLongitude = gpsTracker?.longitude.toString()
        binding.ccp.setTypeFace(null, Typeface.BOLD)
        binding.ccp.setCountryForPhoneCode(+51)
        binding.EtPhonePassenger.setText(intent.getStringExtra("Phone"))
        mUseCreateAccountViewModel = ViewModelProvider(this).get(PassengerViewModel::class.java)
        mDriverCreateAccountViewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
    }

    override fun initControl() {
        binding.btnPassengerContinueP.setOnClickListener(this)
        binding.mback.setOnClickListener(this)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
            if (resultCode === RESULT_OK) {
                val number1: String = data?.getStringExtra("number").toString()
                val countryCode: String = data?.getStringExtra("countryCode").toString()
            }
        }
    }

    override fun mObserver() {
        //USER--------------------------------------------------------------------------------------
        mUseCreateAccountViewModel.mUserProfileCompleteViewModel.observe(this) {
            SharedPreference.accessToken = it.response?.accessToken.toString()
            isUserProfileComplete = it.response?.isProfileComplete!!.toInt()
            if (it.response.fullName!=null){
                   SharedPreference.userName = it.response.fullName
               }
               if (it.response.profilePicture!=null){
                   SharedPreference.userprofilePic = it.response.profilePicture
               }
            val i = Intent(this, OTPVerificatonActivity::class.java)
                .putExtra("number", mPhoneNumber)
                .putExtra("countryCode", mCuntryCode)
            startActivityForResult(i, 1)
        }
        //Driver-----------------------------------------------------------------------------------

        mDriverCreateAccountViewModel.mDriverCreateAccount.observe(this) {
            SharedPreference.accessToken = it.response?.accessToken.toString()
            if (it.response?.other?.fullName!=null){
                SharedPreference.driverName = it.response.other?.fullName
            }
            if (it.response?.other?.profilePicture!=null){
                SharedPreference.driverprofilePic = it.response?.other?.profilePicture
            }

            val i = Intent(this, OTPVerificatonActivity::class.java)
                .putExtra("number", mPhoneNumber)
                .putExtra("countryCode", mCuntryCode)
            startActivityForResult(i, 1)
        }

        //USER--------------------------------------------------------------------------------------

        mUseCreateAccountViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mDriverCreateAccountViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
        //Driver-----------------------------------------------------------------------------------

        mDriverCreateAccountViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mUseCreateAccountViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_passenger_continueP -> {
                SharedPreference.mobileNumber = binding.EtPhonePassenger.text.toString()
                SharedPreference.countryCode = binding.ccp.selectedCountryCodeWithPlus
                mPhoneNumber = binding.EtPhonePassenger.text.toString()
                mCuntryCode = binding.ccp.selectedCountryCodeWithPlus
                if (Validation().mUserSingUp(this, binding.EtPhonePassenger)) {
                    if (SharedPreference.userType == "0") {
                        mUseCreateAccountViewModel.mUserCreateAccountHitApi(
                            country_code = binding.ccp.selectedCountryCodeWithPlus,
                            phone_number = binding.EtPhonePassenger.text.toString(),
                            device_token = "ABC",
                            device_type = "1",
                            lat = mlatitude,
                            long = mlongitude,
                            country = "India",
                        )
                    } else {
                        mDriverCreateAccountViewModel.mDriverCreateAccountHitApi(
                            request = DriverCreateAccountRequest(
                                phone = binding.EtPhonePassenger.text.toString(),
                                country_code = binding.ccp.selectedCountryCodeWithPlus,
                                lat = mlatitude,
                                long = mlongitude
                            )
                        )
                    }
                }

            }

            R.id.mback -> {
                onBackPressed()
            }


        }
    }


}