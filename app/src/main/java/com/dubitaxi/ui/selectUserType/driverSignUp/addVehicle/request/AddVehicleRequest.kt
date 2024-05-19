package com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request

data class AddVehicleRequest(
    val accepted_terms: Boolean,
    val color: String,
    val images: ArrayList<String> = arrayListOf(),
    val inspection_sheet_date: String,
    val inspection_sheet_link: String,
    val model_name: String,
    val permissions: Int,
    val permissions_date: String,
    val permissions_doc: String,
    val registration_certificate: String,
    val registration_certificate_date: String,
    val seats_available: String,
    val soat: String,
    val soat_date: String,
    val soat_number: String,
    val vehicle_number: String,
    val vehicle_relation: Int,
    val vehicle_relation_desc: String,
    val vehicle_type: String
)