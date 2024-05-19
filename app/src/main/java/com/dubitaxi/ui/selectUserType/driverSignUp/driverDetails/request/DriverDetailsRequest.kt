package com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.request

class DriverDetailsRequest(
    var DLFrontImg: String,
    var DLBackImg: String,
    var service_offerings: ArrayList<Int>,
    var pets_accepted: Boolean,
    var permit_smoking: Boolean,
    var pets_size: ArrayList<Int>
)
