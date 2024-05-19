package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class CreateFareConfirmResponse(
    @SerializedName("message")
    var message: String?, // Pls wait while we are searching driver
    @SerializedName("response")
    var response: Response?,
    @SerializedName("status")
    var status: Int? // 1
) {
    data class Response(
        @SerializedName("result")
        var result: Result?
    ) {
        data class Result(
            @SerializedName("active_till")
            var activeTill: Long?, // 1689059820624
            @SerializedName("actual_amount")
            var actualAmount: Int?, // 200
            @SerializedName("amount")
            var amount: Int?, // 150
            @SerializedName("created_at")
            var createdAt: Long?, // 1689018582198
           // @SerializedName("createdAt")
            //var createdAt: String?, // 2023-07-11T07:13:00.626Z
            @SerializedName("currency")
            var currency: String?, // euro
            @SerializedName("dropLatitude")
            var dropLatitude: Double?, // 28.61457286479067
            @SerializedName("dropLongitude")
            var dropLongitude: Double?, // 77.38681487739086
            @SerializedName("_id")
            var id: String?, // 64ad00fc760e4d663eee9558
            @SerializedName("is_amount_negotiated")
            var isAmountNegotiated: Boolean?, // true
            @SerializedName("otp")
            var otp: String?, // 1234
            @SerializedName("payment_method")
            var paymentMethod: Int?, // 0
            @SerializedName("pickupLatitude")
            var pickupLatitude: Double?, // 28.614573
            @SerializedName("pickupLongitude")
            var pickupLongitude: Double?, // 77.3868149
            @SerializedName("status")
            var status: Int?, // 0
            @SerializedName("stopLatitude")
            var stopLatitude: Double?, // 13.037503
            @SerializedName("stopLongitude")
            var stopLongitude: Double?, // 77.689817
            @SerializedName("taxi_for")
            var taxiFor: Int?, // 0
            @SerializedName("updatedAt")
            var updatedAt: String?, // 2023-07-11T07:13:00.626Z
            @SerializedName("user_id")
            var userId: String?, // 64a01a08dcfe75c64044f0e9
            @SerializedName("vehicleType")
            var vehicleType: String? // 64aa6cced2dc0af10d558fa2
        )
    }
}