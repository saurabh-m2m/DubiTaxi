package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityVehicleListBinding
import com.dubitaxi.databinding.CancleRequestTripDailogBinding
import com.dubitaxi.databinding.SerachDriverDailogBinding
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.home.HomeActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.VehicleListAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.paymentOption.PaymentOptionActivity
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.GPSTracker
import com.dubitaxi.utils.NetworkUtils
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.checkGpsPermission
import com.dubitaxi.utils.checkGpsPermission2
import com.dubitaxi.utils.checkLocationPermission
import com.dubitaxi.utils.enableDeviceGPS
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.math.roundToInt


class VehicleListActivity : BaseActivity(), View.OnClickListener, OnMapReadyCallback {
    lateinit var binding: ActivityVehicleListBinding
    private var supportMapFragment: SupportMapFragment? = null
    private lateinit var mMap: GoogleMap
    var mChecked = false
    private var latitude = 0.0
    private var longitude = 0.0
    var handler: Handler? = Handler()
    private var gpsTracker: GPSTracker? = null
    var mAddresses = ""
    var radioSelect = 0
    var petSize: String = ""
    var amountRide: Int? = 0
    var positionRide: Int? = 0
    var idRide: String? = ""
    var fareID: String? = ""

    var mPickUpNewLatitute: Double = 0.0
    var mPickUpNewLongtitute: Double = 0.0
    var mDropNewLatitute: Double = 0.0
    var mDropNewLongtitute: Double = 0.0

    lateinit var location: Location
    private lateinit var countDownTimer: CountDownTimer
    private val durationMinutes: Long = 2 // 2 minutes in this example
    private val durationMillis: Long = durationMinutes * 60 * 1000
    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null
    lateinit var mGetVehicleListUser: PassengerViewModel
    var mLayout:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {
        mPickUpNewLatitute=intent.getDoubleExtra("mPickUpNewLatitute",0.0)
        mPickUpNewLongtitute=intent.getDoubleExtra("mPickUpNewLongtitute",0.0)
        mDropNewLatitute=intent.getDoubleExtra("mDropNewLatitute",0.0)
        mDropNewLongtitute=intent.getDoubleExtra("mDropNewLongtitute",0.0)

        showToast(mPickUpNewLatitute.toString())
        showToast(mPickUpNewLongtitute.toString())
        showToast(mDropNewLatitute.toString())
        showToast(mDropNewLongtitute.toString())


        mGetVehicleListUser = ViewModelProvider(this)[PassengerViewModel::class.java]
        Places.initialize(this, "AIzaSyA4L_dBeJDWuqtBT9ZOOUhjigbh6Lxi43s")
        gpsTracker = GPSTracker(this)
        // gpsTracker = GPSTracker(this)
        loadMap()
        mGetVehicleListUser.mGetVehicleListUserHitApi(
            access_token = SharedPreference.accessToken,
            request = GetVehicleTypeListRequest(
                dropLat = mDropNewLatitute,
                dropLong = mDropNewLongtitute,
                pickupLat = mPickUpNewLatitute,
                pickupLong = mPickUpNewLongtitute,
                serviceType = 0
            )
        )
        ///..........................GetFareDetailApiii...................//

    }

    override fun initControl() {
        binding.backpressVehicleList.setOnClickListener(this)
        binding.tvBankOption.setOnClickListener(this)
        binding.ivCalender.setOnClickListener(this)
        binding.btnConfirmDubiPrime.setOnClickListener(this)
        binding.radioButton1.setOnClickListener(this)
        binding.radioButton2.setOnClickListener(this)
        binding.btnSubmitCancel.setOnClickListener(this)
        binding.mCancleArriving.setOnClickListener(this)
        binding.ccCancelReason.setOnClickListener(this)
        binding.mCancleRequestbtn.setOnClickListener(this)
        binding.btnNoTrip.setOnClickListener(this)
        binding.btnCancelReason.setOnClickListener(this)
        binding.reason5.setOnClickListener(this)
        binding.checkboxSmall.setOnClickListener(this)
        binding.checkboxMedium.setOnClickListener(this)
        binding.checkboxLarge.setOnClickListener(this)

    }

