package com.dubitaxi.ui.selectUserType.passengerSignUp.vo


import com.google.gson.annotations.SerializedName

data class GetBannerResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response,
    @SerializedName("status")
    val status: Int
) {
    data class Response(
        @SerializedName("result")
        val result: ArrayList<Result> = arrayListOf()
    ) {
        data class Result(
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("end_date")
            val endDate: Long,
            @SerializedName("_id")
            val id: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("is_blocked")
            val isBlocked: Boolean,
            @SerializedName("link")
            val link: String,
            @SerializedName("start_date")
            val startDate: Long,
            @SerializedName("title")
            val title: String,
            @SerializedName("updatedAt")
            val updatedAt: String
        )
    }
}