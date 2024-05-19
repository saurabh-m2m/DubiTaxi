package com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.utils.compressImageFile
import com.dubitaxi.utils.getImageUri
import com.bumptech.glide.Glide
import com.dubitaxi.utils.ImageListener
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityAddVehicleBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.ImageUpdateModal
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.UploadVehicleImageAdapter
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.VehicleTypeAdapter
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.VehicleTypeAdapterA
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.AddVehicleRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.EditVehicleRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.GetParticularVehicleRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.GetVehicleTypeRequest
import com.dubitaxi.utils.AppConstants
import com.dubitaxi.utils.DayListener
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.ImageUtils
import com.dubitaxi.utils.ImageUtils.chooseImageDialog
import com.dubitaxi.utils.NetworkUtils
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.datePicker
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent
import java.io.File
import java.util.Calendar

class AddVehicleActivity : BaseActivity(), View.OnClickListener, ImageListener {
    lateinit var binding: ActivityAddVehicleBinding
    var selectedFromDueDateTimeStamp = ""
    lateinit var mAddVehicleViewModel: DriverViewModel
    var mProfileImage = ""
    private var Uri: Uri? = null
    private var Files: File? = null
    private var list = ArrayList<Uri>()
    private var list2 = ArrayList<String>()
    private var list3 = ArrayList<String>()
    var mImageType: String = ""
    var mVehicleType: String = ""
    var mServiceTypeId = ""
    var vehicle_relation = -1
    var mVehicleId = ""
   var TeramCondition  = false
    var stringimage: ArrayList<ImageUpdateModal>? = ArrayList()

    val checkboxDataMap = mutableMapOf<Int, String>()
    var mPermission = -1
    var mGetServiceType = ArrayList<String>()


