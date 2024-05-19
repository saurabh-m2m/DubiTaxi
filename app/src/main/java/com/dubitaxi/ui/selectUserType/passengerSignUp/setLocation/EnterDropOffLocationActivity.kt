package com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityEnterDropOffLocationBinding
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.RecentPickupPlaceAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.ChoosePlaceActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.setDropLocation.SetDropLocationActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.SearchLocationResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.UserRecentilyAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.SetOnMapActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.VehicleListActivity
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent
import java.util.ArrayList


class EnterDropOffLocationActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityEnterDropOffLocationBinding
    var mPickUpLocation = ""
    var mStopLocation = ""
    lateinit var mUserRecentlyAddress: PassengerViewModel
    var mAddressType = "0"
    val handler = Handler()
    var mCurrenLat: Double = 0.0
    var mCurrenLog: Double = 0.0
    var mIsFav: Boolean = true
    var LocationType = "1"
    lateinit var viewModel: SearchViewModel
    private val predictionsList = ArrayList<SearchLocationResponse.Predictions>()
    private lateinit var searchLocationAdapter: LocationAdapter
    var mPickupAddress: String = ""
    var mDropAddress: String = ""
    var mLatitude: Double = 0.0
    var mLongitude: Double = 0.0

    var mPickUpNewLatitute: Double = 0.0
    var mPickUpNewLongtitute: Double = 0.0

    var mDropNewLatitute: Double = 0.0
    var mDropNewLongtitute: Double = 0.0

    var mPickupNewAddress: String = ""
    var mDropNewAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterDropOffLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
        inItAdapters()

        mPickUpNewLatitute = (intent.getDoubleExtra("latt", 0.0))
        mPickUpNewLongtitute = (intent.getDoubleExtra("longg", 0.0))
        mPickupNewAddress = (intent.getStringExtra("maddress").toString())
        val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                when (view) {
                    binding.EtPickupLocation -> {
                        mAddressType = "0"
                    }

                    binding.mStopLocation -> {
                        mAddressType = "1"
                    }

                    binding.EtDropLocation -> {
                        mAddressType = "2"
                    }
                }
            } else {
                mAddressType = "0"
            }
        }
        binding.EtPickupLocation.onFocusChangeListener = onFocusChangeListener
        binding.mStopLocation.onFocusChangeListener = onFocusChangeListener
        binding.EtDropLocation.onFocusChangeListener = onFocusChangeListener
    }

    private fun inItAdapters() {
        searchLocationAdapter = LocationAdapter(predictionsList, object :
            LocationAdapter.MyInterface {
            override fun onClick(position: Int, name: String) {
                binding.rvLocation.visibility = View.GONE
                viewModel.placeId = predictionsList[position].place_id
                viewModel.hitGetPlacesDetailsApi()
                binding.EtPickupLocation.removeTextChangedListener(textWatcher)
                binding.EtDropLocation.removeTextChangedListener(textWatcher2)
                if (mAddressType == "0") {
                    binding.EtPickupLocation.setText(name)
                    mPickupNewAddress = name

                }
                if (mAddressType == "2") {
                    binding.EtDropLocation.setText(name)
                    mDropNewAddress = name
                }
                binding.EtPickupLocation.addTextChangedListener(textWatcher)
                binding.EtDropLocation.addTextChangedListener(textWatcher2)
            }
        })
        binding.rvLocation.adapter = searchLocationAdapter
    }


    override fun onResume() {
        super.onResume()
        mUserRecentlyAddress.mUserRecentlyAddressHitApi(SharedPreference.accessToken)
    }

    override fun initView() {
        mUserRecentlyAddress = ViewModelProvider(this)[PassengerViewModel::class.java]
        mUserRecentlyAddress.mUserRecentlyAddressHitApi(SharedPreference.accessToken)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.constraintDropLocation1.visibility = View.GONE
        binding.ivLocation.visibility = View.GONE
        binding.ivLine.visibility = View.GONE

        ////......................
        binding.EtPickupLocation.setText(intent.getStringExtra("maddress").toString())
        mPickUpNewLatitute = intent.getDoubleExtra("latt", 0.0)
        mPickUpNewLongtitute = intent.getDoubleExtra("longg", 0.0)

        ////..........
        /*testing*/
        SharedPreference.userDropLatitude = mDropNewLatitute.toString()
        SharedPreference.userDropLongitude = mDropNewLongtitute.toString()
        SharedPreference.userPickLatitude = mPickUpNewLatitute.toString()
        SharedPreference.userPickLongitude = mPickUpNewLongtitute.toString()

    }

    override fun initControl() {
        binding.EtPickupLocation.addTextChangedListener(textWatcher)
        binding.EtDropLocation.addTextChangedListener(textWatcher2)
        binding.PickLocation.setOnClickListener(this)
        binding.tvSaveLocation.setOnClickListener(this)
        binding.ivBackpressDrop.setOnClickListener(this)
        binding.tvSetLocation.setOnClickListener(this)
        binding.StopLocation.setOnClickListener(this)
        binding.ivCross.setOnClickListener(this)
        binding.ivAdd.setOnClickListener(this)
        binding.DropLocationMap.setOnClickListener(this)
        binding.mHerat.setOnClickListener(this)
        binding.mHeratDrop.setOnClickListener(this)
        binding.mStopLocation.setOnClickListener(this)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            LocationType = "1"
            if (charSequence.isNullOrEmpty()) {
                binding.rvLocation.visibility = View.GONE
            } else {
                binding.rvLocation.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.input = charSequence.toString()
                    viewModel.hitGetLocationApi()
                }, 1000)
            }
        }
    }
    private val textWatcher2 = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            LocationType = "1"
            if (charSequence.isNullOrEmpty()) {
                binding.rvLocation.visibility = View.GONE
            } else {
                binding.rvLocation.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.input = charSequence.toString()
                    viewModel.hitGetLocationApi()
                }, 1000)
            }
        }
    }

    override fun mObserver() {
        mUserRecentlyAddress.mUserRecentlyAddressViewModel.observe(this) {
            if (it.response.result.size == null) {
                binding.recyclerRecentPlace.visibility = View.GONE
            } else {
                binding.recyclerRecentPlace.adapter = RecentPickupPlaceAdapter(
                    this,
                    it.response.result,
                    object : RecentPickupPlaceAdapter.mRecentPickLocation {
                        override fun mRecentAddressClick(
                            position: Int,
                            mRecentAddressList: UserRecentilyAddressResponse.Response.Result,
                            type: String
                        ) {
                            if (mAddressType == "0") {
                                binding.EtPickupLocation.setText(mRecentAddressList.officialName)
                                binding.EtPickupLocation.setSelection(mRecentAddressList.officialName.length)
                                mPickupNewAddress = mRecentAddressList.officialName
                                mPickUpNewLatitute = mRecentAddressList.lat
                                mPickUpNewLongtitute = mRecentAddressList.long
                                if (!binding.EtPickupLocation.text.isNullOrEmpty() && !binding.EtDropLocation.text.isNullOrEmpty()) {
                                    handler.postDelayed(Runnable { // Write whatever to want to do after delay specified (1 sec)
                                        startActivity(
                                            Intent(this@EnterDropOffLocationActivity, VehicleListActivity::class.java).apply {
                                                putExtra("mPickUpNewLatitute",mPickUpNewLatitute)
                                                putExtra("mPickUpNewLongtitute",mPickUpNewLongtitute)
                                                putExtra("mDropNewLatitute",mDropNewLatitute)
                                                putExtra("mDropNewLongtitute",mDropNewLongtitute)
                                            }

                                        )
                                    }, 1000)

                                }
                            }
                            if (mAddressType == "1") {
                                binding.mStopLocation.text = mRecentAddressList.officialName
                                binding.mStopLocation.setText(mRecentAddressList.officialName.length)

                            }
                            if (mAddressType == "2") {
                                binding.EtDropLocation.removeTextChangedListener(textWatcher)
                                binding.EtPickupLocation.removeTextChangedListener(textWatcher2)
                                mDropNewLatitute = mRecentAddressList.lat
                                mDropNewLongtitute = mRecentAddressList.long
                                mDropNewAddress = mRecentAddressList.officialName
                                if (!binding.EtPickupLocation.text.isNullOrEmpty() && !binding.EtDropLocation.text.isNullOrEmpty()) {
                                    handler.postDelayed(Runnable { // Write whatever to want to do after delay specified (1 sec)
                                        startActivity(
                                            Intent(
                                                this@EnterDropOffLocationActivity, VehicleListActivity::class.java).apply {
                                                putExtra("mPickUpNewLatitute",mPickUpNewLatitute)
                                                putExtra("mPickUpNewLongtitute",mPickUpNewLongtitute)
                                                putExtra("mDropNewLatitute",mDropNewLatitute)
                                                putExtra("mDropNewLongtitute",mDropNewLongtitute)
                                            }

                                        )
                                    }, 1000)
                                } else {
                                    binding.EtDropLocation.setText(mRecentAddressList.officialName)
                                    binding.EtDropLocation.setSelection(mRecentAddressList.officialName.length)
                                    SharedPreference.userSavedLatitude =
                                        mRecentAddressList.lat.toString()
                                    SharedPreference.userSavedLongitude =
                                        mRecentAddressList.long.toString()
                                }


                            }

                        }
                    })
            }
            mUserRecentlyAddress.mUserMarkandUnMarkAddressViewModel.observe(this) {
                //mUserRecentlyAddress.mUserRecentlyAddressHitApi(SharedPreference.accessToken)
            }
        }
        viewModel.searchLocationResponse.observe(this) {
            // binding.rvLocation.visibility = View.VISIBLE
            predictionsList.clear()
            predictionsList.addAll(it.predictions)
            searchLocationAdapter.notifyDataSetChanged()
        }
        viewModel.onPlaceDetailsResponse.observe(this) {
            // binding.rvLocation.visibility = View.GONE
            binding.EtPickupLocation.removeTextChangedListener(textWatcher)
            binding.EtDropLocation.removeTextChangedListener(textWatcher2)
            mLatitude = it.result.geometry.location.lat
            mLongitude = it.result.geometry.location.lng
            mPickupAddress = it.result.name
            mDropAddress = it.result.name

            if (mAddressType == "0") {
                mPickUpNewLatitute = mLatitude
                mPickUpNewLongtitute = mLongitude
                if (!binding.EtPickupLocation.text.isNullOrEmpty() && !binding.EtDropLocation.text.isNullOrEmpty()) {
                    handler.postDelayed(Runnable { // Write whatever to want to do after delay specified (1 sec)
                        startActivity(Intent(this@EnterDropOffLocationActivity, VehicleListActivity::class.java).apply {
                                putExtra("mPickUpNewLatitute",mPickUpNewLatitute)
                                putExtra("mPickUpNewLongtitute",mPickUpNewLongtitute)
                                putExtra("mDropNewLatitute",mDropNewLatitute)
                                putExtra("mDropNewLongtitute",mDropNewLongtitute)

                            }
                        )
                    }, 1000)

                }

            }

            if (mAddressType == "2") {
                mDropNewLatitute = mLatitude
                mDropNewLongtitute = mLongitude
                if (!binding.EtPickupLocation.text.isNullOrEmpty() && !binding.EtDropLocation.text.isNullOrEmpty()) {
                    handler.postDelayed(Runnable { // Write whatever to want to do after delay specified (1 sec)
                        startActivity(Intent(this@EnterDropOffLocationActivity, VehicleListActivity::class.java).apply {
                            putExtra("mPickUpNewLatitute",mPickUpNewLatitute)
                            putExtra("mPickUpNewLongtitute",mPickUpNewLongtitute)
                            putExtra("mDropNewLatitute",mDropNewLatitute)
                            putExtra("mDropNewLongtitute",mDropNewLongtitute)
                        })
                    }, 1000)

                }
            }

            binding.EtPickupLocation.addTextChangedListener(textWatcher)
            binding.EtDropLocation.addTextChangedListener(textWatcher2)


        }
        mUserRecentlyAddress.mProgress.observe(this) {
            if (it) {
                //  ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mUserRecentlyAddress.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.PickLocation -> {
                val i = Intent(this, SetDropLocationActivity::class.java)
                i.putExtra("pickup", "pickup")
                startActivityForResult(i, 111)
            }

            R.id.mStopLocation -> {
                val stopLocation = Intent(this, SetDropLocationActivity::class.java)
                stopLocation.putExtra("stopLocationA", "stopLocationA")
                startActivityForResult(stopLocation, 222)
            }


            R.id.DropLocationMap -> {
                if (binding.EtPickupLocation.text.toString().isNullOrEmpty()) {
                    showToast("please select pickup location")
                }else{
                    startActivity(Intent(this, SetDropLocationActivity::class.java).apply {
                        putExtra("dropLocation", "dropLocation")
                            .putExtra("mPickUpNewLatitute",mPickUpNewLatitute)
                            .putExtra("mPickUpNewLongtitute",mPickUpNewLongtitute)
                            .putExtra("mDropNewLatitute",mDropNewLatitute)
                            .putExtra("mDropNewLongtitute",mDropNewLongtitute)
                    })
                }
            }

          /*  R.id.DropLocationMap -> {
                startActivity(Intent(this, SetDropLocationActivity::class.java))
            }*/

            R.id.tvSaveLocation -> {
                startActivity(Intent(this, ChoosePlaceActivity::class.java))
            }

            R.id.ivBackpressDrop -> {
                onBackPressed()
            }

            R.id.tvSetLocation -> {
                startActivity(Intent(this, SetDropLocationActivity::class.java).apply {
                    putExtra("setLocation", "setLocation")
                })
            }

            R.id.ivAdd -> {
                binding.constraintDropLocation1.visibility = View.VISIBLE
                binding.ivLocation.visibility = View.VISIBLE
                binding.ivLine.visibility = View.VISIBLE
            }

            R.id.ivCross -> {
                binding.constraintDropLocation1.visibility = View.GONE
                binding.ivLocation.visibility = View.GONE
                binding.ivLine.visibility = View.GONE
            }
            R.id.mHerat -> {
                if (binding.EtPickupLocation.text.toString() == "") {
                    showToast("Please select pickup location")
                } else {
                    val intent = Intent(this, SetOnMapActivity::class.java)
                        .putExtra("heart", "heart")
                        .putExtra("setMapAddres", mPickupNewAddress)
                        .putExtra("setMapLatitute", mPickUpNewLatitute)
                        .putExtra("setMapLongitute", mPickUpNewLongtitute)
                        .putExtra("pickup", "pickup")
                    startActivityForResult(intent, 111)


                }
                /*     if (mIsFav) {
                         binding.mHerat.setImageResource(R.drawable.heart_active_set_location);
                         mUserRecentlyAddress.mUserMarkandUnMarkAddressHitApi(
                             access_token = SharedPreference.accessToken,
                             request = UsermarkandUnmarkAddressRequest(
                                 official_name = SharedPreference.currentaddress,
                                 latitude = mCurrenLat.toDouble(),
                                 longitude = mCurrenLog.toDouble(),
                                 isFavourite = mIsFav.toString()
                             )
                         )
                         mIsFav = false
                     } else {
                         binding.mHerat.setImageResource(R.drawable.heart_pickup_location);
                         mUserRecentlyAddress.mUserMarkandUnMarkAddressHitApi(
                             access_token = SharedPreference.accessToken,
                             request = UsermarkandUnmarkAddressRequest(
                                 official_name = SharedPreference.currentaddress,
                                 latitude = mCurrenLat.toDouble(),
                                 longitude = mCurrenLog.toDouble(),
                                 isFavourite = mIsFav.toString()
                             )
                         )
                         mIsFav = true
                     }*/

            }
            R.id.mHeratDrop -> {
                if (binding.EtDropLocation.text.toString() == "") {
                    showToast("Please select drop location")
                } else {
                    startActivity(
                        Intent(this, SetOnMapActivity::class.java)
                            .putExtra("heart", "heart")
                            .putExtra("setMapAddres", mDropNewAddress)
                            .putExtra("setMapLatitute", mDropNewLatitute)
                            .putExtra("setMapLongitute", mDropNewLongtitute)
                    )
                }
            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 || resultCode == RESULT_OK) {
            val result = data?.getStringExtra("pickup")
            mPickUpLocation = data?.getStringExtra("pickup").toString()
            mPickUpLocation = data?.getStringExtra("setMapLatitute").toString()
            mPickUpLocation = data?.getStringExtra("setMapLongitute").toString()
            if (!binding.EtPickupLocation.text.isNullOrEmpty() && !binding.EtDropLocation.text.isNullOrEmpty()) {
                handler.postDelayed(Runnable { // Write whatever to want to do after delay specified (1 sec)
                    startActivity(
                        Intent(this@EnterDropOffLocationActivity, VehicleListActivity::class.java)
                            .putExtra("mPickUpNewLatitute",mPickUpNewLatitute)
                            .putExtra("mPickUpNewLongtitute",mPickUpNewLongtitute)
                            .putExtra("mDropNewLatitute",mDropNewLatitute)
                            .putExtra("mDropNewLongtitute",mDropNewLongtitute)
                    )

                }, 1000)

            }
            binding.EtPickupLocation.setText(data?.getStringExtra("pickup"))
            result?.let {
                mPickUpLocation = it


            }
        }
        if (requestCode == 222 && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("stopLocation")
            result?.let {
                mStopLocation = it
                binding.mStopLocation.setText(mStopLocation)
                binding.constraintDropLocation1.visibility = View.VISIBLE
            }
        }
    }


}