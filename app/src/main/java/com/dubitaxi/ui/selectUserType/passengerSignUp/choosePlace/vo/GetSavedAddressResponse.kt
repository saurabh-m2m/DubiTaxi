package com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetSavedAddressResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response,
    @SerializedName("status")
    val status: Int
): Parcelable {
    @Parcelize
    data class Response(
        @SerializedName("result")
        val result: ArrayList<Result> = arrayListOf()
    ): Parcelable {
        @Parcelize
        data class Result(
            @SerializedName("address_name")
            val addressName: String,
            @SerializedName("address_type")
            val addressType: Int,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("frequency_used")
            val frequencyUsed: Int,
            @SerializedName("_id")
            val id: String,
            @SerializedName("is_blocked")
            val isBlocked: Boolean,
            @SerializedName("isFavorite")
            val isFavorite: Boolean,
            @SerializedName("lat")
            val lat: Double,
            @SerializedName("long")
            val long: Double,
            @SerializedName("official_name")
            val officialName: String,
            @SerializedName("status")
            val status: Int,
            @SerializedName("updatedAt")
            val updatedAt: String,
            @SerializedName("user_id")
            val userId: String
        ): Parcelable
    }
}