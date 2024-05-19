package com.dubitaxi.ui.selectUserType.passengerSignUp.setDropLocation

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivitySetDropLocationBinding
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.VehicleListActivity
import com.dubitaxi.utils.checkGpsPermission
import com.dubitaxi.utils.checkGpsPermission2
import com.dubitaxi.utils.checkLocationPermission
import com.dubitaxi.utils.enableDeviceGPS
import com.dubitaxi.utils.statusBarTransparent
import com.dubitaxi.utils.GPSTracker
import com.dubitaxi.utils.SharedPreferenceUtil
import com.dubitaxi.utils.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.Locale

class SetDropLocationActivity : BaseActivity(), View.OnClickListener, OnMapReadyCallback {
    lateinit var binding: ActivitySetDropLocationBinding
    private var supportMapFragment: SupportMapFragment? = null
    private lateinit var mMap: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0
    var handler: Handler? = Handler()
    private var gpsTracker: GPSTracker? = null
    var mAddresses = ""
    lateinit var location: Location


    var mPickUpNewLatitute: Double = 0.0
    var mPickUpNewLongtitute: Double = 0.0
    var mDropNewLatitute: Double = 0.0
    var mDropNewLongtitute: Double = 0.0


    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetDropLocationBinding.inflate(layoutInflater)
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
        gpsTracker = GPSTracker(this)
        loadMap()
        configureCameraIdle()

        mPickUpNewLatitute = intent.getDoubleExtra("mPickUpNewLatitute", 0.0)
        mPickUpNewLongtitute = intent.getDoubleExtra("mPickUpNewLongtitute", 0.0)
        mDropNewLatitute = intent.getDoubleExtra("mDropNewLatitute", 0.0)
        mDropNewLongtitute = intent.getDoubleExtra("mDropNewLongtitute", 0.0)

    }

    override fun initControl() {
        binding.backpressSetDropMap.setOnClickListener(this)
        binding.btnDropLocation.setOnClickListener(this)
        binding.mPinPointA.setOnClickListener(this)
        binding.ivCrossSetDrop.setOnClickListener(this)
        binding.EtSetLocationDrop.setOnClickListener(this)
        // Initialize the Places API client

    }

    override fun mObserver() {

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
                Log.i("TAG", "ManishPlace: " + place.name + ", " + place.id)
                binding.EtSetLocationDrop.setText(place.address)
                SharedPreference.currentaddress = place.address
                latitude = place.latLng!!.latitude
                longitude = place.latLng!!.longitude
                SharedPreference.userPickLatitude = latitude.toString()
                SharedPreference.userPickLongitude = longitude.toString()
                placeMarkerOnMap(place.latLng!!)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 19f))
                mMap.setOnCameraChangeListener {
                    it.target.latitude
                    placeMarkerOnMap(it.target)
                }
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location).draggable(true)
        val titleStr = findAddressFromLatLng(location)  // add these two lines
        markerOptions.title(titleStr.toString())
    }

    private fun loadMap() {
        latitude = gpsTracker?.location?.latitude ?: 0.0
        longitude = gpsTracker?.location?.longitude ?: 0.0
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.mapDrop) as SupportMapFragment
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
                val userCurrentLatLon = LatLng(latitude ?: 0.0, longitude ?: 0.0)
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
            binding.EtSetLocationDrop.text = mAddresses
            mDropNewLatitute = latitude
            mDropNewLongtitute = longitude


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
            R.id.backpressSetDropMap -> {
                onBackPressed()
            }

            R.id.btnDropLocation -> {
                if (intent.hasExtra("stopLocationA") && intent.getStringExtra("stopLocationA") == "stopLocationA") {
                    SharedPreference.userStopLatitude = latitude.toString()
                    SharedPreference.userStopLongitude = longitude.toString()
                    val returnIntentStopLocation = Intent()
                    returnIntentStopLocation.putExtra("stopLocation", mAddresses)
                        .putExtra("StopLat", latitude).putExtra("StopLon", longitude)
                    setResult(RESULT_OK, returnIntentStopLocation)
                    onBackPressed()
                } else if (intent.hasExtra("pickup") && intent.getStringExtra("pickup") == "pickup") {
                    SharedPreference.userPickLatitude = latitude.toString()
                    SharedPreference.userPickLongitude = longitude.toString()
                    val returnIntent = Intent()
                    returnIntent.putExtra("pickup", mAddresses)
                    setResult(RESULT_OK, returnIntent)
                    onBackPressed()


                } else if (intent.hasExtra("dropLocation") && intent.getStringExtra("dropLocation") == "dropLocation") {
                    startActivity(Intent(this, VehicleListActivity::class.java).apply {
                        SharedPreference.userDropLatitude = latitude.toString()
                        SharedPreference.userDropLongitude = longitude.toString()
                        putExtra("userLatitude", latitude)
                        putExtra("userLongitude", longitude)
                            .putExtra("mPickUpNewLatitute", mPickUpNewLatitute)
                            .putExtra("mPickUpNewLongtitute", mPickUpNewLongtitute)
                            .putExtra("mDropNewLatitute", mDropNewLatitute)
                            .putExtra("mDropNewLongtitute", mDropNewLongtitute)
                    })
                } else if (intent.hasExtra("setLocation") && intent.getStringExtra("setLocation") == "setLocation") {
                    startActivity(Intent(this, VehicleListActivity::class.java).apply {
                        putExtra("userLatitude", latitude)
                        putExtra("userLongitude", longitude)
                    })
                }
                finish()
            }

            R.id.ivCrossSetDrop -> {
                binding.EtSetLocationDrop.setText(" ")

            }

            R.id.EtSetLocationDrop -> {
                openPlacePicker()
            }

            R.id.mPinPointA -> {
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
                val latLng = LatLng(
                    SharedPreferenceUtil.getInstance(this).userPickLatitude!!.toDouble(),
                    SharedPreferenceUtil.getInstance(this).userPickLongitude!!.toDouble()
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))


            }
        }
    }

}