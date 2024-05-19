package com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.vo


import com.google.gson.annotations.SerializedName

data class DriverDetailsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response,
    @SerializedName("status")
    val status: Int
) {
    data class Response(
        @SerializedName("result")
        val result: Result
    ) {
        data class Result(
            @SerializedName("access_token")
            val accessToken: String,
            @SerializedName("city")
            val city: Any,
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
            @SerializedName("driving_license")
            val drivingLicense: DrivingLicense,
            @SerializedName("email")
            val email: Any,
            @SerializedName("full_name")
            val fullName: Any,
            @SerializedName("gender")
            val gender: Int,
            @SerializedName("_id")
            val id: String,
            @SerializedName("identity_card")
            val identityCard: ArrayList<String> = arrayListOf(),
            @SerializedName("is_blocked")
            val isBlocked: Boolean,
            @SerializedName("is_email_verified")
            val isEmailVerified: Int,
            @SerializedName("is_mobile_verified")
            val isMobileVerified: Int,
            @SerializedName("is_profile_complete")
            val isProfileComplete: Int,
            @SerializedName("last_login")
            val lastLogin: Long,
            @SerializedName("latitude")
            val latitude: String,
            @SerializedName("location")
            val location: Location,
            @SerializedName("longitude")
            val longitude: String,
            @SerializedName("otp")
            val otp: String,
            @SerializedName("otpValidityTill")
            val otpValidityTill: Long,
            @SerializedName("permit_smoking")
            val permitSmoking: Boolean,
            @SerializedName("pets_accepted")
            val petsAccepted: Boolean,
            @SerializedName("pets_size")
            val petsSize: ArrayList<Int> = arrayListOf(),
            @SerializedName("phone_number")
            val phoneNumber: String,
            @SerializedName("profile_picture")
            val profilePicture: Any,
            @SerializedName("service_offerings")
            val serviceOfferings: ArrayList<Int> = arrayListOf(),
            @SerializedName("smoker")
            val smoker: Boolean,
            @SerializedName("status")
            val status: Int,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("wallet_id")
            val walletId: String
        ) {
            data class DrivingLicense(
                @SerializedName("backImg")
                val backImg: String,
                @SerializedName("frontImg")
                val frontImg: String
            )

            data class Location(
                @SerializedName("coordinates")
                val coordinates: ArrayList<String> = arrayListOf(),
                @SerializedName("type")
                val type: String
            )
        }
    }
}