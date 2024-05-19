package com.dubitaxi.ui.selectUserType.otpVerifiction.vo

data class Response(
    val _id: String,
    val access_token: String,
    val country_code: String,
    val createdAt: String,
    val device_token: String,
    val device_type: Int,
    val email: Any,
    val full_name: Any,
    val gender: Int,
    val is_blocked: Boolean,
    val is_mobile_verified: Int,
    val is_profile_complete: Int,
    val last_login: Long,
    val latitude: Double,
    val location: Location,
    val longitude: Double,
    val otp: String,
    val password: Any,
    val phone_number: String,
    val profile_picture: Any,
    val updatedAt: String
)