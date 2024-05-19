package com.dubitaxi.ui.selectUserType.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityHomeBinding
import com.dubitaxi.databinding.CalenderItemBinding
import com.dubitaxi.databinding.DialogLogoutBinding
import com.dubitaxi.databinding.DialogPurchaseSubscriptionBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.SelectUserTypeActivity
import com.dubitaxi.ui.selectUserType.driverSignUp.subscription.SubscriptionActivity
import com.dubitaxi.ui.selectUserType.home.adapter.NewTripRequestAdapter
import com.dubitaxi.ui.selectUserType.home.request.DriverToggleActiveRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.ViewPagerBannerAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.RecentPickupPlaceAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.MyTripActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.SavedAddressActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.EnterDropOffLocationActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.UserRecentilyAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vo.GetBannerResponse
import com.dubitaxi.utils.DayListener
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.GPSTracker
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.SharedPreferenceUtil
import com.dubitaxi.utils.checkGpsPermission
import com.dubitaxi.utils.checkGpsPermission2
import com.dubitaxi.utils.checkLocationPermission
import com.dubitaxi.utils.datePicker
import com.dubitaxi.utils.enableDeviceGPS
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.status2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar
import java.util.Locale

class HomeActivity : BaseActivity(), View.OnClickListener, OnMapReadyCallback {
    lateinit var binding: ActivityHomeBinding
    var selectedFromDueDateTimeStamp = ""
    lateinit var mUserGetBanner: PassengerViewModel
    lateinit var mDriverToogleView: DriverViewModel
    var backPressedTime: Long = 0
    private var mBannerList = ArrayList<GetBannerResponse.Response.Result>()
    private lateinit var adapter: ViewPagerBannerAdapter
    private var numPages = 0
    private var currentPosition = 0
    var handler: Handler? = Handler()
    var DELAY_MS: Long = 3000
    private var currentPageBannerTop = 0
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var supportMapFragment: SupportMapFragment? = null
    private lateinit var mMap: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0
    private var gpsTracker: GPSTracker? = null
    var mAddresses = ""
    var mbutton: Boolean = false
    lateinit var location: Location
    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null
    val isChecked = false
    var mToggleButton: Boolean = false

