package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class GetVehicleTypeListRequest(
    @SerializedName("dropLat")
    var dropLat: Double?, // 12.997884635904084
    @SerializedName("dropLong")
    var dropLong: Double?, // 77.66765544882603
    @SerializedName("pickupLat")
    var pickupLat: Double?, // 12.996047810408397
    @SerializedName("pickupLong")
    var pickupLong: Double?, // 77.66920186121413
    @SerializedName("serviceType")
    var serviceType: Int? // 0
)