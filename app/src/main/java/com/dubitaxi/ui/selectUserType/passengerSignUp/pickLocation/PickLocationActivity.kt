package com.dubitaxi.ui.selectUserType.passengerSignUp.pickLocation

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityPickLocationBinding
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.EnterDropOffLocationActivity
import com.dubitaxi.utils.checkGpsPermission
import com.dubitaxi.utils.checkGpsPermission2
import com.dubitaxi.utils.checkLocationPermission
import com.dubitaxi.utils.enableDeviceGPS
import com.dubitaxi.utils.statusBarTransparent
import com.dubitaxi.utils.GPSTracker
import com.dubitaxi.utils.SharedPreferenceUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.Locale

class PickLocationActivity : BaseActivity(), View.OnClickListener, OnMapReadyCallback {
    lateinit var binding: ActivityPickLocationBinding
    private var supportMapFragment: SupportMapFragment? = null
    private lateinit var mMap: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0
    var handler: Handler? = Handler()
    private var gpsTracker: GPSTracker? = null
    var mAddresses = ""
    lateinit var location: Location
    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {
        Places.initialize(this, "AIzaSyA4L_dBeJDWuqtBT9ZOOUhjigbh6Lxi43s")
        gpsTracker = GPSTracker(this)
        inItTextChangeListener()
        loadMap()
        configureCameraIdle()
    }

    override fun initControl() {
        binding.ivBackpressP.setOnClickListener(this)
        binding.btnConfirmPickupLocation.setOnClickListener(this)
        binding.EtPickupLocation.setOnClickListener(this)
        binding.EtDropLocation.setOnClickListener(this)
    }

    override fun mObserver() {

    }

    private fun loadMap() {
        latitude = gpsTracker?.location?.latitude!!
        longitude = gpsTracker?.location?.longitude!!
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map2) as SupportMapFragment
        supportMapFragment!!.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.setOnCameraIdleListener(onCameraIdleListener)
        setMarkerOnMap()
        setupMap()
        setMarkerOnMap()
        setupMap()

    }

    private fun setMarkerOnMap() {
        statusBarTransparent()
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

    private fun setupMap() {
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
            binding.EtPickupLocation.setText(mAddresses)
            binding.tvVistaLocation.text = mAddresses

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

    private fun inItTextChangeListener() {
        handler = Handler()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackpressP -> {
                onBackPressed()
            }

            R.id.btnConfirmPickupLocation -> {
                startActivity(Intent(this, EnterDropOffLocationActivity::class.java))

            }

            R.id.EtPickupLocation -> {
                //startActivity(Intent(this, SetDropLocationActivity::class.java))
            }

            R.id.EtDropLocation -> {
                openPlacePicker()
            }
        }
    }
    private val PLACE_PICKER_REQUEST = 111
    private fun openPlacePicker() {
        val fields: List<Place.Field> = listOf(
            Place.Field.ID, Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )

        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        ).build(this)
        startActivityForResult(intent, PLACE_PICKER_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                binding.EtDropLocation.setText(place.address)
            }
        }
    }


}