    var selectedTime=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        status2(this, this)
        initView()
        initControl()
        mObserver()
    }

    override fun onResume() {
        super.onResume()
        //// user side --------------------------------------------------------
        if (intent.getStringExtra("user").equals("user")) {
            mUserGetBanner.mUserRecentlyAddressHitApi(SharedPreference.accessToken)
            mUserGetBanner.mUserGetBannersHitApi(SharedPreference.accessToken)
            Glide.with(this).load(SharedPreference.userprofilePic).into(binding.profilePic)
            binding.name.text = SharedPreference.userName
            /// driver side----------------------------------------------
        } else if (intent.getStringExtra("Driver").equals("Driver")) {
            Glide.with(this).load(SharedPreference.driverprofilePic).into(binding.profilePic)
            binding.name.text = SharedPreference.driverName
            //user side ----------------------------------------------------------
        } else if (intent.getStringExtra("schedule").equals("schedule")) {
            mUserGetBanner.mUserRecentlyAddressHitApi(SharedPreference.accessToken)
            mUserGetBanner.mUserGetBannersHitApi(SharedPreference.accessToken)
            Glide.with(this).load(SharedPreference.userprofilePic).into(binding.profilePic)
            binding.name.text = SharedPreference.userName
            /// driver side----------------------------------------------
        } else if (intent.getStringExtra("AddCard").equals("AddCard")) {
            Glide.with(this).load(SharedPreference.driverprofilePic).into(binding.profilePic)
            binding.name.text = SharedPreference.driverName
        }

    }

    override fun initView() {
        binding.recyclerNewRequest.adapter = NewTripRequestAdapter(this)
        binding.ivToggleCar.isChecked = SharedPreference.isDriverActive
        if (SharedPreference.isDriverActive) {
            binding.btnYouareoffline.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.radio_box_color
                )
            )
        } else {
            binding.btnYouareoffline.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        mUserGetBanner = ViewModelProvider(this)[PassengerViewModel::class.java]
        mDriverToogleView = ViewModelProvider(this)[DriverViewModel::class.java]
        Places.initialize(this, "AIzaSyA4L_dBeJDWuqtBT9ZOOUhjigbh6Lxi43s")
        gpsTracker = GPSTracker(this)
        binding.One.setBackgroundResource(R.drawable.bg)
        binding.mTaxi.setTextColor(Color.parseColor("#FFFFFF"))
        binding.mTaxiImageA.setImageResource(R.drawable.taxi_location_ic)
        inItTextChangeListener()
        loadMap()
        configureCameraIdle()



        if (intent.getStringExtra("user").equals("user")) {
            binding.ivToggleCar.visibility = View.GONE
            binding.mPinPoint.visibility = View.VISIBLE
            binding.midPoint.visibility = View.VISIBLE
            binding.constraintLayout.visibility = View.VISIBLE
            binding.ivRedeemPoints.visibility = View.VISIBLE
            binding.btnYouareoffline.visibility = View.GONE
            binding.ivCar.visibility = View.GONE
            binding.ivDiamondDriver.visibility = View.GONE
            binding.tvRating.visibility = View.GONE
            binding.mTripRequest.visibility = View.GONE
            binding.recyclerNewRequest.visibility = View.GONE
            binding.constraintSubscription.visibility = View.GONE
            binding.constraintMyEarnings.visibility = View.GONE
            binding.constraintProfilePackage.visibility = View.VISIBLE
            binding.constraintProfileMoving.visibility = View.VISIBLE
            binding.constraintParcelOrder.visibility = View.VISIBLE
            binding.constraintSaveAddress.visibility = View.VISIBLE
            binding.constraintRewardPoint.visibility = View.VISIBLE
            binding.mLayoutA.visibility = View.GONE
            binding.LayoytB.visibility = View.GONE
        }
        else if (intent.getStringExtra("Driver").equals("Driver")) {
            binding.ivToggleCar.visibility = View.VISIBLE
            binding.btnYouareoffline.visibility = View.VISIBLE
            binding.ivCar.visibility = View.VISIBLE
            binding.ivDiamondDriver.visibility = View.VISIBLE
            binding.tvRating.visibility = View.VISIBLE
            binding.midPoint.visibility = View.GONE
            binding.mPinPoint.visibility = View.GONE
            binding.constraintLayout.visibility = View.GONE
            binding.ivRedeemPoints.visibility = View.GONE
            binding.mTripRequest.visibility = View.GONE
            binding.findTripLayout.visibility = View.GONE
            binding.constraintProfilePackage.visibility = View.GONE
            binding.constraintProfileMoving.visibility = View.GONE
            binding.constraintParcelOrder.visibility = View.GONE
            binding.constraintSaveAddress.visibility = View.GONE
            binding.constraintRewardPoint.visibility = View.GONE
            binding.constraintSubscription.visibility = View.VISIBLE
            binding.constraintMyEarnings.visibility = View.VISIBLE
            binding.btnYouareoffline.visibility = View.VISIBLE


        }
        if (intent.getStringExtra("AddCard").equals("AddCard")) {
            binding.btnYouareoffline.visibility = View.GONE
            binding.constraintParcelOrder.visibility = View.GONE
            binding.constraintProfilePackage.visibility = View.GONE
            binding.constraintProfileMoving.visibility = View.GONE
            binding.constraintParcelOrder.visibility = View.GONE
            binding.constraintSaveAddress.visibility = View.GONE
            binding.constraintRewardPoint.visibility = View.GONE
            binding.midPoint.visibility = View.GONE
            binding.mPinPoint.visibility = View.GONE
            binding.constraintLayout.visibility = View.GONE
            binding.ivRedeemPoints.visibility = View.GONE
            binding.mTripRequest.visibility = View.GONE
            Handler().postDelayed({
                binding.findTripLayout.visibility = View.VISIBLE
                binding.ivToggleCar.visibility = View.VISIBLE
            }, 2000)
        }

        if (intent.getStringExtra("schedule").equals("schedule")) {
            binding.constraintLayout.visibility = View.GONE
            binding.constraintSubscription.visibility = View.GONE
            binding.constraintMyEarnings.visibility = View.GONE
            Handler().postDelayed({
                binding.mLayoutA.visibility = View.VISIBLE
                binding.midPoint.visibility = View.VISIBLE
                binding.constraintLayout.visibility = View.GONE

            }, 500)
        }

    }

    private fun inItTextChangeListener() {
        handler = Handler()
    }

    override fun initControl() {
        selectedTime=System.currentTimeMillis().toString()
        binding.mPinPoint.setOnClickListener(this)
        binding.SideButton.setOnClickListener(this)
        binding.ivBackpress.setOnClickListener(this)
        binding.mConstraLayout2.setOnClickListener(this)
        binding.One.setOnClickListener(this)
        binding.Two.setOnClickListener(this)
        binding.Three.setOnClickListener(this)
        binding.ivToggleCar.setOnClickListener(this)
        binding.constraintLogOut.setOnClickListener(this)
        binding.constraintParcelOrder.setOnClickListener(this)
        binding.profileSaveAddress.setOnClickListener(this)
        binding.profileHome.setOnClickListener(this)
        binding.constraintSubscription.setOnClickListener(this)
        binding.btnScheduleTimeTrip.setOnClickListener(this)
        binding.btnDone.setOnClickListener(this)
        binding.tvSelectTaxiDate.setOnClickListener(this)
        binding.profileMyTrip.setOnClickListener(this)

    }

    override fun mObserver() {
        mUserGetBanner.mLogoutViewModel.observe(this) {
            startActivity(Intent(this, SelectUserTypeActivity::class.java))
            SharedPreference.isDriverActive = false
        }
        mUserGetBanner.mUserRecentlyAddressViewModel.observe(this) {
            if (it.response.result.size == 0) {
                binding.mSavedAddressRecyclerView.visibility = View.GONE
            } else {
                binding.mSavedAddressRecyclerView.adapter = RecentPickupPlaceAdapter(
                    this, it.response.result,
                    object : RecentPickupPlaceAdapter.mRecentPickLocation {
                        override fun mRecentAddressClick(
                            position: Int,
                            mRecentAddressList: UserRecentilyAddressResponse.Response.Result,
                            type: String
                        ) {
                        }
                    })
            }
        }
        mDriverToogleView.mDriverToggleActiveViewModel.observe(this) {
            mbutton = it.response!!.result!!.toggle == true


        }
        /*mUserGetBanner.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().hideProgress()
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }*/
        mUserGetBanner.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
        mUserGetBanner.mGetBannerViewModel.observe(this) {
            if (it.response.result.size == 0) {
                mBannerList.addAll(it.response.result)
                viewPagerPlusIndicator(it.response.result)
            } else {
                mBannerList.addAll(it.response.result)
                viewPagerPlusIndicator(it.response.result)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mPinPoint -> {
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
                val latLng = LatLng(
                    SharedPreferenceUtil.getInstance(this).userPickLatitude!!.toDouble(),
                    SharedPreferenceUtil.getInstance(this).userPickLongitude!!.toDouble()
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            }

            R.id.SideButton -> {
                binding.myDrawerLayout.openDrawer(GravityCompat.START);
            }

            R.id.constraintLogOut -> {
                popDialogLogOut()
            }
            //user select schedule trip date picker
            R.id.tvSelectTaxiDate -> {
                mCalenderDialogshowDialog()
            }
            R.id.ivBackpress -> {
                binding.myDrawerLayout.closeDrawer(GravityCompat.START) // Close the drawer from the start (left) side
            }

            R.id.mConstraLayout2 -> { startActivity(Intent(this, EnterDropOffLocationActivity::class.java)
                        .putExtra("maddress", mAddresses)
                        .putExtra("latt", latitude)
                        .putExtra("longg", longitude)
                )
            }

            R.id.profileSaveAddress -> {
                startActivity(Intent(this, SavedAddressActivity::class.java))
                binding.myDrawerLayout.close()
            }

            R.id.profileHome -> {
                binding.myDrawerLayout.close()
            }

            R.id.constraintSubscription -> {
                startActivity(Intent(this, SubscriptionActivity::class.java))
                binding.myDrawerLayout.close()
            }

            R.id.btnScheduleTimeTrip -> {
                binding.mLayoutA.visibility = View.GONE
                binding.LayoytB.visibility = View.VISIBLE

            }

            R.id.btnDone -> {
                binding.constraintLayout.visibility = View.VISIBLE
                binding.LayoytB.visibility = View.GONE
            }

            R.id.One -> {
                mInVisible()
                binding.mTaxi.setTextColor(Color.parseColor("#FFFFFF"))
                binding.mTaxiImageA.setImageResource(R.drawable.taxi_location_ic)
                change(binding.One, binding.img1, binding.Two, binding.img2, binding.Three, binding.img3)
            }

            R.id.Two -> {
                mInVisible()
                binding.btnSending.setTextColor(Color.parseColor("#FFFFFF"))
                binding.mTaxiImageB.setImageResource(R.drawable.location_sending_home_active)
                change(binding.Two, binding.img2, binding.Three, binding.img3, binding.One, binding.img1)

            }

            R.id.Three -> {
                mInVisible()
                binding.mMoveing.setTextColor(Color.parseColor("#FFFFFF"))
                binding.mTaxiImageC.setImageResource(R.drawable.location_moving_home_active)
                change(binding.Three, binding.img3, binding.Two, binding.img2, binding.One, binding.img1)

            }

            R.id.ivToggleCar -> {
                if (binding.ivToggleCar.isChecked) {
                    SharedPreference.isDriverActive = true
                    binding.btnYouareoffline.setTextColor(ContextCompat.getColor(this, R.color.radio_box_color))
                    popDialog()
                    mDriverToogleView.mDriverToggleActiveHitAPi(
                        access_token = SharedPreference.accessToken,
                        request = DriverToggleActiveRequest(buttonStatus = true))
                } else {
                    SharedPreference.isDriverActive = false
                    binding.btnYouareoffline.setTextColor(ContextCompat.getColor(this, R.color.red))
                    mDriverToogleView.mDriverToggleActiveHitAPi(
                        access_token = SharedPreference.accessToken,
                        request = DriverToggleActiveRequest(buttonStatus = false)
                    )
                }
            }

            R.id.btnYouareoffline -> {
                popDialog()

            }
            R.id.profileMyTrip -> {
               startActivity(Intent(this, MyTripActivity::class.java))

            }
        }

    }

    private fun popDialog() {
        val mDialogView = LayoutInflater.from(this)
        val view = mDialogView.inflate(R.layout.dialog_purchase_subscription, null)
        val binding = DialogPurchaseSubscriptionBinding.bind(view)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(view)
        val dialog = dialogBuilder.create()
        dialog.show()

        binding.btnSave.setOnClickListener {
            startActivity(Intent(this, SubscriptionActivity::class.java))
            dialog.dismiss() // Dismiss the dialog
        }
        binding.btnCancel.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog
        }
    }

    private fun popDialogLogOut() {
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null)
        val myBuilder = android.app.AlertDialog.Builder(this, 0).create()
        myBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val binding = DialogLogoutBinding.bind(dialogLayout)
        myBuilder.apply {
            setView(dialogLayout)
            setCancelable(false)
        }.show()
        binding.btnSave.setOnClickListener {
               mUserGetBanner.mLogoutHitApi(access_token = SharedPreference.accessToken)
            startActivity(Intent(this, SelectUserTypeActivity::class.java))
            SharedPreference.isDriverActive = false
            finish()
            myBuilder.dismiss() // Dismiss the dialog
        }
        binding.btnCancel.setOnClickListener {
            myBuilder.dismiss()// Dismiss the dialog
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun viewPagerPlusIndicator(bannerList: ArrayList<GetBannerResponse.Response.Result>) {
        if (mBannerList.isEmpty()) {

        } else {
            adapter = ViewPagerBannerAdapter(this, bannerList)
            binding.homePageSlider.adapter = adapter
            binding.dotsIndicator.setViewPager2(binding.homePageSlider)
        }
        numPages = adapter?.itemCount!!
        val dots = arrayOfNulls<ImageView>(numPages)
        for (i in 0 until numPages) {
            dots[i] = ImageView(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
        }
        binding.homePageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until numPages) {
                    if (handler != null) {
                        handler?.removeCallbacks(runnable)
                        handler?.removeCallbacksAndMessages(null)
                        currentPageBannerTop = position
                        handler?.postDelayed(runnable, DELAY_MS)
                    }

                }
            }
        })
    }

    internal var runnable: Runnable = object : Runnable {
        override fun run() {
            if (binding.homePageSlider != null) {
                if (binding.homePageSlider.currentItem < mBannerList!!.size - 1) {
                    currentPageBannerTop++
                    binding.homePageSlider.currentItem = currentPageBannerTop
                } else {
                    currentPageBannerTop = 0
                    binding.homePageSlider.currentItem = currentPageBannerTop
                }
                handler?.postDelayed(this, DELAY_MS)
            }

        }
    }

    private fun loadMap() {
        if (gpsTracker!!.isGPSEnabled) {
            latitude = gpsTracker?.location?.latitude?:0.0
            longitude = gpsTracker?.location?.longitude?:0.0
            supportMapFragment =
                supportFragmentManager.findFragmentById(R.id.HomeMap) as SupportMapFragment
            supportMapFragment!!.getMapAsync(this)
        } else {
            showToast("Please Enabled GPS Settings")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener(onCameraIdleListener)
        mMap.uiSettings.isMapToolbarEnabled = false
        setMarkerOnMap()
        setupMap()
        setMarkerOnMap()
        setupMap()

    }

    private fun setMarkerOnMap() {
        //statusBarTransparent()
        try {
            if (latitude != null && longitude != null) {
                val userCurrentLatLon = LatLng(latitude!!, longitude!!)
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
                mMap.animateCamera(CameraUpdateFactory.newLatLng(userCurrentLatLon))
                findAddressFromLatLng(userCurrentLatLon)
                SharedPreferenceUtil.getInstance(this).userPickLatitude = latitude.toString()
                SharedPreferenceUtil.getInstance(this).userPickLongitude = longitude.toString()


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

    @SuppressLint("SuspiciousIndentation")
    private fun setupMap() {
        if (GPSTracker(this).location!=null)
        location = GPSTracker(this).location!!
        val mLatLng = LatLng((gpsTracker?.latitude ?: 0.0), (gpsTracker?.longitude ?: 0.0))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15F))
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
        findAddressFromLatLng(latLng)
    }

    private fun findAddressFromLatLng(latLng: LatLng) {
        try {
            val addresses: List<Address>
            val geoCoder = Geocoder(this, Locale.getDefault())
            addresses =
                geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1) as List<Address>
            val address = addresses[0].getAddressLine(0)
            mAddresses = address
            SharedPreference.currentaddress = mAddresses
            SharedPreference.userPickLatitude = latitude.toString()
            SharedPreference.userPickLongitude = longitude.toString()


        } catch (e: Exception) {
            if (checkGpsPermission(this)) {
                if (!checkLocationPermission(this)) {
                    //showToast(getString(R.string.need_location_permission_for_fetch_current_location))
                }
            } else {
                enableDeviceGPS(this)
            }
        }
    }

    fun mInVisible() {
        binding.mTaxi.setTextColor(getResources().getColor(R.color.black));
        binding.mTaxiImageA.setImageResource(R.drawable.taxi_location_ic_inactive)
        binding.mTaxiImageB.setImageResource(R.drawable.location_sending_home)
        binding.btnSending.setTextColor(getResources().getColor(R.color.black));
        binding.mMoveing.setTextColor(getResources().getColor(R.color.black));
        binding.mTaxiImageC.setImageResource(R.drawable.location_moving_home)
        binding.One.setBackgroundResource(R.drawable.taxi_bg)
        binding.Two.setBackgroundResource(R.drawable.taxi_bg)
        binding.Three.setBackgroundResource(R.drawable.taxi_bg)
    }

    private fun change(
        con1: ConstraintLayout,
        img1: ImageView,
        con2: ConstraintLayout,
        img2: ImageView,
        con3: ConstraintLayout,
        img3: ImageView
    ) {
        con1.background = null
        img1.isVisible = true
        con2.setBackgroundResource(R.drawable.taxi_bg)
        img2.isVisible = false
        con3.setBackgroundResource(R.drawable.taxi_bg)
        img3.isVisible = false
    }

    fun mCalenderDialogshowDialog() {
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.calender_item, null)
        val myBuilder = android.app.AlertDialog.Builder(this, 0).create()
        myBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val binding = CalenderItemBinding.bind(dialogLayout)
        myBuilder.apply {
            setView(dialogLayout)
            setCancelable(false)

            binding?.timeSelector?.addOnDateChangedListener { displayed, date ->
                selectedTime=date.time.toString()

            }
        }.show()
    }
}

