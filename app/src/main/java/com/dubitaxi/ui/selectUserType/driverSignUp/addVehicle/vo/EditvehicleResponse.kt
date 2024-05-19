package com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo


import com.google.gson.annotations.SerializedName

data class EditvehicleResponse(
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
            @SerializedName("accepted_terms")
            val acceptedTerms: Boolean,
            @SerializedName("color")
            val color: String,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("driver_id")
            val driverId: String,
            @SerializedName("_id")
            val id: String,
            @SerializedName("images")
            val images: ArrayList<String> = arrayListOf(),
            @SerializedName("inspection_sheet_date")
            val inspectionSheetDate: String,
            @SerializedName("inspection_sheet_link")
            val inspectionSheetLink: String,
            @SerializedName("is_blocked")
            val isBlocked: Boolean,
            @SerializedName("model_name")
            val modelName: String,
            @SerializedName("permission_link")
            val permissionLink: String,
            @SerializedName("permissions")
            val permissions: Int,
            @SerializedName("permissions_date")
            val permissionsDate: String,
            @SerializedName("relation_with_vehicle")
            val relationWithVehicle: Int,
            @SerializedName("relation_with_vehicle_desc")
            val relationWithVehicleDesc: String,
            @SerializedName("seats_available")
            val seatsAvailable: Int,
            @SerializedName("soat_date")
            val soatDate: String,
            @SerializedName("soat_link")
            val soatLink: String,
            @SerializedName("soat_number")
            val soatNumber: String,
            @SerializedName("status")
            val status: Int,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("vehicle_number")
            val vehicleNumber: String,
            @SerializedName("vehicle_reg_cert_date")
            val vehicleRegCertDate: String,
            @SerializedName("vehicle_reg_cert_link")
            val vehicleRegCertLink: String,
            @SerializedName("vehicle_type")
            val vehicleType: String
        )
    }
}