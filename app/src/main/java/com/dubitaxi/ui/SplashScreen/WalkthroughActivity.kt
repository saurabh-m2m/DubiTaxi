package com.dubitaxi.ui.SplashScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.constant.GPSTracker
import com.dubitaxi.databinding.ActivityWalkthroughBinding
import com.dubitaxi.ui.SplashScreen.vo.ImageModel
import com.dubitaxi.ui.selectUserType.SelectUserTypeActivity
import com.dubitaxi.utils.statusBar
import com.dubitaxi.utils.statusBarTransparent
import com.dubitaxi.utils.statusBarTransparentWithWhite
import com.github.florent37.runtimepermission.kotlin.askPermission


class WalkthroughActivity : BaseActivity(), View.OnClickListener {
    private var passwordVisible: Boolean = true
    private var mLatitude: String? = "0.0"
    private var mLongitude: String? = "0.0"
    private var liveLocation: Location? = null
    lateinit var binding: ActivityWalkthroughBinding
    private lateinit var adapter: TutorialsViewPagerAdapter
    private var numPages = 0
    private var currentPosition = 0
    private var imagesArray = ArrayList<ImageModel>()
    var DELAY_MS: Long = 3000
    var handler: Handler? = Handler()
    private var currentPageBannerTop = 0
    private lateinit var constraints1: ConstraintLayout
    private val images = listOf<ImageModel>(ImageModel(R.drawable.walk_1,
            "Lorem Ipsum is simply dummy text",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been industry's text ever since the 1500s."),
        ImageModel(
            R.drawable.walk_2,
            "Lorem Ipsum is simply dummy text",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been industry's text ever since the 1500s."
        ),
        ImageModel(
            R.drawable.walk_3,
            "Lorem Ipsum is simply dummy text",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been industry's text ever since the 1500s."
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkthroughBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //statusBar()
        statusBarTransparentWithWhite()
        initView()
        initControl()
        mObserver()

        constraints1 = findViewById(R.id.constraintsLayout3)
    }

    override fun initView() {
        if (liveLocation != null) {
            mLatitude = liveLocation?.latitude.toString()
            mLongitude = liveLocation?.longitude.toString()
            SharedPreference.userPickLatitude = mLatitude
            SharedPreference.userPickLongitude = mLongitude
        }
        locationPermissionSetup()
        viewPagerPlusIndicator()
    }

    override fun initControl() {
        binding.btnViewPager.setOnClickListener(this)
        binding.btnWalkNext.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_ViewPager -> {
                startActivity(Intent(this, SelectUserTypeActivity::class.java))
            }

            R.id.btn_walk_next -> {
                currentPosition = binding.viewPagerTutorials.currentItem + 1
                if (currentPosition < images.size) {
                    binding.viewPagerTutorials.currentItem = currentPosition
                } else if (currentPosition == images.size) {
                    // Handle end of photos and start NextActivity
                    startActivity(Intent(this, SelectUserTypeActivity::class.java))
                } else {
                    currentPosition = 0
                    binding.viewPagerTutorials.currentItem = currentPosition
                }
            }
        }
    }

    private fun viewPagerPlusIndicator() {
        for (element in images) imagesArray.addAll(listOf(element))
        adapter = TutorialsViewPagerAdapter(imagesArray)

        binding.viewPagerTutorials.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.viewPagerTutorials)
        numPages = adapter.itemCount
        val dots = arrayOfNulls<ImageView>(numPages)
        for (i in 0 until numPages) {
            dots[i] = ImageView(this)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
        }
        binding.viewPagerTutorials.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

        })
    }


    private fun locationPermissionSetup() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (liveLocation != null) {
                liveLocation = GPSTracker(this).location
                val geocoderResume = Geocoder(this)
                val addresses = geocoderResume.getFromLocation(
                    liveLocation?.latitude ?: 0.0,
                    liveLocation?.longitude ?: 0.0,
                    1
                )
                mLatitude = liveLocation?.latitude.toString()
                mLongitude = liveLocation?.longitude.toString()
            }

        } else {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            {
                if (liveLocation != null) {
                    liveLocation = GPSTracker(this).location
                    mLatitude = liveLocation?.latitude.toString()
                    mLongitude = liveLocation?.longitude.toString()
                    SharedPreference.userPickLatitude = mLatitude
                    SharedPreference.userPickLongitude = mLongitude
                }

            }.onDeclined {
                run {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Enable location access permission.",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()

                    mLatitude = "0.0"
                    mLongitude = "0.0"
                }
            }
        }

    }
}