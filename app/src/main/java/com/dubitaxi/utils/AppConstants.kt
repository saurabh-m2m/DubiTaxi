package com.dubitaxi.utils

import android.Manifest

interface AppConstants {

    companion object {
        val PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)

        const val MAX_OPPONENTS_COUNT = 6

        const val EXTRA_LOGIN_RESULT = "login_result"

        const val EXTRA_LOGIN_ERROR_MESSAGE = "login_error_message"

        const val EXTRA_LOGIN_RESULT_CODE = 1002

        const val EXTRA_IS_INCOMING_CALL = "conversation_reason"

        const val MAX_LOGIN_LENGTH = 15

        const val MAX_FULLNAME_LENGTH = 20

        /*
            Base URL
        */
        const val BASE_URL_FOR_DEVELOPMENT = "http://15.229.76.241:3000/api/"
        //production

        //val BASE_URL_FOR_DEVELOPMENT = "https://restaurants.theplace.com.ng/"


        // Formats
        const val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        const val NUMBER_PATTERN = "^[0-9]*$"
        const val NAME_PATTERN = "^[A-Za-z ]*$"

        /*Delivery Constants*/
        const val FROM_TO = 5
        const val ON_GOING = 0
        const val COMPLETED = 1
        const val TODAY_DEL = 2
        const val WEEKLY_DEL = 3
        const val MONTHLY_DEL = 4

        /*Order Constants*/
        const val ONGOING = 0
        const val PAST = 3
        const val NEW = -1
        const val DISPATCHED = 4
        const val ACCEPT = 0
        const val REJECT = 2
        const val DAILY = 1
        const val WEEKLY = 2
        const val MONTHLY = 4

        /*PassenderApi*/

        // 0 - Order Placed
        const val USER_CREATE_ACCOUNT = "user/login"
        const val USER_OTP_VERIFICATION = "user/verify_otp"
        const val USER_RESEND_OTP = "user/resend_otp"
        const val USER_PROFILE = "user/profile_create"
        const val GET_BANNERS = "user/get_banners"
        const val SAVED_ADDRESS = "user/save_address"
        const val UPDATE_ADDRESS = "user/update_address"
        const val GET_SAVED_ADDRESS = "user/all_saved_address"
        const val USER_REMOVE_ADDRESS = "user/remove_address"
        const val USER_RECENTLY_ADDRESS = "user/recently_used_address"
        const val USER_MARK_UNMARK_ADDRESS = "user/mark_unmark_address_as_fav"


        //M3------------------------------------------------
        const val GetVehicleTypeList = "user/get_vehicle_type_list"
        const val CreateFareConfirm = "user/create_fare"
        const val GetFareDetails = "user/get_fare_details"
        const val CancelFare = "user/cancel_fare"
        const val mLogout = "user/logout"
        /*Driver API*/

        const val DRIVER_CREATE_ACCOUNT = "driver/send_otp_on_phone"
        const val DRIVER_OTP_VERIFICATION = "driver/verify_otp"
        const val DRIVER_UPLOAD_IMAGE = "driver/upload_file_wot"
        const val DRIVER_PERSONAL_DETAILS = "driver/addDriverPersonalDetails"
        const val DRIVER_DETAILS = "driver/add_driver_details"
        const val DRIVER_CITY = "driver/citiesList"
        const val DRIVER_GET_ALL_VEHICLES = "driver/get_all_vehicles"
        const val ADD_VEHICLE = "driver/add_vehicle"
        const val ADD_DRIVER_BANK_DETAILS = "driver/add_bank_account"
        const val GET_VEHICLE_TYPE = "driver/get_vehicletypes"
        const val EDIT_VEHICLE = "driver/update_vehicle"
        const val DRIVER_RESEND_OTP = "driver/resend_otp"
        const val DRIVER_GET_PARTICULAR_VEHICLE_DETAILS = "driver/get_vehicle_details"


        //M2------------------------------------------------------------
        const val DRIVER_TOGGLE_ACTIVE = "driver/toggle_activity"
        const val DRIVER_GET_SUBSCRIPTION_PLANS = "driver/get_subscription_plans"
        const val DRIVER_PURCHASE_SUBSCRIPTION_PLAN = "driver/purchase_subscription"
        const val DRIVER_GET_UP_COMING_TAXI_FARE = "driver/get_upcoming_taxi_fare"
        const val DRIVER_ACCEPT_TAXI_FARE = "driver/accept_taxi_fare"
        const val DRIVER_REJECT_TAXI_FARE = "driver/reject_taxi_fare"
        const val DRIVER_CANCEL_TAXI_RIDE = "driver/cancel_taxi_ride"

        ///driver/cancel_taxi_ride
        const val DRIVER_ON_THE_WAY_TO_STORE = "5" //Receive User and Store Side
        const val DRIVER_ARRIVED_TO_STORE = "6" //Receive User and Store Side
        const val DRIVER_PICKUP_ORDER = "9"  //Receive User and Store Side
        const val CANCEL_BY_USER = "16"  //Receive Driver and Store Side
        const val CANCEL_BY_DRIVER = "17" //Receive User and Store Side


        const val DEVICE_TYPE = 1

        // Request Codes
        const val GALLERY = 1111
        const val CAMERA = 2222
        const val VIDEO = 3333
        const val TASK_AWAIT = 120L

        // Media Type
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2
        const val IMAGE_MAX_SIZE = 1024
        const val EDIT_FAVOUR_REQUEST = 111
        const val DEFAULT_COUNTRY_CODE = "+91"
        const val PREFRENCE_NAME = "MyPrefs"

        const val TITLE_ENGLISH = "titleEnglish"
        const val TITLE_ARAB = "titleArab"
        const val RESTAURANT_NAME = "restaurantName"
        const val ITEM = "item"
        const val ID = "id"

        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val ADDRESS = "address"

    }
}