package com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityDriverSignUpBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.ui.selectUserType.driverSignUp.adapter.UploadIdentityCardNumberAdapter
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.DriverDetailActivity
import com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.request.DriverPersonalDetailsRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.TermAndConditionActivity
import com.dubitaxi.utils.*
import com.dubitaxi.utils.AppConstants.Companion.EMAIL_PATTERN
import com.dubitaxi.utils.ImageUtils.chooseImageDialog
import java.io.File


open class DriverPersonalDetailsActivity : BaseActivity(), View.OnClickListener, ImageListener {
    lateinit var binding: ActivityDriverSignUpBinding
    lateinit var mDriverCreateAccountViewModel: DriverViewModel
    var mcountry = ""
    var mChecked = false
    var mProfileImage = ""
    var agreeTermConditioonValue: String? = null
    private var Uri: Uri? = null
    private var Files: File? = null
    private var list = ArrayList<Uri>()
    private var list2 = ArrayList<String>()
    var newCountryList = ArrayList<String>()
    private var image_type: String = ""
    var mGender = ""
    var SmokingStatus = ""
    var DniNumber = "DNI"
    var positionImage:Int?=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDriverSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()

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

    override fun initView() {
        mDriverCreateAccountViewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
        getCountryList()
        mcountry = intent.getStringExtra("countryCode").toString()
        binding.EtPhoneDriver.setText(intent.getStringExtra("PhoneNumber"))
        binding.ccp.textView_selectedCountry.setText(mcountry)
        binding.ccp.setTypeFace(null, Typeface.BOLD)
        list = ArrayList()
        binding.UploadIdentityCardNumber.adapter = UploadIdentityCardNumberAdapter(this, list,
            object : UploadIdentityCardNumberAdapter.mCancleImageClick {
                override fun imageCancle(position: Int) {
                    positionImage=position
                    list2.removeAt(position)
                }
            })
    }
    override fun initControl() {
        binding.btnPassengerContinue.setOnClickListener(this)
        binding.DniNumber.setOnClickListener(this)
        binding.imgCameraPicker.setOnClickListener(this)
        binding.mLayout.setOnClickListener(this)
        binding.DriverCity.setOnClickListener(this)
        binding.CheckBoxReferralDriver.setOnClickListener(this)
        binding.radioButtonMale.setOnClickListener(this)
        binding.radioButtonFemale.setOnClickListener(this)
        binding.radioButtonYes.setOnClickListener(this)
        binding.radioButtonNo.setOnClickListener(this)
        binding.tvTerm.setOnClickListener(this)


    }

