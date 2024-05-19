package com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.vo

import com.google.gson.annotations.SerializedName

data class UserRemoveAddressResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response,
    @SerializedName("status")
    val status: Int
) {
    class Response
}