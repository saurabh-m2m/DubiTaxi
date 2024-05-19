package com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.PlaceDetailsResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.SearchLocationResponse
import com.dubitaxi.webservice.mApiInterface
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SearchViewModel :ViewModel(){
    var input=""
    var placeId=""
    var mProgess = MutableLiveData<Boolean>()
    var error = MutableLiveData<Throwable>()
    var searchLocationResponse = MutableLiveData<SearchLocationResponse>()
    var onPlaceDetailsResponse = MutableLiveData<PlaceDetailsResponse>()


    fun hitGetLocationApi() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .build()
        val api = retrofit.create(mApiInterface::class.java)
        val disposable = api.getLocation(
            //key = "AIzaSyDamZ7d0ReWyBLinPRyZDNSiBNXjEsI37Q",
            key = "AIzaSyCfR7HZDKTU5PfrulgTHzo9fMfYwVTuSLY",
            input = input
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onGetLocationSuccess(it) }, { onFailure(it) })
    }

    private fun onGetLocationSuccess(it: SearchLocationResponse) {
        searchLocationResponse.value = it
    }private fun onFailure(it: Throwable) {
        error.value = it
    }
    fun hitGetPlacesDetailsApi() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .build()
        val api = retrofit.create(mApiInterface::class.java)
        val disposable = api.getPlaceDetails(
            // key = "AIzaSyDIv4lxpOPlIDQ5sLOpHHZ8O0plZBTSaMA",
            key = "AIzaSyCfR7HZDKTU5PfrulgTHzo9fMfYwVTuSLY",
            placeid = placeId
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onPlaceDetailsSuccess(it) }, { onFailure(it) })

    }
    private fun onPlaceDetailsSuccess(it: PlaceDetailsResponse) {
        onPlaceDetailsResponse.value = it

    }




}