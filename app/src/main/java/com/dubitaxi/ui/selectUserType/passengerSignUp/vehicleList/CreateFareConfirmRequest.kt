package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class CreateFareConfirmRequest(
    @SerializedName("actual_amount")
    var actualAmount: Int?, // 200
    @SerializedName("amount")
    var amount: Int?, // 150
    @SerializedName("currency")
    var currency: String?, // euro
    @SerializedName("dropLatitude")
    var dropLatitude: Double?, // 13.097920
    @SerializedName("dropLongitude")
    var dropLongitude: Double?, // 77.621631
    @SerializedName("payment_method")
    var paymentMethod: Int?, // 0
    @SerializedName("pickupLatitude")
    var pickupLatitude: Double?, // 13.037303
    @SerializedName("pickupLongitude")
    var pickupLongitude: Double?, // 77.689817
    @SerializedName("scheduled_date")
    var scheduledDate: String?, // 2023/12/12
    @SerializedName("scheduled_time")
    var scheduledTime: String?, // 21:45
    @SerializedName("stopLatitude")
    var stopLatitude: Double?, // 13.037503
    @SerializedName("stopLongitude")
    var stopLongitude: Double?, // 77.689817
    @SerializedName("taxi_for")
    var taxiFor: Int?, // 0
    @SerializedName("pet_size")
    var petSize: Int?, // 0
    @SerializedName("vehicleType")
    var vehicleType: String? // 6496f58d8a1ba7cb98da41de
)