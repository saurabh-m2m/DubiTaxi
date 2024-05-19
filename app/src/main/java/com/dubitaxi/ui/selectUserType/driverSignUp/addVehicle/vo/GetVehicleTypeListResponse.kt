package com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetVehicleTypeListResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("status")
    val status: Int?
) : Parcelable {
    @Parcelize
    data class Response(
        @SerializedName("result")
        val result: ArrayList<Result> = arrayListOf()
    ) : Parcelable {
        @Parcelize
        data class Result(
            @SerializedName("createdAt")
            val createdAt: String?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("icon")
            val icon: String?,
            @SerializedName("_id")
            val id: String?,
            @SerializedName("is_blocked")
            val isBlocked: Boolean?,
            @SerializedName("service_type")
            val serviceType: Int?,
            @SerializedName("status")
            val status: Int?,
            @SerializedName("updatedAt")
            val updatedAt: String?,
            @SerializedName("vehicle_type")
            val vehicleType: String?
        ) : Parcelable
    }
}