    override fun mObserver() {
        mGetVehicleListUser.mGetVehicleListViewModel.observe(this) {
            binding.recyclerVehicleList.adapter = VehicleListAdapter(this, it.response?.result!!,
                object : VehicleListAdapter.selectVehicle {
                    override fun selectRide(position: Int, price: Int, id: String) {
                        positionRide = position
                        amountRide = price
                        idRide = id
                    }

                })
        }
        mGetVehicleListUser.mCreateFareConfirmViewModel.observe(this) {
            binding.constraintVehicleList.visibility = View.GONE
            binding.ccCancelRequest.visibility = View.VISIBLE
            timer()

            fareID = it.response?.result?.id

            mGetVehicleListUser.mCreateFareDetailHitApi(
                access_token = SharedPreference.accessToken, request = GetFareDetailsRequest(
                    fareId = it.response?.result?.id
                )
            )
        }
        mGetVehicleListUser.mCancelFareViewModel.observe(this) {
            binding.ccCancelTrip.visibility = View.GONE
            binding.constraintVehicleList.visibility = View.VISIBLE
        }
        mGetVehicleListUser.mCreateFareDetailViewModel.observe(this) {

            binding.CancleTripTitle.text = it.response?.result?.vehicleType?.description
            binding.mCanclePrice.text = "€ " + it.response?.result?.actualAmount.toString()
            Glide.with(this).load(it.response?.result?.vehicleType?.icon)
                .placeholder(R.drawable.car).into(binding.mCancleTripImage)

            /////............................ArrivingSoon Set Data........///////
            if (it.response?.result?.driverId?.fullName != null) {
                binding.mDriverName.text = it.response?.result?.driverId?.fullName.toString()
            }
            Glide.with(this).load(it.response?.result?.driverId?.profilePicture)
                .placeholder(R.drawable.placeholder_ic).into(binding.mDriverImage)
            binding.mOTP.text = it.response?.result?.otp.toString()
            binding.midmMin.text = ("€ " + it.response?.result?.amount ?: 0).toString()
            binding.mVehiclName.text = ": " + it.response?.result?.vehicleType?.description
            if (it.response?.result?.vehicleId?.vehicleNumber != null) {
                binding.mVehiclNumber.text =
                    ": " + it.response?.result?.vehicleId?.vehicleNumber
            }
            if (it.response?.result?.vehicleId?.color != null) {
                binding.mVehicleColor.text = ": " + it.response?.result?.vehicleId?.color
            }
        }
        mGetVehicleListUser.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mGetVehicleListUser.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.constraintVehicleList.visibility = View.GONE

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backpressVehicleList -> {
                onBackPressed()
                binding.constraintVehicleList.visibility = View.GONE
            }

            R.id.radioButton1 -> {
                radioSelect = 0
                binding.SelectSizeLayout.visibility = View.VISIBLE
            }
            R.id.checkboxSmall -> {
                petSize = "0"
                binding.checkboxSmall.isChecked = true
                binding.checkboxMedium.isChecked = false
                binding.checkboxLarge.isChecked = false
            }
            R.id.checkboxMedium -> {
                petSize = "1"
                binding.checkboxSmall.isChecked = false
                binding.checkboxMedium.isChecked = true
                binding.checkboxLarge.isChecked = false
            }
            R.id.checkboxLarge -> {
                petSize = "2"
                binding.checkboxLarge.isChecked = true
                binding.checkboxSmall.isChecked = false
                binding.checkboxMedium.isChecked = false
            }

            R.id.radioButton2 -> {
                radioSelect = 1
                binding.SelectSizeLayout.visibility = View.GONE
            }

            R.id.tvBankOption -> {
                startActivity(Intent(this, PaymentOptionActivity::class.java))
            }

            R.id.ivCalender -> {
                //openBottomSheet()
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra("schedule", "schedule")
                })
            }

            R.id.btnConfirmDubiPrime -> {

                if (NetworkUtils.isInternetAvailable(this)) {
                    when {
                        binding.radioButton1.isChecked == false -> {
                            showToast("Please selectTaxi For")
                        }

                        idRide.isNullOrEmpty() -> {
                            showToast("Please selectVehicle Type")
                        }
                        else -> {
                            mGetVehicleListUser.mCreateFareHitApi(
                                access_token = SharedPreference.accessToken,
                                request = CreateFareConfirmRequest(
                                    actualAmount = amountRide,
                                    amount = amountRide,
                                    currency = "peru",
                                    dropLatitude = mDropNewLatitute,
                                    dropLongitude = mDropNewLongtitute,
                                    paymentMethod = 0,
                                    pickupLatitude = mPickUpNewLatitute,
                                    pickupLongitude = mPickUpNewLongtitute,
                                    scheduledDate = "",
                                    scheduledTime = "",
                                    stopLatitude = 0.0,
                                    stopLongitude = 0.0 ,
                                    taxiFor = radioSelect,
                                    vehicleType = idRide, petSize = radioSelect
                                )
                            )
                        }
                    }

                } else {
                    showToast(resources.getString(R.string.error_internet))
                }

            }
            R.id.mCancleRequestbtn -> {
                binding.constraintVehicleList.visibility = View.VISIBLE
                binding.ccCancelRequest.visibility = View.GONE
                countDownTimer.onFinish()
                countDownTimer.cancel()
            }

            R.id.btnSubmitCancel -> {
                binding.ccCancelTrip.visibility = View.VISIBLE
                binding.ccCancelReason.visibility = View.GONE
            }

            R.id.mCancleArriving -> {
                binding.ccArrivingSoon.visibility = View.GONE
                binding.ccCancelReason.visibility = View.VISIBLE
            }
            R.id.btnNoTrip -> {
                mGetVehicleListUser.mCancelFareHitApi(
                    access_token = SharedPreference.accessToken,
                    request = CancelFareRequest(fareId = fareID)
                )

            }
            R.id.btnCancelReason -> {
                binding.ccArrivingSoon.visibility = View.VISIBLE
                binding.ccCancelReason.visibility = View.GONE
            }
            R.id.reason5 -> {
                if (mChecked) {
                    binding.EtmDescribeReason.visibility = View.GONE
                    mChecked = false
                } else {
                    binding.EtmDescribeReason.visibility = View.VISIBLE
                    mChecked = true
                }
            }
        }
    }

    fun timer() {
        var secondsLeft = 0
        object : CountDownTimer(10000, 100) {
            override fun onTick(ms: Long) {
                if ((ms.toFloat() / 1000.0f).roundToInt() != secondsLeft) {
                    secondsLeft = (ms.toFloat() / 1000.0f).roundToInt()
                    binding.mCancleTimerCount.text = secondsLeft.toString()
                }
                Log.i("test", "ms=$ms till finished=$secondsLeft")
            }

            override fun onFinish() {
                binding.mCancleTimerCount.text = "00:00"
                binding.ccArrivingSoon.visibility = View.VISIBLE
                binding.ccCancelRequest.visibility = View.GONE
            }
        }.start()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener(onCameraIdleListener)
        setMarkerOnMap()
        setupMap()
        setMarkerOnMap()
        setupMap()
    }

    private fun loadMap() {
        latitude = gpsTracker?.location?.latitude ?: 0.0
        longitude = gpsTracker?.location?.longitude ?: 0.0
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.mapVehicleList) as SupportMapFragment
        supportMapFragment!!.getMapAsync(this)
    }

    private fun configureCameraIdle() {
        try {
            onCameraIdleListener = GoogleMap.OnCameraIdleListener {
                val latLng = mMap.cameraPosition.target
                onLocationChanged(latLng)
            }
        } catch (e: Exception) {
            if (checkGpsPermission(this)) {
                if (!checkLocationPermission(this)) {
                    // showToast(getString(R.string.need_location_permission_for_fetch_current_
                    // location))
                }
            } else {
                enableDeviceGPS(this)
            }
        }
    }

    private fun onLocationChanged(latLng: LatLng) {
        latitude = latLng.latitude
        longitude = latLng.longitude
    }

    private fun setMarkerOnMap() {
        statusBarTransparent()
        try {
            if (latitude != null && longitude != null) {
                val userCurrentLatLon = LatLng(latitude!!, longitude!!)
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
                mMap.animateCamera(CameraUpdateFactory.newLatLng(userCurrentLatLon))
                //findAddressFromLatLng(userCurrentLatLon)
                /*SharedPreferenceUtil.getInstance(this).userLatitude = latitude.toString()
                SharedPreferenceUtil.getInstance(this).userLongitude = longitude.toString()*/


            }
        } catch (e: Exception) {
            if (checkGpsPermission2(this)) {
                if (!checkLocationPermission(this)) {
                    //showToast(getString(R.string.need_location_permission_for_fetch_current_location))
                }
            } else {
                enableDeviceGPS(this)
            }
        }
    }

    private fun setupMap() {
        location = GPSTracker(this).location!!
        val mLatLng = LatLng((gpsTracker?.latitude ?: 0.0), (gpsTracker?.longitude ?: 0.0))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15F))
    }

    private fun inItTextChangeListener() {
        handler = Handler()

    }


    private fun mCancelRequestBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.MyBottomThemeA)
        val bottomSheetView = layoutInflater.inflate(R.layout.cancle_request_trip_dailog, null)
        val binding = CancleRequestTripDailogBinding.bind(bottomSheetView)
        bottomSheetDialog?.apply {
            setContentView(bottomSheetView)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            countDownTimer = object : CountDownTimer(durationMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val minutesRemaining = (millisUntilFinished / 1000) / 60
                    val secondsRemaining = (millisUntilFinished / 1000) % 60
                    val timeString = String.format("%02d:%02d", minutesRemaining, secondsRemaining)
                    binding.mCancleTimerCount.text = timeString

                }

                override fun onFinish() {
                    binding.mCancleTimerCount.text = "00:00"
                    binding
                }

            }
            countDownTimer.start()


        }?.show()
    }

    fun mSerachDriverBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.MyBottomThemeA)
        val bottomSheetView = layoutInflater.inflate(R.layout.serach_driver_dailog, null)
        val binding = SerachDriverDailogBinding.bind(bottomSheetView)
        bottomSheetDialog?.apply {
            setContentView(bottomSheetView)
            setCancelable(true)
            setCanceledOnTouchOutside(true)


        }?.show()

    }

    private fun mEconomicalVehicleBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.MyBottomThemeA)
        val bottomSheetView = layoutInflater.inflate(R.layout.economical_vehicle_dailog, null)
        val mCancelbtn = findViewById<TextView>(R.id.mCancel)
        val mContinuebtn = findViewById<TextView>(R.id.mContinue)
        bottomSheetDialog?.apply {
            setContentView(bottomSheetView)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            mCancelbtn.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            mContinuebtn.setOnClickListener {

            }

        }?.show()
    }


}
