package com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails.request

data class AddBankDetailsRequest(
    val acc_holder_name: String,
    val acc_no: String,
    val accepted_tnc: Boolean,
    val bank_branch: String,
    val plin_no: String,
    val yape_no: String
)