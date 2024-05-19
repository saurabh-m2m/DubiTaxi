package com.dubitaxi.utils

import android.content.Context
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dubitaxi.utils.AppConstants.Companion.EMAIL_PATTERN
import com.dubitaxi.utils.AppConstants.Companion.NAME_PATTERN
import com.dubitaxi.utils.AppConstants.Companion.NUMBER_PATTERN
import com.dubitaxi.utils.AppConstants.Companion.PASSWORD_PATTERN


class Validation {

    fun loginMobileValidation(
        context: Context,
        etEmail: EditText,
        etPassword: EditText

    ): Boolean {

        if (etEmail.text.toString().isNullOrEmpty()) {
            context.showToastContext(" Please enter registered Email id")
            return false
        } else if (etEmail.text.toString().isBlank()) {
            context.showToastContext("Please enter email address")
            return false

        } else if (!EMAIL_PATTERN.toRegex().matches(etEmail.text.toString())) {
            context.showToastContext("Please enter a valid email address")
            return false

        } else if (etPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter password")
            return false
        } else
            return true
    }


    fun mUserSingUp(
        context: Context,
        Et_phone_passenger: EditText,

        ): Boolean {
        return when {
            Et_phone_passenger.text.toString().isEmpty() && Et_phone_passenger.text.toString().length != 10 -> {
                Et_phone_passenger.error = "Please enter valid Mobile Number"
                Toast.makeText(context, "Please enter valid Mobile Number", Toast.LENGTH_SHORT).show()
                false
            }
            !NUMBER_PATTERN.toRegex().matches(Et_phone_passenger.text.toString()) -> {
                Et_phone_passenger.error = "Please enter valid Mobile Number"
                Toast.makeText(context, "Please enter valid Mobile Number", Toast.LENGTH_SHORT)
                    .show()

                false
            }
            Et_phone_passenger.length() !in 6..10 -> {
                Et_phone_passenger.error = "Please enter valid Mobile Number"
                Toast.makeText(context, "Please enter valid Mobile Number", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            else -> {
                true
            }
        }

    }


    fun mUserOTPVerification(
        context: Context,
        firstPinView: EditText
    ): Boolean {
        if (firstPinView.text.toString().isNullOrEmpty()) {
            context.showToastContext("Please enter OTP!!")
            return false
        }
        return true
    }


    fun mUserProfileComplete(
        context: Context,
        Et_passenger_name: EditText,
        Et_passenger_email: EditText,
        Et_phone_driver: EditText,
    ): Boolean {
        if (Et_passenger_name.text.toString().isNullOrEmpty()) {
            context.showToastContext("Please Enter Frist Name !!")
            return false
        }else if (Et_passenger_email.text.toString().isNullOrEmpty()){
            context.showToastContext("Please Enter Email Id !!")
            return false
        }else if (!EMAIL_PATTERN.toRegex().matches(Et_passenger_email.text.toString())){
            context.showToastContext("Please enter a valid email address")
            return false
        }else if (Et_phone_driver.text.toString().isNullOrEmpty()){
            context.showToastContext("Please Enter Mobile Number !!")
        }
        return true
    }


    fun forgotValid(
        context: Context,
        etEmailForget: EditText,
    ): Boolean {
        if (etEmailForget.text.toString().isNullOrEmpty()) {
            context.showToastContext("Please enter registered Email id")
            return false
        } else if (etEmailForget.text.toString().isBlank()) {
            context.showToastContext("Please enter email address")
            return false

        } else if (!EMAIL_PATTERN.toRegex().matches(etEmailForget.text.toString())) {
            context.showToastContext("Please enter a valid email address")
            return false
        } else
            return true

    }


    fun mConfirmAddress(
        context: Context,
        mHomeNumberFullAddress: EditText,
        mBuildingNumber: EditText,
    ): Boolean {
        if (mHomeNumberFullAddress.text.toString().isNullOrEmpty()) {
            context.showToastContext("Block no./ House no./ Flat no.")
            return false
        } else if (mBuildingNumber.text.toString().isNullOrEmpty()) {
            context.showToastContext("Street/Apartment/villa name")
            return false
        } else
            return true

    }

    fun mWalletAmount(
        context: Context,
        mUserNumberAmount: EditText
    ): Boolean {
        if (mUserNumberAmount.text.toString().isNullOrEmpty()) {
            context.showToastContext("Please enter Amount !!")
            return false
        }
        return true
    }

    fun mUserChnagePasswordValidation(
        context: Context,
        mCurrentPassword: EditText,
        mNewPassword: EditText,
        mConfirmNewPassword: EditText
    ): Boolean {
        if (mCurrentPassword.text.toString().isNullOrEmpty()) {
            context.showToastContext("Please enter your current Password !!")
            return false
        } else if (mNewPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter password")
            return false
        } else if (mNewPassword.text.toString().length < 8) {
            //  context.showToastContext("Password should have atleast 8 alphanumeric including special characters")
            ErrorUtil.showErrorMessage(
                "Password must contain at least 8 characters, 1 alphabet, 1 number, 1 uppercase and 1 lowercase ",
                context
            )

            return false
        } else if (!PASSWORD_PATTERN.toRegex().matches(mNewPassword.text.toString())) {
            // context.showToastContext("Password should have atleast 8 alphanumeric including special characters")
            ErrorUtil.showErrorMessage(
                "Password must contain at least 8 characters, 1 alphabet, 1 number, 1 uppercase and 1 lowercase ",
                context
            )

            return false
        } else if (mConfirmNewPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter confirm password")
            return false
        }
        if (mNewPassword.text.toString() != mConfirmNewPassword.text.toString()) {
            context.showToastContext("Passwords mismatched")
            return false
        }
        return true
    }

    fun forgotPassValidation(
        context: Context,
        etMobile: EditText
    ): Boolean {
        if (etMobile.text.toString().isBlank()) {
            context.showToastContext(" Please enter registered mobile number")
            return false
        } else if (etMobile.text.toString().length !in 8..15) {
            context.showToastContext("Please enter a valid mobile number")
            return false
        } else if (!NUMBER_PATTERN.toRegex()
                .matches(etMobile.text.toString()) && !EMAIL_PATTERN.toRegex()
                .matches(etMobile.text.toString())
        ) {
            context.showToastContext("Please enter the valid Field")
            return false

        }

        return true
    }

    fun otpVerifyValidation(
        context: Context,
        mySignUpPinView: EditText
    ): Boolean {
        if (mySignUpPinView.text.toString().isBlank()) {
            context.showToastContext("Please enter OTP")
            return false
        }
        return true

    }

    fun resetPassValidation(
        context: Context, etNewPassword: EditText, etCnfPassword: EditText
    ): Boolean {
        if (etNewPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter password")
            return false
        } else if (etNewPassword.text.toString().length < 8) {
            //  context.showToastContext("Password should have atleast 8 alphanumeric including special characters")
            ErrorUtil.showErrorMessage(
                "Password must contain at least 8 characters, 1 alphabet, 1 number, 1 uppercase and 1 lowercase ",
                context
            )

            return false
        } else if (!PASSWORD_PATTERN.toRegex().matches(etNewPassword.text.toString())) {
            // context.showToastContext("Password should have atleast 8 alphanumeric including special characters")
            ErrorUtil.showErrorMessage(
                "Password must contain at least 8 characters, 1 alphabet, 1 number, 1 uppercase and 1 lowercase ",
                context
            )

            return false
        } else if (etCnfPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter confirm password")
            return false
        }
        if (etNewPassword.text.toString() != etCnfPassword.text.toString()) {
            context.showToastContext("Passwords mismatched")
            return false
        }
        return true
    }

    fun signupValidation(
        context: Context,
        etFullName: EditText,
        etEmail: EditText,
        etMobile: EditText,
        etPassword: EditText,
        etCnfPassword: EditText,
        ch_Terms: CheckBox,


        ): Boolean {
        if (etFullName.text.toString().isBlank()) {
            context.showToastContext("Please enter Full name")
            return false
        } else if (!NAME_PATTERN.toRegex().matches(etFullName.text.toString())) {
            context.showToastContext(" Name should contain alphabets only")
            return false
        } else if (etEmail.text.toString().isBlank()) {
            context.showToastContext("Please enter email address")

            return false
        } else if (!EMAIL_PATTERN.toRegex().matches(etEmail.text.toString())) {
            context.showToastContext("Please enter a valid email address")
            return false

        } else if (etMobile.text.toString().isBlank()) {
            context.showToastContext("Please enter Mobile number")

            return false

        } else if (etMobile.text.toString().length !in 8..15) {
            context.showToastContext("Please enter a valid mobile number")
            return false
        } else if (!NUMBER_PATTERN.toRegex()
                .matches(etMobile.text.toString()) && !EMAIL_PATTERN.toRegex()
                .matches(etMobile.text.toString())
        ) {
            context.showToastContext("Please enter the valid Field")
            return false

        } else if (etPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter password")
            return false
        } else if (etPassword.text.toString().length < 8) {

            ErrorUtil.showErrorMessage(
                "Password must contain at least 8 characters, 1 alphabet, 1 number, 1 uppercase and 1 lowercase ",
                context
            )
            // context.showToastContext("Password should have atleast 8 alphanumeric including special characters")
            return false
        } else if (!PASSWORD_PATTERN.toRegex().matches(etPassword.text.toString())) {
            // context.showToastContext("Password should have atleast 8 alphanumeric including special characters")
            ErrorUtil.showErrorMessage(
                "Password must contain at least 8 characters, 1 alphabet, 1 number, 1 uppercase and 1 lowercase ",
                context
            )

            return false
        } else if (etCnfPassword.text.toString().isBlank()) {
            context.showToastContext("Please enter confirm password")

            return false
        }
        if (etPassword.text.toString() != etCnfPassword.text.toString()) {
            context.showToastContext("Passwords mismatched")
            return false
        } else if (!ch_Terms.isChecked) {
            context.showToastContext("Please accept the term and conditions")
            return false
        }

        return true
    }

    fun bankDetailsValidation(
        context: Context,
        regNum: EditText,
        vehicleMake: EditText,
        vehicleModel: EditText,
        vehicleColor: EditText
    ): Boolean {
        when {
            regNum.text.toString().isBlank() -> {
                context.showToastContext("Please enter vehicle Registration Number")
                return false
            }
            vehicleMake.text.toString().isBlank() -> {
                context.showToastContext("Please enter vehicle Make Details")
                return false
            }
            vehicleModel.text.toString().isBlank() -> {
                context.showToastContext("Please enter vehicle Model Details")
                return false
            }
            vehicleColor.text.toString().isBlank() -> {
                context.showToastContext("Please enter vehicle Color")
                return false
            }
            else -> return true
        }

    }


    fun bankAccountValidation(
        context: Context,
        tv_language_nameP: TextView,

    ): Boolean {
        when {
            tv_language_nameP.text.toString().isBlank() -> {
                context.showToastContext("Please enter account Number")
                return false
            }
            else -> return true
        }
    }


    fun createdocumentdata(
        context: Context,
        etStoreName: EditText,
        etFullStoreAddress: EditText,
        etCountry: EditText,
        etZipPostalCode: EditText
    ): Boolean {
        when {
            etStoreName.text.toString().isBlank() -> {
                context.showToastContext("Please enter Store Name")
                return false
            }
            etFullStoreAddress.text.toString().isBlank() -> {
                context.showToastContext("Please enter Store Address")
                return false
            }
            etCountry.text.toString().isBlank() -> {
                context.showToastContext("Please enter Countryname")
                return false
            }

            etZipPostalCode.text.toString().isBlank() -> {
                context.showToastContext("Please enter ZipCode")
                return false
            }
            else -> return true
        }
    }


}
