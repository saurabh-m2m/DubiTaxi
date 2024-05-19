package com.dubitaxi.ui.selectUserType.otpVerifiction.vo


import com.google.gson.annotations.SerializedName

data class DriverResendOTPResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("otp")
        val otp: String
    )
}