package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class GetVehicleTypeDataResponse(
    @SerializedName("message")
    var message: String?, // Successfully Fetched vehicle types
    @SerializedName("response")
    var response: Response?,
    @SerializedName("status")
    var status: Int? // 1
) {
    data class Response(
        @SerializedName("result")
        var result: ArrayList<Result>?
    ) {
        data class Result(
            @SerializedName("createdAt")
            var createdAt: String?, // 2023-07-09T08:16:14.401Z
            @SerializedName("description")
            var description: String?, // ghkjgf gjfgf 
            @SerializedName("fareDetails")
            var fareDetails: FareDetails?,
            @SerializedName("icon")
            var icon: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1688890563285/d_banner.jpg
            @SerializedName("_id")
            var id: String?, // 64aa6cced2dc0af10d558fa2
            @SerializedName("is_blocked")
            var isBlocked: Boolean?, // false
            @SerializedName("nearbyDriver")
            var nearbyDriver: NearbyDriver?,
            @SerializedName("service_type")
            var serviceType: Int?, // 0
            @SerializedName("status")
            var status: Int?, // 1
            @SerializedName("subVehicleTypeForTaxi")
            var subVehicleTypeForTaxi: String?, // ECONOMICAL
            @SerializedName("updatedAt")
            var updatedAt: String?, // 2023-07-09T08:16:19.355Z
            @SerializedName("vehicle_type")
            var vehicleType: String? // Economical new
        ) {
            data class FareDetails(
                @SerializedName("amount")
                var amount: Int?, // 272
                @SerializedName("currency")
                var currency: String? // USD
            )

            data class NearbyDriver(
                @SerializedName("distance")
                var distance: Int?, // 0
                @SerializedName("time")
                var time: String? // 0
            )
        }
    }
}