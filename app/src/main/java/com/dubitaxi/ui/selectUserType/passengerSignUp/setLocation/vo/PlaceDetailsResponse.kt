package com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PlaceDetailsResponse(
    val result: PlaceDetails,
    val status: String
):Parcelable {
    @Parcelize
    data class PlaceDetails(
        val geometry: Geometry,
        val name: String
    ):Parcelable {
        @Parcelize
        data class Geometry(
            val location: Location
        ):Parcelable {
            @Parcelize
            data class Location(
                val lat: Double,
                val lng: Double
            ):Parcelable
        }
    }
}