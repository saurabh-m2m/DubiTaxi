package com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails.vo

data class Account(
    val _id: String,
    val acc_holder_name: String,
    val acc_no: String,
    val aggreed_tnc: Boolean,
    val bank_branch: String,
    val createdAt: String,
    val driver_id: String,
    val is_blocked: Boolean,
    val plin_no: String,
    val status: Int,
    val updatedAt: String,
    val yape_no: String
)