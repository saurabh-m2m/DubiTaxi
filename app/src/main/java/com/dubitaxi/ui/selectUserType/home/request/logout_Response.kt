package com.dubitaxi.ui.selectUserType.home.request


import com.google.gson.annotations.SerializedName

data class logout_Response(
    @SerializedName("message")
    var message: String?, // Successfully logged out
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
            @SerializedName("access_token")
            var accessToken: String?,
            @SerializedName("country")
            var country: String?, // India
            @SerializedName("country_code")
            var countryCode: String?, // +51
            @SerializedName("createdAt")
            var createdAt: String?, // 2023-07-11T13:19:20.278Z
            @SerializedName("device_token")
            var deviceToken: String?, // ABC
            @SerializedName("device_type")
            var deviceType: Int?, // 1
            @SerializedName("email")
            var email: String?, // mailto:amankumar@123gmail.com
            @SerializedName("full_name")
            var fullName: String?, // Aman Singh 
            @SerializedName("gender")
            var gender: Int?, // 0
            @SerializedName("_id")
            var id: String?, // 64ad56d87a80d35539a64d07
            @SerializedName("is_blocked")
            var isBlocked: Boolean?, // false
            @SerializedName("is_mobile_verified")
            var isMobileVerified: Int?, // 1
            @SerializedName("is_profile_complete")
            var isProfileComplete: Int?, // 1
            @SerializedName("last_login")
            var lastLogin: Long?, // 1689145930533
            @SerializedName("latitude")
            var latitude: Double?, // 28.6145841
            @SerializedName("location")
            var location: Location?,
            @SerializedName("longitude")
            var longitude: Double?, // 77.3869306
            @SerializedName("otp")
            var otp: String?, // 1234
            @SerializedName("password")
            var password: Any?, // null
            @SerializedName("phone_number")
            var phoneNumber: String?, // 9675418720
            @SerializedName("profile_picture")
            var profilePicture: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1689081606257/IMG_1689081602032.jpg
            @SerializedName("status")
            var status: Int?, // 1
            @SerializedName("updatedAt")
            var updatedAt: String?, // 2023-07-12T07:12:33.082Z
            @SerializedName("wallet_id")
            var walletId: String? // 64ad57467a80d35539a64d2c
        ) {
            data class Location(
                @SerializedName("coordinates")
                var coordinates: List<Double?>?,
                @SerializedName("type")
                var type: String? // Point
            )
        }
    }
}