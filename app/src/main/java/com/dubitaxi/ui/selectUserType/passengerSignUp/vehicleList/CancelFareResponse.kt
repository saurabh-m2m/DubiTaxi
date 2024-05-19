package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class CancelFareResponse(
    @SerializedName("message")
    var message: String?, // Successfully cancelled ride
    @SerializedName("response")
    var response: Response?,
    @SerializedName("status")
    var status: Int? // 1
) {
    data class Response(
        @SerializedName("fareDetails")
        var fareDetails: FareDetails?
    ) {
        data class FareDetails(
            @SerializedName("active_till")
            var activeTill: Long?, // 1689077549542
            @SerializedName("actual_amount")
            var actualAmount: Int?, // 0
            @SerializedName("amount")
            var amount: Int?, // 0
            @SerializedName("created_at")
            var createdAt: Long?, // 1689070841623
            // @SerializedName("createdAt")
            //   var createdAt: String?, // 2023-07-11T12:08:29.544Z
            @SerializedName("currency")
            var currency: String?, // peru
            @SerializedName("dropLatitude")
            var dropLatitude: Double?, // 28.614554616583213
            @SerializedName("dropLongitude")
            var dropLongitude: Double?, // 77.38677933812141
            @SerializedName("_id")
            var id: String?, // 64ad463db221dffa576f913e
            @SerializedName("is_amount_negotiated")
            var isAmountNegotiated: Boolean?, // false
            @SerializedName("otp")
            var otp: String?, // 1234
            @SerializedName("payment_method")
            var paymentMethod: Int?, // 0
            @SerializedName("pickupLatitude")
            var pickupLatitude: Double?, // 28.6145547
            @SerializedName("pickupLongitude")
            var pickupLongitude: Double?, // 77.3867795
            @SerializedName("scheduled_date")
            var scheduledDate: String?,
            @SerializedName("scheduled_time")
            var scheduledTime: String?,
            @SerializedName("status")
            var status: Int?, // -2
            @SerializedName("stopLatitude")
            var stopLatitude: Int?, // 0
            @SerializedName("stopLongitude")
            var stopLongitude: Int?, // 0
            @SerializedName("taxi_for")
            var taxiFor: Int?, // 0
            @SerializedName("updatedAt")
            var updatedAt: String?, // 2023-07-11T12:09:33.100Z
            @SerializedName("user_id")
            var userId: String?, // 64a01a08dcfe75c64044f0e9
            @SerializedName("vehicleType")
            var vehicleType: String? // 64aa6cced2dc0af10d558fa2
        )
    }
}