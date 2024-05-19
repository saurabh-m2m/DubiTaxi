package com.dubitaxi.ui.selectUserType.home.vo


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class DriverToggleActiveResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("status")
    val status: Int?
) : Parcelable {
    @Parcelize
    data class Response(
        @SerializedName("result")
        val result: Result?
    ) : Parcelable {
        @Parcelize
        data class Result(
            @SerializedName("access_token")
            val accessToken: String?,
            @SerializedName("city")
            val city: String?,
            @SerializedName("country")
            val country: String?,
            @SerializedName("country_code")
            val countryCode: String?,
            @SerializedName("createdAt")
            val createdAt: String?,
            @SerializedName("device_token")
            val deviceToken: String?,
            @SerializedName("device_type")
            val deviceType: Int?,
            @SerializedName("driving_license")
            val drivingLicense: DrivingLicense?,
            @SerializedName("email")
            val email: String?,
            @SerializedName("full_name")
            val fullName: String?,
            @SerializedName("gender")
            val gender: Int?,
            @SerializedName("_id")
            val id: String?,
            @SerializedName("identity_card")
            val identityCard: ArrayList<String?>? = arrayListOf(),
            @SerializedName("is_blocked")
            val isBlocked: Boolean?,
            @SerializedName("is_email_verified")
            val isEmailVerified: Int?,
            @SerializedName("is_mobile_verified")
            val isMobileVerified: Int?,
            @SerializedName("is_plan_used_completely")
            val isPlanUsedCompletely: Boolean?,
            @SerializedName("is_profile_complete")
            val isProfileComplete: Int?,
            @SerializedName("last_login")
            val lastLogin: Long?,
            @SerializedName("latitude")
            val latitude: Double?,
            @SerializedName("location")
            val location: Location?,
            @SerializedName("longitude")
            val longitude: Double?,
            @SerializedName("otp")
            val otp: String?,
            @SerializedName("otpValidityTill")
            val otpValidityTill: Long?,
            @SerializedName("permit_smoking")
            val permitSmoking: Boolean?,
            @SerializedName("pets_accepted")
            val petsAccepted: Boolean?,
            @SerializedName("pets_size")
            val petsSize: List<Int?>?,
            @SerializedName("phone_number")
            val phoneNumber: String?,
            @SerializedName("profile_picture")
            val profilePicture: String?,
            @SerializedName("referred_by")
            val referredBy: String?,
            @SerializedName("service_offerings")
            val serviceOfferings: List<Int?>?,
            @SerializedName("smoker")
            val smoker: Boolean?,
            @SerializedName("status")
            val status: Int?,
            @SerializedName("subscription_for_service")
            val subscriptionForService: Int?,
            @SerializedName("subscription_plan")
            val subscriptionPlan: String?,
            @SerializedName("toggle")
            val toggle: Boolean?,
            @SerializedName("updatedAt")
            val updatedAt: String?,
            @SerializedName("verification_document")
            val verificationDocument: VerificationDocument?,
            @SerializedName("wallet_id")
            val walletId: String?
        ) : Parcelable {
            @Parcelize
            data class DrivingLicense(
                @SerializedName("backImg")
                val backImg: String?,
                @SerializedName("frontImg")
                val frontImg: String?
            ) : Parcelable

            @Parcelize
            data class Location(
                @SerializedName("coordinates")
                val coordinates: List<Double?>?,
                @SerializedName("type")
                val type: String?
            ) : Parcelable

            @Parcelize
            data class VerificationDocument(
                @SerializedName("docType")
                val docType: String?,
                @SerializedName("num")
                val num: String?
            ) : Parcelable
        }
    }
}