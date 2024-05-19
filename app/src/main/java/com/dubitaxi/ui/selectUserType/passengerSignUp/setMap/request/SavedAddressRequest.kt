package com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.request

class SavedAddressRequest (
    val address_name:String,
    val latitude:Double,
    val longitude:Double,
    var isFavourite:Boolean,
    val address_type:String,
    val official_name:String, )