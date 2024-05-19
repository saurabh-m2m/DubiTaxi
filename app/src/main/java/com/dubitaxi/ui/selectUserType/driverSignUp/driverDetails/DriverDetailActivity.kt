package com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityDriverDetailBinding
import com.dubitaxi.databinding.LayoutCarDetailsBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.ShowImageAdapter
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.VehicleDetailsAdapter
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.AddVehicleActivity
import com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails.BankDetailActivity
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.request.DriverDetailsRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.vo.DriverGetVehicleResponse
import com.dubitaxi.utils.*
import com.dubitaxi.utils.ImageUtils.chooseImageDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File


class DriverDetailActivity : BaseActivity(), View.OnClickListener, ImageListener {
    lateinit var binding: ActivityDriverDetailBinding
    var mProfileImage = ""

    lateinit var mDriverCreateDetailsViewModel: DriverViewModel
    private var Uri: Uri? = null
    private var Files: File? = null
    var isTravelSelected: Boolean = false
    var Smoking: Boolean = false
    var mMultipalImage = ArrayList<String>()
    var mImageType: String = ""
    var count = 0
    var docFront = ""
    var docBack = ""
    var mSelectSize = ""
    var checkselectsize: Boolean = false
    var mServiceType = ArrayList<Int>()
    val selectedDataList: ArrayList<Int> = ArrayList()
    var type = ""
    var list = ""
    var backimage = ""
    var frontimage = ""
    var ImageList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvAddVehicle
        mMultipalImage.add(0, "a")
        mMultipalImage.add(1, "b")
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
        mSelectSice()

    }

    override fun onResume() {
        super.onResume()
        mDriverCreateDetailsViewModel.mDriverGetVehiclesListHitAPi(SharedPreference.accessToken)
    }

    override fun initView() {
        mDriverCreateDetailsViewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
    }


    fun mSelectSice() {
        binding.checkboxSmall.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                /*binding.checkboxMedium.isChecked = true
                binding.checkboxLarge.isChecked = true*/
                selectedDataList.add(1) // Add data to the list when checkbox1 is checked
            } else {
                selectedDataList.removeAt(0) // Remove data from the list when checkbox1 is unchecked
            }
        }
        binding.checkboxMedium.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                /*binding.checkboxSmall.isChecked = true
                binding.checkboxLarge.isChecked = true*/
                selectedDataList.add(2) // Add data to the list when checkbox2 is checked
            } else {
                selectedDataList.removeAt(1) // Remove data from the list when checkbox2 is unchecked
            }
        }
        binding.checkboxLarge.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                /*binding.checkboxMedium.isChecked = true
                binding.checkboxSmall.isChecked = true*/
                selectedDataList.add(3) // Add data to the list when checkbox3 is checked
            } else {
                selectedDataList.removeAt(2) // Remove data from the list when checkbox3 is unchecked
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun mSetImage() {
        if (Build.VERSION.SDK_INT < 33) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED

            ) {

                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA,
                    ), 103
                )
            } else {
                chooseImageDialog(this)
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.READ_MEDIA_IMAGES,
                        android.Manifest.permission.CAMERA,
                    ), 103
                )
            } else {
                chooseImageDialog(this)
            }

        }

    }


    override fun initControl() {
        binding.checkboxDubiTexi.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.SelectSizeLayout.visibility = View.VISIBLE
                enableview()
                type = "0"
                if (binding.checkboxDubiTexi.isChecked && binding.checkboxDubiSending.isChecked) {
                    binding.checkboxDubiMoving.isChecked = false

                }
            } else {
                binding.SelectSizeLayout.visibility = View.GONE
                dusableview()
                if (binding.checkboxDubiMoving.isChecked || binding.checkboxDubiSending.isChecked)
                    binding.SelectSizeLayout.visibility = View.GONE
            }
        }
        binding.checkboxDubiSending.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                if (binding.checkboxDubiSending.isChecked && !binding.checkboxDubiTexi.isChecked) {

                    dusableview()
                }

                type = "1"
                if (binding.checkboxDubiSending.isChecked && binding.checkboxDubiMoving.isChecked) {
                    binding.checkboxDubiTexi.isChecked = false

                }
            } else {
                if (!binding.checkboxDubiSending.isChecked && !binding.checkboxDubiMoving.isChecked) {
                    enableview()
                }
            }
            binding.checkboxDubiMoving.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    if (binding.checkboxDubiMoving.isChecked && !binding.checkboxDubiTexi.isChecked) {
                        dusableview()
                    }
                    type = "2"
                    if (binding.checkboxDubiTexi.isChecked && binding.checkboxDubiMoving.isChecked) {
                        binding.checkboxDubiSending.isChecked = false
                    }
                } else {
                    if (!binding.checkboxDubiSending.isChecked && !binding.checkboxDubiMoving.isChecked) {
                        enableview()
                    }
                }
            }

        }
        binding.btnPassengerContinue.setOnClickListener(this)
        binding.mAddVehicle.setOnClickListener(this)
        binding.imgUploadDoc.setOnClickListener(this)
        binding.TvYes.setOnClickListener(this)
        binding.TvNo.setOnClickListener(this)
        binding.TvSmokingYes.setOnClickListener(this)
        binding.TvSmokingNo.setOnClickListener(this)
        binding.mImageClose.setOnClickListener(this)
        binding.mImageback.setOnClickListener(this)
        binding.checkboxDubiSending.setOnClickListener(this)
        binding.checkboxDubiTexi.setOnClickListener(this)
        binding.checkboxDubiMoving.setOnClickListener(this)
        binding.checkboxSmall.setOnClickListener(this)
        binding.checkboxMedium.setOnClickListener(this)
        binding.checkboxLarge.setOnClickListener(this)
    }

    override fun mObserver() {
        mDriverCreateDetailsViewModel.mDriverGetVehiclesListViewModel.observe(this) {
            mDriverCreateDetailsViewModel.mDriverGetVehiclesListHitAPi(SharedPreference.accessToken)
            val mGetVehicleList = it.response.result
            if (it.response.result.isNotEmpty())
                ImageList = it.response.result[0].images
            binding.recyclerCarList.adapter = VehicleDetailsAdapter(this, mGetVehicleList,
                object : VehicleDetailsAdapter.mShowVehicleData {
                    override fun mVehicleItem(
                        data: DriverGetVehicleResponse.Response.Result,
                        position: Int
                    ) {
                        mVehivlDetailsBottomSheet(data)
                    }
                })
        }
        mDriverCreateDetailsViewModel.mDriverUpLoadImageViewModel.observe(this) {
            if (docFront == "" && docBack == "") {
                mProfileImage = it.response
                if (it.response.isNullOrEmpty()) {
                    showToast("No front image available")
                    frontimage = ""
                } else {
                    Glide.with(this).load(it.response).into(binding.FrontSideImage)
                    frontimage = "frontImage"
                }

                binding.FrontSideImage.visibility = View.VISIBLE
                binding.BackSide.visibility = View.VISIBLE
                binding.mImageClose.visibility = View.VISIBLE
                docFront = it.response
            } else if (docFront == "") {
                mProfileImage = it.response
                binding.FrontSideImage.visibility = View.VISIBLE
                binding.BackSide.visibility = View.VISIBLE
                binding.mImageClose.visibility = View.VISIBLE
                docFront = it.response
            } else if (docBack == "") {
                mProfileImage = it.response
                if (it.response.isNullOrEmpty()) {
                    showToast("No front image available")
                    backimage = ""
                } else {
                    Glide.with(this).load(it.response).into(binding.uploadBackImage)
                    backimage = "backImage"
                }

                binding.uploadBackImage.visibility = View.VISIBLE
                binding.BackSideText.visibility = View.VISIBLE
                binding.mImageback.visibility = View.VISIBLE
                docBack = it.response
            }


        }

        mDriverCreateDetailsViewModel.mDriverDetailsViewModel.observe(this) {
            startActivity(Intent(this, BankDetailActivity::class.java))
            finish()
        }
        mDriverCreateDetailsViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mDriverCreateDetailsViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_passenger_continue -> {
                if (frontimage.isNullOrEmpty()) {
                    showToast("Please upload Driver front image")
                } else if (backimage.isNullOrEmpty()) {
                    showToast("Please upload Driver back image")
                } else {
                    mDriverDatilsValidation()
                }


            }

            R.id.mAddVehicle -> {
                addToList()
                if (mServiceType.isNotEmpty()) {
                    startActivity(Intent(this, AddVehicleActivity::class.java).apply {
                        putExtra("VehicleType", mServiceType)
                        putExtra("AddVehicle", "AddVehicle")
                    })
                }

            }

            R.id.img_uploadDoc -> {
                mSetImage()
            }

            R.id.TvYes -> {
                binding.SelectSizeLayout.visibility = View.VISIBLE
                isTravelSelected = true
                binding.TvYes.isChecked = true
                binding.TvNo.isChecked = false
            }

            R.id.TvNo -> {
                binding.SelectSizeLayout.visibility = View.GONE
                isTravelSelected = true
                binding.TvYes.isChecked = false
                binding.TvNo.isChecked = true
            }

            R.id.TvSmokingYes -> {
                Smoking = true
                binding.TvSmokingYes.isChecked = true
                binding.TvSmokingNo.isChecked = false
            }

            R.id.TvSmokingNo -> {
                Smoking = true
                binding.TvSmokingYes.isChecked = false
                binding.TvSmokingNo.isChecked = true
            }

            R.id.mImageClose -> {
                mProfileImage = ""
                docFront = ""
                binding.FrontSideImage.visibility = View.INVISIBLE
                binding.BackSide.visibility = View.INVISIBLE
                binding.mImageClose.visibility = View.INVISIBLE
            }

            R.id.mImageback -> {
                mProfileImage = ""
                docBack = ""
                binding.uploadBackImage.visibility = View.INVISIBLE
                binding.BackSideText.visibility = View.INVISIBLE
                binding.mImageback.visibility = View.INVISIBLE
            }
        }
    }

    private fun addToList() {
        mServiceType.clear()
        if (binding.checkboxDubiTexi.isChecked)
            mServiceType.add(0)
        if (binding.checkboxDubiSending.isChecked)
            mServiceType.add(1)
        if (binding.checkboxDubiMoving.isChecked)
            mServiceType.add(2)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.CAMERA) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage = data.extras!!["data"] as Bitmap?
                val imageuri = (this as AppCompatActivity).getImageUri(selectedImage!!)
                Uri = imageuri
                Files = compressImageFile(this, imageuri)
            }
        } else if (requestCode == AppConstants.GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage: Uri? = data.data
                Uri = selectedImage
                Files = compressImageFile(this, selectedImage!!)
            }
        }
        if (requestCode == AppConstants.GALLERY || requestCode == AppConstants.CAMERA) {
            ImageUtils.onActivityResult(requestCode, resultCode, data, this, this)
        }
    }

    override fun getImageData(uri: Uri?, bm: Bitmap?, file: File?) {
        Files = file
        if (mImageType == "1") {
            mDriverCreateDetailsViewModel.mDriverUpLoadImageHitApi(file = file)
        } else {
            mDriverCreateDetailsViewModel.mDriverUpLoadImageHitApi(file = file)
        }
    }


    private fun mDriverDatilsValidation() {
        addToList()
        checkSmoking()
        if (NetworkUtils.isInternetAvailable(this)) {
            when {
                mProfileImage.trim().isNullOrEmpty() -> {
                    showToast("Please upload Driver License image")
                }

                type.isNullOrEmpty() -> {
                    showToast("Please select service Type")
                }



                binding.checkboxDubiTexi.isChecked && selectedDataList.isEmpty() -> {
                    showToast("Please select Size")
                }



                (binding.recyclerCarList.adapter as VehicleDetailsAdapter).mGetDriverVehiclesList.isNullOrEmpty() -> {
                    showToast("Please Add Vehicle details")
                }

                else -> {
                    mDriverCreateDetailsViewModel.mDriverDetailsHitApi(
                        access_token = SharedPreference.accessToken, request = DriverDetailsRequest(
                            DLFrontImg = mProfileImage,
                            DLBackImg = mProfileImage,
                            service_offerings = mServiceType,
                            pets_accepted = isTravelSelected,
                            permit_smoking = Smoking,
                            pets_size = selectedDataList
                        )
                    )
                }
            }
        } else {
            showToast(resources.getString(R.string.error_internet))
        }
    }

    private var dialog: BottomSheetDialog? = null
    fun mVehivlDetailsBottomSheet(data: DriverGetVehicleResponse.Response.Result) {
        dialog = BottomSheetDialog(this, R.style.MyBottomTheme)
        val view = layoutInflater.inflate(R.layout.layout_car_details, null)
        val binding = LayoutCarDetailsBinding.bind(view)
        dialog?.apply {
            setContentView(view)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            binding.vehiclemodelName?.text = data.modelName
            binding.tvVehiclemodelNumber.text = data.vehicleNumber
            binding.tvVehicleColorName.text = data.color
            binding.tvVehicleTypeName.text = data.vehicle_type_name.toString()
            binding.tvVehicleSeatNo.text = data.seatsAvailable.toString()
            binding.tvVehicleRelationshipName.text = data.relationWithVehicle.toString()
            Glide.with(context).load(data.images[0]).into(binding.imgCar)
            Glide.with(context).load(data.permissionLink).into(binding.uploadSoatImageShow)
            binding.ShowImageRecyclerView.adapter = ShowImageAdapter(context, ImageList)
            binding.imgCarDetailsClose.setOnClickListener {
                dismiss()
            }

            binding.EditVehicle.setOnClickListener {
                startActivity(Intent(this@DriverDetailActivity, AddVehicleActivity::class.java).apply { putExtra("Vehicle", data.id)
                        putExtra ("VehicleA", "VehicleA") })
                dialog?.dismiss()
            }
        }?.show()
        //dialog?.dismiss()
    }

    private fun dusableview() {
        binding.tvPetsAccept.setTextColor(Color.parseColor("#9D9D9D"))
        binding.tvSmoking.setTextColor(Color.parseColor("#9D9D9D"))
        binding.radioGroup1.isEnabled = false
        binding.radioGroup2.isEnabled = false
        binding.TvNo.isEnabled = false
        binding.TvYes.isEnabled = false
        isTravelSelected = false
        Smoking = false
        binding.TvSmokingNo.isEnabled = false
        binding.TvSmokingYes.isEnabled = false
        binding.TvYes.setTextColor(Color.parseColor("#9D9D9D"))
        binding.TvSmokingYes.setTextColor(Color.parseColor("#9D9D9D"))
        binding.TvSmokingNo.setTextColor(Color.parseColor("#9D9D9D"))
        binding.TvNo.setTextColor(Color.parseColor("#9D9D9D"))
        binding.TvNo.setTextColor(Color.parseColor("#9D9D9D"))
    }

    private fun enableview() {
        binding.tvPetsAccept.setTextColor(Color.BLACK)
        binding.tvSmoking.setTextColor(Color.BLACK)
        binding.radioGroup1.isEnabled = true
        binding.radioGroup2.isEnabled = true
        binding.TvNo.isEnabled = true
        binding.TvYes.isEnabled = true
        binding.TvSmokingNo.isEnabled = true
        binding.TvSmokingYes.isEnabled = true
        binding.TvYes.setTextColor(Color.BLACK)
        binding.TvSmokingYes.setTextColor(Color.BLACK)
        binding.TvSmokingNo.setTextColor(Color.BLACK)
        binding.TvNo.setTextColor(Color.BLACK)
        binding.TvNo.setTextColor(Color.BLACK)
    }

    private fun checkSmoking() {
        if (binding.checkboxDubiTexi.isChecked) {
            isTravelSelected = binding.TvYes.isChecked
            Smoking = binding.TvSmokingYes.isChecked
            selectedDataList.clear()
            if (binding.checkboxSmall.isChecked) {
                selectedDataList.add(1)
            }
            if (binding.checkboxMedium.isChecked) {
                selectedDataList.add(2)
            }
            if (binding.checkboxLarge.isChecked) {
                selectedDataList.add(3)
            }
        } else {
            selectedDataList.clear()
            selectedDataList.add(1)
            if (binding.checkboxDubiSending.isChecked || binding.checkboxDubiMoving.isChecked) {
                Smoking = false
                isTravelSelected = false
            }
        }
    }
}