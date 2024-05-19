package com.dubitaxi.ui.selectUserType.driverSignUp.subscription.vo


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetSubscriptionPlansResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("status")
    val status: Int?
) : Parcelable {
    @Parcelize
    data class Response(
        @SerializedName("plans")
        val plans: ArrayList<Plan> = arrayListOf()
    ) : Parcelable {
        @Parcelize
        data class Plan(
            @SerializedName("active_hours")
            val activeHours: Int?,
            @SerializedName("charges")
            val charges: Int?,
            @SerializedName("createdAt")
            val createdAt: String?,
            @SerializedName("currency")
            val currency: String?,
            @SerializedName("duration")
            val duration: String?,
            @SerializedName("_id")
            val id: String?,
            @SerializedName("is_blocked")
            val isBlocked: Boolean?,
            @SerializedName("plan_name")
            val planName: String?,
            @SerializedName("service_name")
            val serviceName: String?,
            @SerializedName("service_type")
            val serviceType: Int?,
            @SerializedName("updatedAt")
            val updatedAt: String?,
            @SerializedName("vehicle_type")
            val vehicleType: VehicleType?
        ) : Parcelable {
            @Parcelize
            data class VehicleType(
                @SerializedName("_id")
                val id: String?,
                @SerializedName("vehicle_type")
                val vehicleType: String?
            ) : Parcelable
        }
    }
}