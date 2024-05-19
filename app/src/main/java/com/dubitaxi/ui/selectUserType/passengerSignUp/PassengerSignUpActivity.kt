package com.dubitaxi.ui.selectUserType.passengerSignUp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.utils.compressImageFile
import com.dubitaxi.utils.getImageUri
import com.dubitaxi.utils.ImageListener
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityPassengerSignUpBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.home.HomeActivity
import com.dubitaxi.utils.*
import com.dubitaxi.utils.ImageUtils.chooseImageDialog
import java.io.File

class PassengerSignUpActivity : BaseActivity(), View.OnClickListener, ImageListener {
    lateinit var binding: ActivityPassengerSignUpBinding
    var mChecked = false
    private var Uri: Uri? = null
    private var Files: File? = null
    var gender = ""
    var mProfileImage = ""
    var agreeTermConditioonValue: String? = "false"
    lateinit var mUseCreatePrifleViewModel: PassengerViewModel
    lateinit var mUserProfileViewModel: DriverViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun initView() {
        binding.ccp.setTypeFace(null, Typeface.BOLD)
        binding.ccp.setCountryForPhoneCode(SharedPreference.countryCode.toInt())
        binding.EtPhoneDriver.text = SharedPreference.mobileNumber
        mUseCreatePrifleViewModel = ViewModelProvider(this)[PassengerViewModel::class.java]
        mUserProfileViewModel = ViewModelProvider(this)[DriverViewModel::class.java]
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
        binding.mRb1.setOnClickListener(this)
        binding.mRb2.setOnClickListener(this)
        binding.mRb3.setOnClickListener(this)
        binding.CheckBoxReferralP.setOnClickListener(this)
        binding.btnPassengerContinue.setOnClickListener(this)
        binding.imgCameraPicker.setOnClickListener(this)
        binding.tvTerm.setOnClickListener(this)
    }

    override fun mObserver() {
        mUseCreatePrifleViewModel.mUserProfileCreate.observe(this) {
            SharedPreference.userprofilePic = it.response.profilePicture
            SharedPreference.userName = it.response.fullName
            startActivity(Intent(this, HomeActivity::class.java).apply {
                putExtra("user", "user")
            })
            finish()

        }
        mUserProfileViewModel.mDriverUpLoadImageViewModel.observe(this) {
            mProfileImage = it.response

        }
        mUseCreatePrifleViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mUseCreatePrifleViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.mRb1 -> {
                binding.mRb1.isChecked = true
                binding.mRb2.isChecked = false
                binding.mRb3.isChecked = false
                gender = "0"
            }

            R.id.mRb2 -> {
                binding.mRb1.isChecked = false
                binding.mRb2.isChecked = true
                binding.mRb3.isChecked = false
                gender = "1"

            }

            R.id.mRb3 -> {
                binding.mRb1.isChecked = false
                binding.mRb2.isChecked = false
                gender = "2"
                binding.mRb3.isChecked = true
            }

            R.id.Check_box_ReferralP -> {
                if (mChecked) {
                    binding.layoutReferralCodeP.visibility = View.GONE
                    mChecked = false
                } else {
                    binding.layoutReferralCodeP.visibility = View.VISIBLE
                    mChecked = true
                }
            }
            R.id.tvTerm -> {
                val i = Intent(this, TermAndConditionActivity::class.java)
                startActivityForResult(i, 1)
            }

            R.id.btn_passenger_continue -> {

                isBasicDetailsHit()
            }

            R.id.img_camera_picker -> {
                mSetImage()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.CAMERA) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImage = data.extras!!["data"] as Bitmap?

                val imageuri = (this as AppCompatActivity).getImageUri(selectedImage!!)
                Uri = imageuri
                Files = compressImageFile(this, imageuri)
                mProfileImage = selectedImage.toString()
                binding.CircleImgPassenger.setImageURI(imageuri)
            }
        } else if (requestCode == AppConstants.GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val selectedImage: Uri? = data.data
                Uri = selectedImage
                Files = compressImageFile(this, selectedImage!!)
                mProfileImage = selectedImage.toString()
                binding.CircleImgPassenger.setImageURI(selectedImage)

            }
        }
        if (resultCode == RESULT_OK) {
            agreeTermConditioonValue = data?.getStringExtra("result")
            binding.Term.isChecked = agreeTermConditioonValue == "true"
        }
        if (requestCode == AppConstants.GALLERY || requestCode == AppConstants.CAMERA) {
            ImageUtils.onActivityResult(requestCode, resultCode, data, this, this)
        }
    }

    override fun getImageData(uri: Uri?, bm: Bitmap?, file: File?) {
        Files = file
        mUserProfileViewModel.mDriverUpLoadImageHitApi(file = file)

    }

    fun isBasicDetailsHit() {
        if (NetworkUtils.isInternetAvailable(this)) {
            when {
                /*mProfileImage.isNullOrEmpty() -> {
                    showToast("Please upload Profile image")
                }*/

                binding.EtPassengerName.text.toString().trim().isNullOrEmpty() -> {
                    showToast("Please Enter Frist Name ")
                }

                binding.EtPassengerEmail.text.toString().isNullOrEmpty() -> {
                    showToastContext("Please Enter Email Id ")
                }

                !AppConstants.EMAIL_PATTERN.toRegex()
                    .matches(binding.EtPassengerEmail.text.toString()) -> {
                    showToastContext("Please enter a valid email address")
                }
                gender.isNullOrEmpty() -> {
                    showToast("Please select Gender")
                }

                 !binding.Term.isChecked -> {
                     showToast("Please accept the terms and conditions")
                 }
                else -> {
                    ProgressDialogUtils.getInstance().showProgress(this, false)
                    mUseCreatePrifleViewModel.mUserProfileCreateHitApi(
                        access_token = SharedPreference.accessToken,
                        profile_picture = mProfileImage,
                        full_name = binding.EtPassengerName.text.toString(),
                        email = binding.EtPassengerEmail.text.toString(),
                        country_code = binding.ccp.selectedCountryCodeWithPlus,
                        phone_number = binding.EtPhoneDriver.text.toString(),
                        gender = gender,
                        is_profile_complete = ""
                    )
                }
            }
        } else {
            showToast(resources.getString(R.string.error_internet))
        }
    }
}