    override fun mObserver() {
        mDriverCreateAccountViewModel.mDriverUpLoadImageViewModel.observe(this) {
            if (image_type == "0") {
                mProfileImage = it.response.toString()
            } else {
                list2.add(it.response)
            }
        }
        mDriverCreateAccountViewModel.mDriverGetCityViewModel.observe(this) {
            newCountryList = it.response.cities.map {
                it.name
            } as ArrayList<String>
        }


        mDriverCreateAccountViewModel.mDriverPersonalDetailViewModel.observe(this) {

            if (it.response?.result?.full_name!=null){
                SharedPreference.driverName =it.response?.result?.full_name
            }
            if (it.response?.result?.profile_picture!=null){
                SharedPreference.driverprofilePic = it.response?.result?.profile_picture
            }
            showToast(it.message)
            startActivity(Intent(this, DriverDetailActivity::class.java))
            finish()
        }

        mDriverCreateAccountViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mDriverCreateAccountViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun getCountryList() {
        if (NetworkUtils.isInternetAvailable(this)) {
            mDriverCreateAccountViewModel!!.mDriverCityHitAPi(SharedPreference.accessToken)
        } else {
            showToast("Internet Is Not Available")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_passenger_continue -> {
                isDriverPersonalDetail()
            }

            R.id.DniNumber -> {
                DirectorIdentificationNumber()
            }

            R.id.mLayout -> {
                if (list2.size < 2) {
                    image_type = "1"
                    mSetImage()
                } else {
                    showToast("please upload maximum two image")
                }

            }

            R.id.img_camera_picker -> {
                image_type = "0"
                mSetImage()
            }

            R.id.DriverCity -> {
                pop()
            }

            R.id.tvTerm -> {
                val i = Intent(this, TermAndConditionActivity::class.java)
                startActivityForResult(i, 1)
            }

            R.id.Check_box_ReferralDriver -> {
                if (mChecked) {
                    binding.layoutReferralCodeP.visibility = View.GONE
                    mChecked = false
                } else {
                    binding.layoutReferralCodeP.visibility = View.VISIBLE
                    mChecked = true
                }
            }

            R.id.radioButtonMale -> {
                mGender = "1"
                binding.radioButtonMale.isChecked = true
                binding.radioButtonFemale.isChecked = false
            }

            R.id.radioButtonFemale -> {
                mGender = "2"
                binding.radioButtonMale.isChecked = false
                binding.radioButtonFemale.isChecked = true
            }

            R.id.radioButtonYes -> {
                SmokingStatus = true.toString()
                binding.radioButtonYes.isChecked = true
                binding.radioButtonNo.isChecked = false
            }

            R.id.radioButtonNo -> {
                SmokingStatus = false.toString()
                binding.radioButtonYes.isChecked = false
                binding.radioButtonNo.isChecked = true
            }
        }
    }


    private fun DirectorIdentificationNumber() {
        val popup = PopupMenu(this, binding.DniNumber)
        val list: ArrayList<String> = arrayListOf("DNI", "PASS", "CE")
        for (element in list) {
            popup.menu.add(element)

        }
        popup.setOnMenuItemClickListener { item ->
            binding.DniNumber.setText(item?.title.toString())
            if (item.title.toString().equals("DNI")) {
                binding.EtDriverDni.setText("")
                val maxLength = 8
                val filters = arrayOfNulls<InputFilter>(1)
                filters[0] = LengthFilter(maxLength)
                binding.EtDriverDni.setFilters(filters)
                binding.EtDriverDni.inputType = InputType.TYPE_CLASS_NUMBER

                binding.EtDriverDni.hint = "Enter DNI Number"
                binding.tvDriverDni.hint = "Enter DNI Number"

                DniNumber = "DNI"


            } else if (item.title.toString().equals("PASS")) {
                binding.EtDriverDni.setText("")
                val maxLength = 12
                val filters = arrayOfNulls<InputFilter>(1)
                filters[0] = LengthFilter(maxLength)
                binding.EtDriverDni.setFilters(filters)
                binding.EtDriverDni.inputType = InputType.TYPE_CLASS_TEXT
                binding.EtDriverDni.hint = "Enter Pass Number"
                binding.tvDriverDni.hint = "Enter Pass Number"
                DniNumber = "PASS"

            } else if (item.title.toString().equals("CE")) {
                binding.EtDriverDni.setText("")
                val maxLength = 12
                val filters = arrayOfNulls<InputFilter>(1)
                filters[0] = LengthFilter(maxLength)
                binding.EtDriverDni.setFilters(filters)
                binding.EtDriverDni.inputType = InputType.TYPE_CLASS_TEXT
                binding.EtDriverDni.hint = "Enter CE Number"
                binding.tvDriverDni.hint = "Enter CE Number "
                DniNumber = "CE"
            }

            true
        }
        popup.show()
    }

    private fun pop() {
        val popup = PopupMenu(this, binding.DriverCity)
        for (element in newCountryList) {
            popup.menu.add(element)
        }
        popup.setOnMenuItemClickListener { item ->
            binding.DriverCity.setText(item?.title.toString())
            true
        }
        popup.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            agreeTermConditioonValue = data?.getStringExtra("result")
            binding.Teram.isChecked = agreeTermConditioonValue == "true"
        }
        if (requestCode == AppConstants.CAMERA) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage = data.extras!!["data"] as Bitmap?
                val imageuri = (this as AppCompatActivity).getImageUri(selectedImage!!)
                Uri = imageuri
                Files = compressImageFile(this, imageuri)
                if (image_type == "0") {
                    mProfileImage = selectedImage.toString()
                    binding.CircleImgPassenger.setImageURI(imageuri)
                } else {
                    list.add(Uri!!)
                    (binding.UploadIdentityCardNumber.adapter)?.notifyDataSetChanged()
                }
            }
        } else if (requestCode == AppConstants.GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage: Uri? = data.data
                Uri = selectedImage
                Files = compressImageFile(this, selectedImage!!)
                if (image_type == "0") {
                    mProfileImage = selectedImage.toString()
                    binding.CircleImgPassenger.setImageURI(selectedImage)
                } else {
                    list.add(Uri!!)
                    (binding.UploadIdentityCardNumber.adapter)?.notifyDataSetChanged()
                }

            }
        }
        if (requestCode == AppConstants.GALLERY || requestCode == AppConstants.CAMERA) {
            ImageUtils.onActivityResult(requestCode, resultCode, data, this, this)
        }
    }


    override fun getImageData(uri: Uri?, bm: Bitmap?, file: File?) {
        Files = file
        mDriverCreateAccountViewModel.mDriverUpLoadImageHitApi(file = file)
        if (image_type == "0") {
            Glide.with(this).load(uri).into(binding.CircleImgPassenger)
        }

    }

    fun isDriverPersonalDetail() {
        if (NetworkUtils.isInternetAvailable(this)) {
            when {
                mProfileImage.trim().isNullOrEmpty() -> {
                    showToast("Please upload Profile image")
                }

                binding.EtDriverName.text.toString().trim().isNullOrEmpty() -> {
                    showToast("Please Enter First Name")
                }

                binding.EtDriverEmail.text.toString().isNullOrEmpty() -> {
                    showToastContext("Please Enter Email Id ")
                }

                !EMAIL_PATTERN.toRegex().matches(binding.EtDriverEmail.text.toString()) -> {
                    showToastContext("Please enter a valid email address")
                }
                binding.EtDriverDni.text.toString().trim()
                    .isNullOrEmpty() && binding.tvDriverDni.hint.contains("DNI") -> {
                    showToastContext("Please enter DNI")
                }

                /* !binding.EtDriverDni.text.toString().trim()
                     .isNullOrEmpty() && binding.EtDriverDni.text!!.length !in 8..8 -> {
                     showToastContext("Please enter min Length 8 digit and maxLength  8 digit")
                 }*/

                binding.EtDriverDni.text.toString().trim().isNullOrEmpty()
                        && binding.tvDriverDni.hint.contains("Pass") -> {
                    showToastContext("Please enter pass number")
                }


                /*!binding.EtDriverDni.text.toString().trim()
                    .isNullOrEmpty() && binding.tvDriverDni.hint.contains("Pass") && binding.tvDriverDni.text!!.length !in 12..12 -> {
                    showToastContext("Please enter min Length 12 character and maxLength 12 character ")
                }*/

                binding.EtDriverDni.text.toString().trim().isNullOrEmpty() -> {
                    showToastContext("Please enter CE number")
                }
                /*binding.EtDriverDni.text!!.length !in 12..12 -> {
                    showToastContext("Please enter min Length 12 character  and maxLength  12 character")

                }*/
                list2.size == 0 ->{
                    showToast("Please upload front image")
                }
                list2.size == 1 ->{
                    showToast("Please upload back image")
                }


                list2.isNullOrEmpty() -> {
                    showToast("Please upload identity card image")
                }

                mGender.isNullOrEmpty() -> {
                    showToast("Please select gender")
                }

                SmokingStatus.isNullOrEmpty() -> {
                    showToast("Please select Smoking Status")
                }

                binding.DriverCity.text.isNullOrEmpty() -> {
                    showToastContext("Please select city")
                }

                 !binding.Teram.isChecked -> {
                     showToast("Please accept the terms and conditions")
                 }
                else -> {
                    mDriverCreateAccountViewModel.mDriverPersonalDetailsHitAPi(
                        access_token = SharedPreference.accessToken,
                        request = DriverPersonalDetailsRequest(
                            name = binding.EtDriverName.text.toString(),
                            country_code = binding.ccp.selectedCountryCodeWithPlus,
                            phone_number = binding.EtPhoneDriver.text.toString(),
                            email = binding.EtDriverEmail.text.toString(),
                            verification_document_type = DniNumber,
                            verification_document_num = binding.EtDriverDni.text.toString(),
                            idCardImages = list2,
                            gender = mGender,
                            smoker = SmokingStatus,
                            city = binding.DriverCity.text.toString(),
                            lat = "12.2212",
                            long = "11.2356",
                            referralCode = "HWXYZ",
                            profile_picture = mProfileImage,
                            deviceToken = "ABC",
                            device_type = "1212544"
                        )
                    )
                }
            }
        } else {
            showToast(resources.getString(R.string.error_internet))
        }

    }


}