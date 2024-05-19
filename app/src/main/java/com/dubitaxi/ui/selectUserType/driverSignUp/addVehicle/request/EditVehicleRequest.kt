package com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request
import com.google.gson.annotations.SerializedName


data class EditVehicleRequest(
    @SerializedName("accepted_terms")
    val acceptedTerms: Boolean,
    @SerializedName("color")
    val color: String,
    @SerializedName("images")
    val images: ArrayList<String> = arrayListOf(),
    @SerializedName("inspection_sheet_date")
    val inspectionSheetDate: String,
    @SerializedName("inspection_sheet_link")
    val inspectionSheetLink: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("permissions")
    val permissions: Int,
    @SerializedName("permissions_date")
    val permissionsDate: String,
    @SerializedName("permissions_doc")
    val permissionsDoc: String,
    @SerializedName("registration_certificate")
    val registrationCertificate: String,
    @SerializedName("registration_certificate_date")
    val registrationCertificateDate: String,
    @SerializedName("seats_available")
    val seatsAvailable: String,
    @SerializedName("soat")
    val soat: String,
    @SerializedName("soat_date")
    val soatDate: String,
    @SerializedName("soat_number")
    val soatNumber: String,
    @SerializedName("vehicleId")
    val vehicleId: String,
    @SerializedName("vehicle_number")
    val vehicleNumber: String,
    @SerializedName("vehicle_relation")
    val vehicleRelation: Int,
    @SerializedName("vehicle_relation_desc")
    val vehicleRelationDesc: String,
    @SerializedName("vehicle_type")
    val vehicleType: String
)