    //  var mImageLoadAdapter: UploadVehicleImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initControl()
        mObserver()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun mSetImage() {
        if (Build.VERSION.SDK_INT < 33) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
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
                    this, Manifest.permission.READ_MEDIA_IMAGES
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

    override fun initView() {
        mAddVehicleViewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
        if (intent.hasExtra("VehicleType")) {
            val vehicleType = intent.getSerializableExtra("VehicleType") as ArrayList<String>
            mAddVehicleViewModel.mGetVehicleListHitAPi(access_token = SharedPreference.accessToken, request = GetVehicleTypeRequest(serviceType = vehicleType))
        } else {
            val vehicleId = intent.getStringExtra("Vehicle").toString()
            mAddVehicleViewModel.mGetParticularVehicleHitAPi(SharedPreference.accessToken, request = GetParticularVehicleRequest(vehicleId = vehicleId))
            binding.btnMAddVehicle.text = "Vehicle Update"
        }
        statusBarTransparent()

    }

    override fun initControl() {
        binding.appHeader.setOnClickListener(this)
        binding.TvMVehiclePermisDate.setOnClickListener(this)
        binding.mLayoutVehicle.setOnClickListener(this)
        binding.mLayoutVehicleA.setOnClickListener(this)
        binding.mLayoutVehicleB.setOnClickListener(this)
        binding.mLayoutVehicleC.setOnClickListener(this)
        binding.mLayoutVehicleD.setOnClickListener(this)
        binding.EtMVehicleRegistration.setOnClickListener(this)
        binding.TvMVehicleTech.setOnClickListener(this)
        binding.btnMAddVehicle.setOnClickListener(this)
        binding.mCheckBox1.setOnClickListener(this)
        binding.mCheckBox2.setOnClickListener(this)
        binding.mCheckBox3.setOnClickListener(this)
        binding.mCheckBox4.setOnClickListener(this)
        binding.mRadioPermissionNeedA.setOnClickListener(this)
        binding.mRadioPermissionNoNeedB.setOnClickListener(this)
        binding.mSoatDate.setOnClickListener(this)
        binding.imgMCheck.setOnClickListener(this)
    }

   /* override fun onResume() {
        super.onResume()
        mAddVehicleViewModel.mDriverGetVehiclesListHitAPi(SharedPreference.accessToken)
    }*/

    override fun mObserver() {
        mAddVehicleViewModel.mAddVehicleViewModel.observe(this) {
            //mAddVehicleViewModel.mDriverGetVehiclesListHitAPi(SharedPreference.accessToken)
            onBackPressed()
        }

        mAddVehicleViewModel.mGetVehicleListViewModel.observe(this) {
            binding.VehicleTypeRecyclerView.adapter = VehicleTypeAdapter(this, it.response!!.result,
                object : VehicleTypeAdapter.mShowVehicleData {
                    override fun mVehicleItem(id: String, position: Int) {
                        mVehicleType = id
                    }

                })
        }

        mAddVehicleViewModel.mEditVehicleViewModel.observe(this) {
            showToast(it.message)
            onBackPressed()
        }
        mAddVehicleViewModel.mDriverParticularVehicleViewModel.observe(this) {
            mVehicleId = it.response.result.id
            mProfileImage  = it.response.result.inspectionSheetLink
            mProfileImage  = it.response.result.soatLink
            mProfileImage  = it.response.result.permissionLink
            mProfileImage  = it.response.result.soatLink
            mVehicleType = it.response.result.vehicleType
            var mpostion: Int? = -1
            val selectvehicletypr: String? = it.response.result.vehicleTypeName
            for (i in 0 until it.response.result.allOtherVehicleTypes.size) {
                if (it.response.result.allOtherVehicleTypes.get(i).vehicleType.equals(selectvehicletypr)) {
                    mpostion = i
                }
            }
            binding.VehicleTypeRecyclerView.adapter = VehicleTypeAdapterA(this, it.response.result.allOtherVehicleTypes, mpostion!!, object : VehicleTypeAdapterA.mShowVehicleData {
                    override fun mVehicleItem(id: String, position: Int) {
                        mVehicleType = id
                    }
                })
            for (i in 0 until it.response.result.images.size) {
                list2.add(it.response.result.images.get(i))
                var aaaaa = ImageUpdateModal(it.response.result.images.get(i), 1, 0)
                stringimage!!.add(aaaaa)

            }
            binding.mUploadVehileImagerecyclerview.adapter = UploadVehicleImageAdapter(this, stringimage!!, 1, object : UploadVehicleImageAdapter.mCancleImageClick {
                override fun imageCancle(position: Int, sataus: Int) {
                        if (sataus == 0) {
                            list2.removeAt(position)
                        }
                    }
                })
            if (it.response.result.relationWithVehicle == 1) {
                binding.mCheckBox1.isChecked = true
            } else if (it.response.result.relationWithVehicle == 2) {
                binding.mCheckBox2.isChecked = true
            } else if (it.response.result.relationWithVehicle == 3) {
                binding.mCheckBox3.isChecked = true
            } else if (it.response.result.relationWithVehicle == 4) {
                binding.mCheckBox4.isChecked = true
            }
            if (it.response.result.permissions == 1) {
                binding.mRadioPermissionNeedA.isChecked = true
            } else if (it.response.result.permissions == 2) {
                binding.mRadioPermissionNoNeedB.isChecked = true
            }
            binding.EtCarNumber.setText(it.response.result.vehicleNumber)
            binding.EtCarModelNumber.setText(it.response.result.modelName)
            binding.mSoatNumber.setText(it.response.result.soatNumber)
            binding.mSoatDate.setText(it.response.result.soatDate)
            Glide.with(this).load(it.response.result.soatLink).into(binding.uploadSoatImage)
            binding.uploadSoatImage.visibility = View.VISIBLE
            binding.EtMVehicleColor.setText(it.response.result.color)
            binding.EtMVehicleSeat.setText(it.response?.result?.seatsAvailable.toString())
            Glide.with(this).load(it.response.result.vehicleRegCertLink).into(binding.uploadRegistrationImage)
            binding.uploadRegistrationImage.visibility = View.VISIBLE
            binding.EtMVehicleRegistration.setText(it.response.result.vehicleRegCertDate)
            Glide.with(this).load(it.response.result.inspectionSheetLink).into(binding.Vehicletechnical)
            binding.Vehicletechnical.visibility = View.VISIBLE
            binding.TvMVehicleTech.setText(it.response.result.inspectionSheetDate)
            Glide.with(this).load(it.response.result.permissionLink).into(binding.UploadPermission)
            binding.UploadPermission.visibility = View.VISIBLE
            binding.TvMVehiclePermisDate.setText(it.response.result.permissionsDate)

        }

        mAddVehicleViewModel.mDriverUpLoadImageViewModel.observe(this) {
            if (mImageType == "0") {
                mProfileImage = it.response
                binding.uploadSoatImage.visibility = View.VISIBLE
                Glide.with(this).load(it.response).into(binding.uploadSoatImage)
            } else if (mImageType == "1") {
                list3.add(it.response)
                list2.add(it.response)
            } else if (mImageType == "2") {
                mProfileImage = it.response
                Glide.with(this).load(it.response).into(binding.uploadRegistrationImage)
                binding.uploadRegistrationImage.visibility = View.VISIBLE
            } else if (mImageType == "3") {
                mProfileImage = it.response
                Glide.with(this).load(it.response).into(binding.Vehicletechnical)
                binding.Vehicletechnical.visibility = View.VISIBLE
            } else if (mImageType == "4") {
                mProfileImage = it.response
                Glide.with(this).load(it.response).into(binding.UploadPermission)
                binding.UploadPermission.visibility = View.VISIBLE
            }
        }


        mAddVehicleViewModel.mProgress.observe(this) {
            if (it) {
               // ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }


        mAddVehicleViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.app_header -> {
                onBackPressed()
            }
            R.id.btn_mAddVehicle -> {
                mAddVehicleHitApi()
            }
            R.id.mLayoutVehicle -> {
                mImageType = "0"
                mSetImage()
            }
            R.id.mLayoutVehicleA -> {
                mImageType = "1"
                mSetImage()
            }

            R.id.mLayoutVehicleB -> {
                mImageType = "2"
                mSetImage()
            }

            R.id.mLayoutVehicleC -> {
                mImageType = "3"
                mSetImage()
            }

            R.id.mLayoutVehicleD -> {
                mImageType = "4"
                mSetImage()
            }

            R.id.mVehicleTypeCheckBox -> {

            }

            R.id.mSoatDate -> {
                datePicker(
                    this, object : DayListener {
                        override fun getDate(dob: String, dobTimeStamp: String) {
                            binding.mSoatDate.setText(dob)
                            selectedFromDueDateTimeStamp = dobTimeStamp
                        }
                    }, Calendar.getInstance().timeInMillis
                )
            }

            R.id.mCheckBox1 -> {
                vehicle_relation = 1
                mRelationshipselect()
                binding.mCheckBox1.isChecked = true
            }

            R.id.mCheckBox2 -> {
                vehicle_relation = 2
                mRelationshipselect()
                binding.mCheckBox2.isChecked = true
            }

            R.id.mCheckBox3 -> {
                vehicle_relation = 3
                mRelationshipselect()
                binding.mCheckBox3.isChecked = true


            }



            R.id.mCheckBox4 -> {
                vehicle_relation = 4
                mRelationshipselect()
                binding.mCheckBox4.isChecked = true

            }

            R.id.mRadioPermissionNeedA -> {
                mPermission = 1
                binding.mRadioPermissionNeedA.isChecked = true
                binding.mRadioPermissionNoNeedB.isChecked = false
            }

            R.id.mRadioPermissionNoNeedB -> {
                mPermission = 2
                binding.mRadioPermissionNeedA.isChecked = false
                binding.mRadioPermissionNoNeedB.isChecked = true
            }

            R.id.img_mCheck-> TeramCondition = TeramCondition==false

            R.id.Tv_mVehiclePermisDate -> {
                datePicker(
                    this, object : DayListener {
                        override fun getDate(dob: String, dobTimeStamp: String) {
                            binding.TvMVehiclePermisDate.setText(dob)
                            selectedFromDueDateTimeStamp = dobTimeStamp
                        }
                    }, Calendar.getInstance().timeInMillis
                )
            }

            R.id.Et_mVehicleRegistration -> {
                datePicker(
                    this, object : DayListener {
                        override fun getDate(dob: String, dobTimeStamp: String) {
                            binding.EtMVehicleRegistration.setText(dob)
                            selectedFromDueDateTimeStamp = dobTimeStamp
                        }
                    }, Calendar.getInstance().timeInMillis
                )
            }

            R.id.Tv_mVehicleTech -> {
                datePicker(
                    this, object : DayListener {
                        override fun getDate(dob: String, dobTimeStamp: String) {
                            binding.TvMVehicleTech.setText(dob)
                            selectedFromDueDateTimeStamp = dobTimeStamp
                        }
                    }, Calendar.getInstance().timeInMillis
                )
            }
        }
    }
    fun mRelationshipselect() {
        binding.mCheckBox1.isChecked = false
        binding.mCheckBox2.isChecked = false
        binding.mCheckBox3.isChecked = false
        binding.mCheckBox4.isChecked = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.CAMERA) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage = data.extras!!["data"] as Bitmap?
                val imageuri = (this as AppCompatActivity).getImageUri(selectedImage!!)
                Uri = imageuri
                Files = compressImageFile(this, imageuri)
                if (mImageType == "0") {
                    mProfileImage = imageuri.toString()
                } else if (mImageType == "1") {
                    list.add(Uri!!)
                    stringimage!!.clear()
                    for (i in 0 until list2.size) {
                        val aaaaa = ImageUpdateModal(list2.get(i).toString(), 1, 0)
                        stringimage!!.add(aaaaa)
                    }
                    for (i in 0 until list.size) {
                        val aaaaa = ImageUpdateModal(list.get(i).toString(), 0, 1)
                        stringimage!!.add(aaaaa)
                    }
                    binding.mUploadVehileImagerecyclerview.adapter = UploadVehicleImageAdapter(this, stringimage!!, 0,
                        object : UploadVehicleImageAdapter.mCancleImageClick {
                            override fun imageCancle(position: Int, status: Int) {
                            if (status == 0) {
                                list2.removeAt(position)
                            } else if (status == 1) {
                                list.removeAt(position)
                            }
                        }
                        })
                } else if (mImageType == "2") {
                    mProfileImage = imageuri.toString()
                } else if (mImageType == "3") {
                    mProfileImage = imageuri.toString()
                } else {
                    mProfileImage = imageuri.toString()
                }
            }
        } else if (requestCode == AppConstants.GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage: Uri? = data.data
                Uri = selectedImage
                Files = compressImageFile(this, selectedImage!!)
                if (mImageType == "0") {
                    mProfileImage = selectedImage.toString()
                } else if (mImageType == "1") {
                    list.add(Uri!!)
                    stringimage!!.clear()
                    for (i in 0 until list2.size) {
                        val aaaaa = ImageUpdateModal(list2.get(i).toString(), 1, 0)
                        stringimage!!.add(aaaaa)
                    }
                    for (i in 0 until list.size) {
                        val aaaaa = ImageUpdateModal(list.get(i).toString(), 0, 1)
                        stringimage!!.add(aaaaa)
                    }
                    binding.mUploadVehileImagerecyclerview.adapter = UploadVehicleImageAdapter(this, stringimage!!, 0,
                        object : UploadVehicleImageAdapter.mCancleImageClick {
                            override fun imageCancle(position: Int, status: Int) {
                                if (status == 0) {
                                    list2.removeAt(position)
                                } else if (status == 1) {
                                    list.removeAt(position)
                                }
                            }
                        })
                } else if (mImageType == "2") {
                    mProfileImage = selectedImage.toString()
                } else if (mImageType == "3") {
                    mProfileImage = selectedImage.toString()
                } else {
                    mProfileImage = selectedImage.toString()
                }

            }
        }
        if (requestCode == AppConstants.GALLERY || requestCode == AppConstants.CAMERA) {
            ImageUtils.onActivityResult(requestCode, resultCode, data, this, this)
        }
    }

    override fun getImageData(uri: Uri?, bm: Bitmap?, file: File?) {
        Files = file
        if (mImageType == "0") {
            mAddVehicleViewModel.mDriverUpLoadImageHitApi(file = file)
        } else if (mImageType == "1") {
            mAddVehicleViewModel.mDriverUpLoadImageHitApi(file = file)
        } else if (mImageType == "2") {
            mAddVehicleViewModel.mDriverUpLoadImageHitApi(file = file)
        } else if (mImageType == "3") {
            mAddVehicleViewModel.mDriverUpLoadImageHitApi(file = file)
        } else if (mImageType == "4") {
            mAddVehicleViewModel.mDriverUpLoadImageHitApi(file = file)
        }
    }

    fun mAddVehicleHitApi() {
        if (NetworkUtils.isInternetAvailable(this)) {
            if (intent.getStringExtra("AddVehicle").equals("AddVehicle")) {
                when {
                    mVehicleType.isNullOrEmpty() -> {
                        showToast("Please select Vehicle Type")
                    }
                    binding.EtCarNumber.text!!.isNullOrEmpty() -> {
                        showToast("Please Enter Vehicle Number")
                    }

                    binding.EtCarNumber.text!!.length !in 6..6 -> {
                        showToast("Please Enter Vehicle length must be at  6 character")
                    }
                    binding.EtCarModelNumber.text.isNullOrEmpty() -> {
                        showToast("Please Enter Vehicle Model Name")
                    }

                    binding.mSoatNumber.text.isNullOrEmpty() -> {
                        showToast("Please Enter soat number")
                    }

                    binding.mSoatNumber.text!!.length !in 9..12 -> {
                        showToast("Please Enter min 9 max 12 soat number")
                    }

                    binding.mSoatDate.text.isNullOrEmpty() -> {
                        showToast("Please soat date")
                    }

                    mProfileImage.isNullOrEmpty() -> {
                        showToast("Please upload  SOAT image")
                    }

                    list3.isNullOrEmpty() -> {
                        showToast("Please upload Vehicle Image")
                    }

                    binding.EtMVehicleColor.text.isNullOrEmpty() -> {
                        showToast("Please Enter Vehicle Color")
                    }

                    binding.EtMVehicleSeat.text.isNullOrEmpty() -> {
                        showToast("Please Enter Number of Available Seats")
                    }

                    mProfileImage.isNullOrEmpty() -> {
                        showToast("Please Enter Vehicle Registration Certificate ")
                    }

                    binding.EtMVehicleRegistration.text.isNullOrEmpty() -> {
                        showToast("Please selected Vehicle Registration Certification Date")
                    }

                    vehicle_relation == -1 -> {
                        showToast("Please selected Vehicle and Relationship with Vehicle")
                    }

                    mProfileImage.isNullOrEmpty() -> {
                        showToast("Please Enter Vehicle technical inspection sheet image")
                    }

                    binding.TvMVehicleTech.text.isNullOrEmpty() -> {
                        showToast("Please Enter Vehicle technical inspection sheet Date")
                    }

                    mPermission == -1 -> {
                        showToast("Permissions/allowance/licence for drive people")
                    }

                    mProfileImage.isNullOrEmpty() -> {
                        showToast("Upload Permission / Permiso de taxi Image")
                    }

                    binding.TvMVehiclePermisDate.text.isNullOrEmpty() -> {
                        showToast("Please Enter Permiso dete taxi")
                    }

                    !binding.imgMCheck.isChecked -> {
                        showToast("Please accept que tada information")
                    }
                    else -> {
                        mAddVehicleViewModel.mAddVehicleHitApi(
                            access_token = SharedPreference.accessToken,
                            request = AddVehicleRequest(
                                accepted_terms =TeramCondition,
                                color = binding.EtMVehicleColor.text.toString(),
                                images = list3,
                                inspection_sheet_date = binding.TvMVehicleTech.text.toString(),
                                inspection_sheet_link = mProfileImage,
                                model_name = binding.EtCarModelNumber.text.toString(),
                                permissions = mPermission,
                                permissions_date = binding.TvMVehiclePermisDate.text.toString(),
                                permissions_doc = mProfileImage,
                                registration_certificate = mProfileImage,
                                registration_certificate_date = binding.EtMVehicleRegistration.text.toString(),
                                seats_available = binding.EtMVehicleSeat.text.toString(),
                                soat = mProfileImage,
                                soat_date = binding.mSoatDate.text.toString(),
                                soat_number = binding.mSoatNumber.text.toString(),
                                vehicle_number = binding.EtCarNumber.text.toString(),
                                vehicle_relation = vehicle_relation!!.toInt(),
                                vehicle_relation_desc = binding.tvMDescribe.text.toString(),
                                vehicle_type = mVehicleType
                            )
                        )
                    }
                }
            } else if (intent.getStringExtra("VehicleA").equals("VehicleA")) {
                mAddVehicleViewModel.mEditVehicleHitAPi(
                    access_token = SharedPreference.accessToken,
                    request = EditVehicleRequest(
                        acceptedTerms =TeramCondition,
                        color = binding.EtMVehicleColor.text.toString(),
                        images = list2,
                        inspectionSheetDate = binding.TvMVehicleTech.text.toString(),
                        inspectionSheetLink = mProfileImage,
                        modelName = binding.EtCarModelNumber.text.toString(),
                        permissions = mPermission,
                        permissionsDate = binding.TvMVehiclePermisDate.text.toString(),
                        permissionsDoc = mProfileImage,
                        registrationCertificate = mProfileImage,
                        registrationCertificateDate = binding.EtMVehicleRegistration.text.toString(),
                        seatsAvailable = binding.EtMVehicleSeat.text.toString(),
                        soat = mProfileImage,
                        soatDate = binding.mSoatDate.text.toString(),
                        soatNumber = binding.mSoatNumber.text.toString(),
                        vehicleId = mVehicleId,
                        vehicleNumber = binding.EtCarNumber.text.toString(),
                        vehicleRelation = vehicle_relation!!.toInt(),
                        vehicleRelationDesc = binding.tvMDescribe.text.toString(),
                        vehicleType = mVehicleType
                    )
                )


            } else {
                showToast("Im doing wrong")
            }
        } else {
            showToast(resources.getString(R.string.error_internet))
        }
    }
}