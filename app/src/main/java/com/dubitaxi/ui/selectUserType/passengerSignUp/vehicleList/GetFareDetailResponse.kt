package com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList


import com.google.gson.annotations.SerializedName

data class GetFareDetailResponse(
    @SerializedName("message")
    var message: String?, // Successfully fetched fare details
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
            var activeTill: Long?, // 1688991295653
            @SerializedName("actual_amount")
            var actualAmount: Int?, // 200
            @SerializedName("amount")
            var amount: Int?, // 150
            @SerializedName("created_at")
            var createdAt: Long?, // 1688990440871
            // @SerializedName("createdAt")
            // var createdAt: String?, // 2023-07-10T12:10:55.680Z
            @SerializedName("currency")
            var currency: String?, // euro
            @SerializedName("driver_id")
            var driverId: DriverId?,
            @SerializedName("dropLatitude")
            var dropLatitude: Double?, // 13.09792
            @SerializedName("dropLongitude")
            var dropLongitude: Double?, // 77.621631
            @SerializedName("_id")
            var id: String?, // 64abf54fc5d44e1ff0a00823
            @SerializedName("is_amount_negotiated")
            var isAmountNegotiated: Boolean?, // true
            @SerializedName("otp")
            var otp: String?, // 1234
            @SerializedName("payment_method")
            var paymentMethod: Int?, // 0
            @SerializedName("pickupLatitude")
            var pickupLatitude: Double?, // 13.037303
            @SerializedName("pickupLongitude")
            var pickupLongitude: Double?, // 77.689817
            @SerializedName("should_retry")
            var shouldRetry: Boolean?, // false
            @SerializedName("status")
            var status: Int?, // 1
            @SerializedName("status_text")
            var statusText: String?, // DRIVER_ON_WAY
            @SerializedName("stopLatitude")
            var stopLatitude: Double?, // 13.037503
            @SerializedName("stopLongitude")
            var stopLongitude: Double?, // 77.689817
            @SerializedName("taxi_for")
            var taxiFor: Int?, // 0
            @SerializedName("updatedAt")
            var updatedAt: String?, // 2023-07-10T12:11:37.315Z
            @SerializedName("user_id")
            var userId: UserId?,
            @SerializedName("vehicleId")
            var vehicleId: VehicleId?,
            @SerializedName("vehicleType")
            var vehicleType: VehicleType?
        ) {
            data class DriverId(
                @SerializedName("city")
                var city: String?, // Hyderabad
                @SerializedName("country")
                var country: String?, // India
                @SerializedName("country_code")
                var countryCode: String?, // +91
                @SerializedName("device_type")
                var deviceType: Int?, // 1
                @SerializedName("full_name")
                var fullName: String?, // geo driver latest 1
                @SerializedName("gender")
                var gender: Int?, // 1
                @SerializedName("_id")
                var id: String?, // 64a7ae36a2f8d32b6f28f34d
                @SerializedName("is_blocked")
                var isBlocked: Boolean?, // false
                @SerializedName("is_email_verified")
                var isEmailVerified: Int?, // 0
                @SerializedName("is_mobile_verified")
                var isMobileVerified: Int?, // 0
                @SerializedName("is_profile_complete")
                var isProfileComplete: Int?, // 1
                @SerializedName("last_login")
                var lastLogin: Long?, // 1688710107412
                @SerializedName("latitude")
                var latitude: Double?, // 13.021535
                @SerializedName("location")
                var location: Location?,
                @SerializedName("longitude")
                var longitude: Double?, // 77.665194
                @SerializedName("otp")
                var otp: String?, // 1234
                @SerializedName("otpValidityTill")
                var otpValidityTill: Long?, // 1688711610654
                @SerializedName("permit_smoking")
                var permitSmoking: Boolean?, // true
                @SerializedName("pets_accepted")
                var petsAccepted: Boolean?, // true
                @SerializedName("pets_size")
                var petsSize: List<Int?>?,
                @SerializedName("phone_number")
                var phoneNumber: String?, // 9027902790
                @SerializedName("profile_picture")
                var profilePicture: String?, // https://www.gettyimages.in/gi-resources/images/Embed/new/embed2.jpg
                @SerializedName("service_offerings")
                var serviceOfferings: ArrayList<Int?>?,
                @SerializedName("smoker")
                var smoker: Boolean?, // true
                @SerializedName("status")
                var status: Int?, // 2
                @SerializedName("subscription_for_service")
                var subscriptionForService: Int? // 0
            ) {
                data class Location(
                    @SerializedName("coordinates")
                    var coordinates: ArrayList<Double?>?,
                    @SerializedName("type")
                    var type: String? // Point
                )
            }

            data class UserId(
                @SerializedName("country")
                var country: String?, // undefined
                @SerializedName("device_token")
                var deviceToken: String?, // abcd
                @SerializedName("device_type")
                var deviceType: Int?, // 1
                @SerializedName("full_name")
                var fullName: String?, // geo account user
                @SerializedName("gender")
                var gender: Int?, // 1
                @SerializedName("_id")
                var id: String?, // 649e6e3f3761feb91d0e7b10
                @SerializedName("is_blocked")
                var isBlocked: Boolean?, // false
                @SerializedName("is_mobile_verified")
                var isMobileVerified: Int?, // 1
                @SerializedName("latitude")
                var latitude: Double?, // 13.021995
                @SerializedName("location")
                var location: Location?,
                @SerializedName("longitude")
                var longitude: Double?, // 77.663939
                @SerializedName("password")
                var password: Any?, // null
                @SerializedName("profile_picture")
                var profilePicture: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1686922829289/w3logo.jpg
                @SerializedName("status")
                var status: Int? // 1
            ) {
                data class Location(
                    @SerializedName("coordinates")
                    var coordinates: List<Double?>?,
                    @SerializedName("type")
                    var type: String? // Point
                )
            }
            data class VehicleId(
                @SerializedName("accepted_terms")
                var acceptedTerms: Boolean?, // true
                @SerializedName("color")
                var color: String?, // red
                @SerializedName("createdAt")
                var createdAt: String?, // 2023-07-07T06:23:49.090Z
                @SerializedName("driver_id")
                var driverId: String?, // 64a7ae36a2f8d32b6f28f34d
                @SerializedName("_id")
                var id: String?, // 64a7af75a2f8d32b6f28f362
                @SerializedName("images")
                var images: ArrayList<String?>?,
                @SerializedName("inspection_sheet_date")
                var inspectionSheetDate: String?, // 02-03-2023
                @SerializedName("inspection_sheet_link")
                var inspectionSheetLink: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1686922829289/w3logo.jpg
                @SerializedName("is_blocked")
                var isBlocked: Boolean?, // false
                @SerializedName("model_name")
                var modelName: String?, // Hyundai XYZ erde
                @SerializedName("permission_link")
                var permissionLink: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1686922829289/w3logo.jpg
                @SerializedName("permissions")
                var permissions: Int?, // 1
                @SerializedName("permissions_date")
                var permissionsDate: String?, // 02-03-2023
                @SerializedName("relation_with_vehicle")
                var relationWithVehicle: Int?, // 1
                @SerializedName("relation_with_vehicle_desc")
                var relationWithVehicleDesc: String?,
                @SerializedName("seats_available")
                var seatsAvailable: Int?, // 5
                @SerializedName("soat_date")
                var soatDate: String?, // 02-03-2023
                @SerializedName("soat_link")
                var soatLink: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1686922829289/w3logo.jpg
                @SerializedName("soat_number")
                var soatNumber: String?, // 172345678
                @SerializedName("status")
                var status: Int?, // 1
                @SerializedName("updatedAt")
                var updatedAt: String?, // 2023-07-07T06:23:49.090Z
                @SerializedName("vehicle_number")
                var vehicleNumber: String?, // LT1744
                @SerializedName("vehicle_reg_cert_date")
                var vehicleRegCertDate: String?, // 02-03-2023
                @SerializedName("vehicle_reg_cert_link")
                var vehicleRegCertLink: String?, // https://agambabucket.s3.ap-south-1.amazonaws.com/Uploads/1686922829289/w3logo.jpg
                @SerializedName("vehicle_type")
                var vehicleType: String? // 6496f58d8a1ba7cb98da41de
            )
            data class VehicleType(
                @SerializedName("createdAt")
                var createdAt: String?, // 2023-06-24T13:54:21.657Z
                @SerializedName("description")
                var description: String?, // SUV Car
                @SerializedName("icon")
                var icon: String?, // https://sowartikbucket.s3.me-central-1.amazonaws.com/1687613753293/finished.png
                @SerializedName("_id")
                var id: String?, // 6496f58d8a1ba7cb98da41de
                @SerializedName("is_blocked")
                var isBlocked: Boolean?, // false
                @SerializedName("service_type")
                var serviceType: Int?, // 0
                @SerializedName("status")
                var status: Int?, // 1
                @SerializedName("subVehicleTypeForTaxi")
                var subVehicleTypeForTaxi: String?, // ECONOMICAL
                @SerializedName("updatedAt")
                var updatedAt: String?, // 2023-07-08T05:42:14.050Z
                @SerializedName("vehicle_type")
                var vehicleType: String? // SUVCAR
            )
        }
    }
}