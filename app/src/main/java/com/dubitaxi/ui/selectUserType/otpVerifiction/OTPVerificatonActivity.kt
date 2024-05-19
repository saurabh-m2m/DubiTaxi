package com.dubitaxi.ui.selectUserType.otpVerifiction


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.databinding.ActivityPassengerOtpverificatonBinding
import com.dubitaxi.model.DriverViewModel
import com.dubitaxi.model.PassengerViewModel
import com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.DriverPersonalDetailsActivity
import com.dubitaxi.ui.selectUserType.driverSignUp.request.DriverOTPVerificationRequest
import com.dubitaxi.ui.selectUserType.home.HomeActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.PassengerSignUpActivity
import com.dubitaxi.utils.ErrorUtil
import com.dubitaxi.utils.NetworkUtils
import com.dubitaxi.utils.ProgressDialogUtils
import com.dubitaxi.utils.showToast
import com.dubitaxi.utils.statusBarTransparent

class OTPVerificatonActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityPassengerOtpverificatonBinding
    lateinit var mUseOTPVerifyViewModel: PassengerViewModel
    lateinit var mDriverOTPVerification: DriverViewModel

    lateinit var timer: CountDownTimer
    var mGetPhoneNumber = ""
    val termAndConditionText = "Change"
    var number: String? = null
    var countryCode: String? = null


    var fullText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerOtpverificatonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarTransparent()
        initView()
        initControl()
        mObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.mOne.setText("")
        binding.mTwo.setText("")
        binding.mThree.setText("")
        binding.mFour.setText("")
        binding.mOne.requestFocus()
    }


    override fun initView() {
        number = intent.getStringExtra("number")
        countryCode = intent.getStringExtra("countryCode")
        startTimer()
        mOTPText()
        mUseOTPVerifyViewModel = ViewModelProvider(this).get(PassengerViewModel::class.java)
        mDriverOTPVerification = ViewModelProvider(this).get(DriverViewModel::class.java)
        fullText =
            "Enter 4 digit verification code (OTP) sent on your registered Mobile Number " + intent.getStringExtra(
                "countryCode"
            ) + " " + intent.getStringExtra("number").toString() + " " + termAndConditionText + "."
        val clickableTextColor = resources.getColor(R.color.add_Vehicle_blue)
        val spannableString = SpannableString(fullText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(v: View) {
                val intent = Intent();
                intent.putExtra("number", number)
                intent.putExtra("countryCode", countryCode)
                setResult(RESULT_OK, intent);
                finish();
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Remove the underline

            }
        }

        val termAndConditionStartIndex = fullText.indexOf(termAndConditionText)
        val termAndConditionEndIndex = fullText.lastIndex
        spannableString.setSpan(
            clickableSpan,
            termAndConditionStartIndex,
            termAndConditionEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(clickableTextColor),
            termAndConditionStartIndex,
            termAndConditionEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvMobilePhoneP.text = spannableString
        binding.tvMobilePhoneP.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun initControl() {
        binding.btnOTPContinueP.setOnClickListener(this)
        binding.tvResendOtpText.setOnClickListener(this)
    }

    override fun mObserver() {
        mUseOTPVerifyViewModel.mUserOTPVerifyViewModel.observe(this) {
            SharedPreference.accessToken = it.response.access_token
           /* SharedPreference.userName = it.response.full_name.toString()
            SharedPreference.userprofilePic = it.response.profile_picture.toString()*/
            if (it.response.full_name != null) {
                SharedPreference.userName = it.response.full_name.toString()
            }
            if (it.response.profile_picture != null) {
                SharedPreference.userprofilePic = it.response.profile_picture.toString()
            }
            if (it.response.is_profile_complete == 1) {
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra("user", "user")
                })
            } else {
                startActivity(Intent(this, PassengerSignUpActivity::class.java).apply {
                    putExtra("PhoneNumber", number)
                    putExtra("countryCode", countryCode)
                })
            }
            finishAffinity()
        }

        mDriverOTPVerification.mDriverOTPVerificationViewModel.observe(this) {
            /* SharedPreference.userprofilePic = it.response.
             SharedPreference.userName = it.response.fullName*/
            SharedPreference.accessToken = it.response.access_token
            if (it.response?.is_profile_complete == 1) {
                startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra("Driver", "Driver")
                })
            } else {
                startActivity(Intent(this, DriverPersonalDetailsActivity::class.java).apply {
                    putExtra("PhoneNumber", number)
                    putExtra("countryCode", countryCode)
                })
            }
            finishAffinity()
        }


        mUseOTPVerifyViewModel.mResendOTpViewModel.observe(this) {
            startTimer()
            binding.mOne.setText("")
            binding.mTwo.setText("")
            binding.mThree.setText("")
            binding.mFour.setText("")
            binding.mOne.requestFocus()
        }

        mDriverOTPVerification.mDriverResendOTPViewModel.observe(this) {
            startTimer()
            binding.mOne.setText("")
            binding.mTwo.setText("")
            binding.mThree.setText("")
            binding.mFour.setText("")
            binding.mOne.requestFocus()
        }
        //User------------------------------------------------------------------------------------

        mUseOTPVerifyViewModel.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mUseOTPVerifyViewModel.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }

        //Driver------------------------------------------------------------------------------------

        mDriverOTPVerification.mProgress.observe(this) {
            if (it) {
                ProgressDialogUtils.getInstance().showProgress(this, false)
            } else {
                ProgressDialogUtils.getInstance().hideProgress()
            }
        }
        mDriverOTPVerification.mError.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_OTP_continueP -> {
                if (isValidInputs()) {
                    if (NetworkUtils.isInternetAvailable(this)) {
                        if (SharedPreference.userType == "0") {
                            mUseOTPVerifyViewModel.mUserOTPVerifyHitApi(
                                otp = binding.mOne.text.toString() + binding.mTwo.text.toString() + binding.mThree.text.toString() + binding.mFour.text.toString(),
                                access_token = SharedPreference.accessToken
                            )
                        } else {
                            mDriverOTPVerification.mDriverOTPVerificationHitApi(
                                access_token = SharedPreference.accessToken,
                                request = DriverOTPVerificationRequest(otp = binding.mOne.text.toString() + binding.mTwo.text.toString() + binding.mThree.text.toString() + binding.mFour.text.toString())
                            )
                        }
                    }
                }
            }

            R.id.tvResendOtpText -> {
                if (NetworkUtils.isInternetAvailable(this)) {
                    if (SharedPreference.userType == "0") {
                        mUseOTPVerifyViewModel.mResendOTPHitApi(SharedPreference.accessToken)
                    } else {
                        mDriverOTPVerification.mDriverResendOTPHitAPi(SharedPreference.accessToken)
                    }
                }

            }
        }
    }


    fun startTimer() {
        binding.tvResendOtpText.visibility = View.INVISIBLE
        binding.OtpTimer.visibility = View.VISIBLE

        timer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                // time--
                val time1 = millisUntilFinished / 1000

                //makeMeTwoDigits(time, millisUntilFinished)
                makeMeTwoDigits(time1)
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                binding.tvResendOtpText.visibility = View.VISIBLE
                binding.OtpTimer.visibility = View.GONE


            }


        }.start()
    }

    fun makeMeTwoDigits(n: Long) {
        if (n.toString().length < 2) {
            binding.OtpTimer?.setText("00:" + "0$n")
        } else {
            if (binding.OtpTimer != null) {
                n.let {
                    binding.OtpTimer?.setText("00:" + "$n")
                }
            }
        }

    }

    fun mOTPText() {
        binding.mOne?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.mOne?.text!!.toString().isNotEmpty()) {
                    binding.mTwo?.requestFocus()
                    //binding.mOne?.setBackgroundResource(R.drawable.otpitemback)
                } else {
                    binding.mOne?.setBackgroundResource(R.drawable.otpeditbackground)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun afterTextChanged(s: Editable) {
                // edtOne?.setBackgroundResource(R.drawable.otpeditbackground)
            }
        })
        binding.mTwo?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.mTwo?.text!!.toString().length >= 1) {
                    binding.mThree?.requestFocus()
                    //      edtThree?.setBackgroundResource(R.drawable.otpitemback)
                    // binding. mTwo?.setBackgroundResource(R.drawable.otpitemback)
                } else {
                    binding.mOne?.requestFocus()
                    binding.mTwo?.setBackgroundResource(R.drawable.otpeditbackground)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun afterTextChanged(s: Editable) {
                //   edtTwo?.setBackgroundResource(R.drawable.otpeditbackground)
            }
        })
        binding.mThree?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.mThree?.text!!.toString().length >= 1) {
                    binding.mFour?.requestFocus()
                    // edtFour?.setBackgroundResource(R.drawable.otpitemback)
                    //binding.mThree?.setBackgroundResource(R.drawable.otpitemback)
                } else if (binding.mThree?.text!!.isEmpty()) {
                    binding.mTwo?.requestFocus()
                    binding.mThree?.setBackgroundResource(R.drawable.otpeditbackground)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {

                //       edtThree?.setBackgroundResource(R.drawable.otpeditbackground)
            }
        })
        binding.mFour?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.mFour?.text!!.length >= 1) {
                    mHideKeyboard()
                    //binding.mFour?.setBackgroundResource(R.drawable.otpitemback)
                } else if (binding.mFour?.text!!.isEmpty()) {
                    binding.mThree?.requestFocus()
                    binding.mFour?.setBackgroundResource(R.drawable.otpeditbackground)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }

        })

    }

    fun mHideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun isValidInputs(): Boolean {
        if (binding.mOne.text!!.isEmpty() || binding.mTwo.text!!.isEmpty() || binding.mThree.text!!.isEmpty()
            || binding.mFour.text!!.isEmpty()
        ) {
            showToast("Please enter complete OTP")
            return false
        }
        if (!NetworkUtils.isInternetAvailable(applicationContext!!)) {
            ErrorUtil.showErrorMessage(resources.getString(R.string.error_internet), this)
            return false
        }
        return true
    }

}