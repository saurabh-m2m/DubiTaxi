package com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.request

class DriverPersonalDetailsRequest(
    val name:String,
    val country_code:String,
    val phone_number:String,
    val email:String,
    val verification_document_type:String,
    val verification_document_num:String,
    val idCardImages:ArrayList<String>,
    val gender:String,
    val smoker:String,
    val city:String,
    val lat:String,
    val long:String,
    val referralCode:String,
    val profile_picture:String,
    val deviceToken:String,
    val device_type:String,
)