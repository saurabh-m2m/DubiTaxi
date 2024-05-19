package com.dubitaxi.ui.selectUserType.passengerSignUp.setMap


import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivitySetOnMapBinding
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo.GetSavedAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.request.SavedAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.request.UpdateAddressRequest
import com.dubitaxi.utils.*
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
import java.util.*

class SetOnMapActivity : BaseActivity(), View.OnClickListener, OnMapReadyCallback {
    lateinit var binding: ActivitySetOnMapBinding
    private var supportMapFragment: SupportMapFragment? = null
    lateinit var mSavedAddressResponse: PassengerViewModel
    private lateinit var mMap: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0

    private var latitude1 = 0.0
    private var longitude1 = 0.0



    var handler: Handler? = Handler()
    private var gpsTracker: GPSTracker? = null
    var mAddresses = ""
    var newAdd = ""
    var googleMapLocation = ""
    var mEditAddress = ""
    lateinit var location: Location
    var mAddressId = ""
    var onMapMoved = false
    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null
    private var AddressType = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetOnMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        latitude1 = (intent.getDoubleExtra("setMapLatitute", 0.0))
        longitude1 = (intent.getDoubleExtra("setMapLongitute", 0.0))
        mAddresses = (intent.getStringExtra("setMapAddres").toString())
        placeMarkerOnMap(LatLng(latitude,longitude))
        if (intent.hasExtra("Edit")){
            binding.EtSetLocationMap.text = newAdd
        }else{
            binding.EtSetLocationMap.text = mAddresses
        }
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
        loadMap()
    }

    override fun initView() {
        /*   mPickUpNewLatitute=(intent.getDoubleExtra("latt",0.0))
           mPickUpNewLongtitute=(intent.getDoubleExtra("longg",0.0))
           mNewAddress=(intent.getStringExtra("maddress").toString())*/
        mSavedAddressResponse = ViewModelProvider(this).get(PassengerViewModel::class.java)
        if (intent.hasExtra("Edit") && intent.getStringExtra("Edit") == "Edit") {
            val mEditAddress =
                intent.getParcelableExtra<GetSavedAddressResponse.Response.Result>("EditAddress")
            if (mEditAddress != null) {
                when (mEditAddress.addressType) {
                    0 -> binding.checkHome.isChecked = true
                    1 -> binding.checkWork.isChecked = true
                    2 -> binding.checkOther.isChecked = true
                }
                //binding.EtSetLocationMap.setText(mEditAddress.addressName)
                binding.tvVistaLocation.setText(mEditAddress.addressName)
                mAddressId = mEditAddress.id
                latitude1=mEditAddress.lat
                longitude1=mEditAddress.long
                mAddresses=mEditAddress.officialName
                newAdd=mEditAddress.officialName
            } else {
                if (intent.hasExtra("heart")) {

                }
            }
        }
        Places.initialize(this, "AIzaSyA4L_dBeJDWuqtBT9ZOOUhjigbh6Lxi43s")
        gpsTracker = GPSTracker(this)
        inItTextChangeListener()
        gpsTracker = GPSTracker(this)
        configureCameraIdle()


    }
    /* private fun getAddress(latitude: Double, longitude: Double): String? {
         val result = StringBuilder()
         try {
             val geocoder = Geocoder(this, Locale.getDefault())
             val addresses = geocoder.getFromLocation(latitude, longitude, 1)
             if (addresses!!.size > 0) {
                 val address = addresses[0]
                 result.append(address.locality).append("\n")
                 result.append(address.countryName)
             }
         } catch (e: IOException) {
             Log.e("tag", e.getMessage())
         }
         return result.toString()
     }*/


    override fun initControl() {
        binding.backpressSetMap.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.ivLocationMapPointerSetMap.setOnClickListener(this)
        binding.checkHome.setOnClickListener(this)
        binding.checkWork.setOnClickListener(this)
        binding.checkOther.setOnClickListener(this)
        binding.ivCrossSetMap.setOnClickListener(this)
        binding.EtSetLocationMap.setOnClickListener(this)

    }

    override fun mObserver() {
        mSavedAddressResponse.mSavedAddressViewModel.observe(this) {
            val returnIntent = Intent()
            returnIntent.putExtra("pickup", mAddresses)
            returnIntent.putExtra("setMapLatitute", latitude1)
            returnIntent.putExtra("setMapLongitute", longitude1)
            setResult(RESULT_OK, returnIntent)
            onBackPressed()
        }
        mSavedAddressResponse.mUserUpdateAddressViewModel.observe(this) {
            showToast(it.message)
            onBackPressed()
        }
        mSavedAddressResponse.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mSavedAddressResponse.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
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
                Log.i("TAG", "ManishPlace: " + place.name + ", " + place.id)
                binding.EtSetLocationMap.setText(place.address)
                latitude = place.latLng!!.latitude
                longitude = place.latLng!!.longitude
                SharedPreference.userPickLatitude = latitude.toString()
                SharedPreference.userPickLongitude = longitude.toString()
                placeMarkerOnMap(place.latLng!!)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 15f))
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

    fun mSavedasFavouriteselect() {
        binding.checkHome.isChecked = false
        binding.checkWork.isChecked = false
        binding.checkOther.isChecked = false

    }
    private fun loadMap() {
        latitude = gpsTracker?.location?.latitude!!
        longitude = gpsTracker?.location?.longitude!!
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.mapSetMap) as SupportMapFragment
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
        SharedPreference.userPickLatitude = latitude.toString()
        SharedPreference.userPickLongitude = longitude.toString()
    }

    private fun findAddressFromLatLng(latLng: LatLng) {
        try {
            val addresses: List<Address>
            val geoCoder = Geocoder(this, Locale.getDefault())
            addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1) as List<Address>
            val address = addresses[0].getAddressLine(0)
            mAddresses = address
            if (onMapMoved) {
                binding.EtSetLocationMap.text = mAddresses
            }

        } catch (e: Exception) {
            if (checkGpsPermission(this)) {
                if (!checkLocationPermission(this)) {

                }
            } else {
                enableDeviceGPS(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener(onCameraIdleListener)
        setupMap()
        setMarkerOnMap()
        val mLatLng = LatLng(latitude1,longitude1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15F))
        if (intent.hasExtra("newAddress")) {
            /* latitude = gpsTracker?.location?.latitude!!
             longitude = gpsTracker?.location?.longitude!!*/
            val mLatLng = LatLng(latitude,longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15F))
            binding.EtSetLocationMap.text = mAddresses
        }
        mMap.setOnCameraMoveListener {
            onMapMoved = true
        }
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
    }
    private fun inItTextChangeListener() {
        handler = Handler()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave -> {
                checkradio()
                mSavedAddressHitApi()
            }

            R.id.backpressSetMap -> {
                onBackPressed()
            }

            R.id.btnCancel -> {
                finish() // If you are inside an activity
            }

            R.id.EtSetLocationMap -> {
                openPlacePicker()
            }

            R.id.ivCrossSetMap -> {
                binding.EtSetLocationMap.text = " "
            }
            R.id.ivLocationMapPointerSetMap -> {
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
                val latLng = LatLng(
                    SharedPreferenceUtil.getInstance(this).userPickLatitude!!.toDouble(),
                    SharedPreferenceUtil.getInstance(this).userPickLongitude!!.toDouble()
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            }

            R.id.checkHome -> {
                binding.constraintFav.visibility = View.GONE
                AddressType = 0
                mSavedasFavouriteselect()
                binding.checkHome.isChecked = true


            }

            R.id.checkWork -> {
                binding.constraintFav.visibility = View.GONE
                AddressType = 1
                mSavedasFavouriteselect()
                binding.checkWork.isChecked = true


            }

            R.id.checkOther -> {
                binding.constraintFav.visibility = View.VISIBLE
                AddressType = 2
                mSavedasFavouriteselect()
                binding.checkOther.isChecked = true
            }
        }
    }

    fun mSavedAddressHitApi() {
        if (NetworkUtils.isInternetAvailable(this)) {
            if (intent.getStringExtra("newAddress").equals("newAddress")) {
                when {
                    AddressType == -1 -> {
                        showToast("Please selected Saved Address")
                    }
                    /* binding.tvVistaLocation.text.isNullOrEmpty() -> {
                         showToast("Please other Saved Address")
                     }*/
                    else -> {
                        mSavedAddressResponse.mSavedAddressHitApi(
                            access_token = SharedPreference.accessToken,
                            request = SavedAddressRequest(
                                address_name = binding.tvVistaLocation.text.toString(),
                                latitude = SharedPreference.userPickLatitude!!.toDouble(),
                                longitude = SharedPreference.userPickLongitude!!.toDouble(),
                                address_type = AddressType.toString(),
                                official_name = mAddresses,
                                isFavourite = false
                            )
                        )
                    }
                }
            } else if (intent.getStringExtra("Edit").equals("Edit")) {
                when {
                    AddressType == -1 -> {
                        showToast("Please selected Saved Address")
                    }
                    /* binding.tvVistaLocation.text.isNullOrEmpty() -> {
                         showToast("Please other Saved Address")
                     }*/
                    else -> {
                        mSavedAddressResponse.mUserUpdateAddressHitApi(
                            SharedPreference.accessToken,
                            request = UpdateAddressRequest(
                                address_name = binding.tvVistaLocation.text.toString(),
                                latitude = SharedPreference.userPickLatitude!!.toDouble(),
                                longitude = SharedPreference.userPickLongitude!!.toDouble(),
                                address_type = AddressType.toString(),
                                addressId = mAddressId,
                                official_name = mAddresses
                            ),
                        )
                    }
                }
            } else if (intent.hasExtra("heart")) {
                when {
                    AddressType == -1 -> {
                        showToast("Please selected Saved Address")
                    }
                    else -> {
                        mSavedAddressResponse.mSavedAddressHitApi(
                            access_token = SharedPreference.accessToken,
                            request = SavedAddressRequest(
                                address_name = binding.tvVistaLocation.text.toString(),
                                latitude = SharedPreference.userPickLatitude!!.toDouble(),
                                longitude = SharedPreference.userPickLongitude!!.toDouble(),
                                address_type = AddressType.toString(),
                                official_name = mAddresses,
                                isFavourite = true
                            )
                        )
                    }
                }

            }
        }
    }

    private fun checkradio() {
        when {
            binding.checkHome.isChecked -> AddressType = 0
            binding.checkWork.isChecked -> AddressType = 1
            binding.checkOther.isChecked -> AddressType = 2
        }
    }
}