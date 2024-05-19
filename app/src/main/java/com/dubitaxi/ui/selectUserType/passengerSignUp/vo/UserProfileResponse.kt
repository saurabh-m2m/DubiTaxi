package com.dubitaxi.ui.selectUserType.passengerSignUp.vo


import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("country")
        val country: Any,
        @SerializedName("country_code")
        val countryCode: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("device_token")
        val deviceToken: String,
        @SerializedName("device_type")
        val deviceType: Int,
        @SerializedName("email")
        val email: Any,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("_id")
        val id: String,
        @SerializedName("is_blocked")
        val isBlocked: Boolean,
        @SerializedName("is_mobile_verified")
        val isMobileVerified: Int,
        @SerializedName("is_profile_complete")
        val isProfileComplete: Int,
        @SerializedName("last_login")
        val lastLogin: Long,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("location")
        val location: Location,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("otp")
        val otp: String,
        @SerializedName("password")
        val password: Any,
        @SerializedName("phone_number")
        val phoneNumber: String,
        @SerializedName("profile_picture")
        val profilePicture: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("wallet_id")
        val walletId: String
    ) {
        data class Location(
            @SerializedName("coordinates")
            val coordinates: List<Double>
        )
    }
}