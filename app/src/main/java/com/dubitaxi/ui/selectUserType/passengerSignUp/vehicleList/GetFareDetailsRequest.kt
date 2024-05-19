package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class GetFareDetailsRequest(
    @SerializedName("fareId")
    var fareId: String? // 64a7ef6510aa47ede82faa54
)