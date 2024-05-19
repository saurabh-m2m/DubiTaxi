package com.dubitaxi.model

import androidx.lifecycle.MutableLiveData
import com.dubitaxi.base.BaseViewModel
import com.dubitaxi.ui.selectUserType.home.request.logout_Response
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.ResendOTPResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.UserOTPVerifyResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo.GetSavedAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.request.UserRemoveAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.vo.UserRemoveAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.request.UsermarkandUnmarkAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.UserMarkandUnMarkAddresRespons
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.UserRecentilyAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.request.SavedAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.request.UpdateAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.vo.SavedAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setMap.vo.UpdateAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.CancelFareRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.CancelFareResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.CreateFareConfirmRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.CreateFareConfirmResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.GetFareDetailResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.GetFareDetailsRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.GetVehicleTypeDataResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.GetVehicleTypeListRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.vo.CreateAccountPassengerResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vo.GetBannerResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.vo.UserProfileResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class PassengerViewModel : BaseViewModel() {
    lateinit var disposable: Disposable
    val mUserProfileCompleteViewModel = MutableLiveData<CreateAccountPassengerResponse>()
    val mUserOTPVerifyViewModel = MutableLiveData<UserOTPVerifyResponse>()
    val mResendOTpViewModel = MutableLiveData<ResendOTPResponse>()
    var mUserProfileCreate = MutableLiveData<UserProfileResponse>()
    val mGetBannerViewModel = MutableLiveData<GetBannerResponse>()
    var mSavedAddressViewModel = MutableLiveData<SavedAddressResponse>()
    var mGetSavedAddressViewModel = MutableLiveData<GetSavedAddressResponse>()
    var mUserRemoveAddressViewModel = MutableLiveData<UserRemoveAddressResponse>()
    var mUserRecentlyAddressViewModel = MutableLiveData<UserRecentilyAddressResponse>()
    var mUserUpdateAddressViewModel = MutableLiveData<UpdateAddressResponse>()
    var mUserMarkandUnMarkAddressViewModel = MutableLiveData<UserMarkandUnMarkAddresRespons>()
    var mGetVehicleListViewModel = MutableLiveData<GetVehicleTypeDataResponse>()
    var mCreateFareConfirmViewModel = MutableLiveData<CreateFareConfirmResponse>()
    var mCreateFareDetailViewModel = MutableLiveData<GetFareDetailResponse>()
    var mCancelFareViewModel = MutableLiveData<CancelFareResponse>()
    var mLogoutViewModel = MutableLiveData<logout_Response>()
    val mError = MutableLiveData<Throwable>()
    var mProgress = MutableLiveData<Boolean>()

    fun mUserCreateAccountHitApi(
        country_code: String,
        phone_number: String,
        device_token: String,
        device_type: String,
        lat: Double,
        long: Double,country:String
    ) {
        disposable = mApiInterface.mUserCreateAccountResponseHitApi(
            country_code,
            phone_number,
            device_token,
            device_type,
            lat,
            long,
            country
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserProfileCompleteViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mUserOTPVerifyHitApi(otp: String, access_token: String) {
        disposable = mApiInterface.UserOTPVerifyHitApi(access_token, otp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserOTPVerifyViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mResendOTPHitApi(access_token: String) {
        disposable = mApiInterface.mResendOTPHitApi(access_token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mResendOTpViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mUserProfileCreateHitApi(
        access_token: String,
        profile_picture: String,
        full_name: String,
        email: String,
        country_code: String,
        phone_number: String,
        gender: String,
        is_profile_complete: String
    ) {
        disposable = mApiInterface.mUserProfileHitApi(
            access_token,
            profile_picture,
            full_name,
            email,
            country_code,
            phone_number,
            gender,
            is_profile_complete
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserProfileCreate.value = it
            }, {
                mError.value = it
            })
    }

    fun mUserGetBannersHitApi(access_token: String) {
        disposable = mApiInterface.mUserGetBannersHitApi(access_token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mGetBannerViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mSavedAddressHitApi(access_token: String,request: SavedAddressRequest) {
        disposable = mApiInterface.mSavedAddressHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mSavedAddressViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mGetSavedAddressHitApi(access_token: String) {
        disposable = mApiInterface.mGetSavedAddressHitApi(access_token = access_token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mGetSavedAddressViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mUserRemoveAddressHitApi(access_token: String,request: UserRemoveAddressRequest) {
        disposable = mApiInterface.mUserRemoveAddressHitApi(access_token = access_token,request=request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserRemoveAddressViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mUserRecentlyAddressHitApi(access_token: String) {
        disposable = mApiInterface.mUserRecentlyAddressHitApi(access_token = access_token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserRecentlyAddressViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mUserUpdateAddressHitApi(access_token: String,request: UpdateAddressRequest) {
        disposable = mApiInterface.mUserUpdateAddressHitApi(access_token = access_token,request=request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserUpdateAddressViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mUserMarkandUnMarkAddressHitApi(access_token: String,request: UsermarkandUnmarkAddressRequest) {
        disposable = mApiInterface.mUserMarkandUnMarkAddressHitApi(access_token = access_token,request=request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mUserMarkandUnMarkAddressViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mGetVehicleListUserHitApi(
        access_token: String,
        request: GetVehicleTypeListRequest
    ) {
        disposable = mApiInterface.mGetVehicleTypeListUser(
            access_token,
            request = request
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mGetVehicleListViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mCreateFareHitApi(
        access_token: String,
        request: CreateFareConfirmRequest
    ) {
        disposable = mApiInterface.mCreateFareConfirmUser(
            access_token,
            request = request
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mCreateFareConfirmViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mCreateFareDetailHitApi(
        access_token: String,
        request: GetFareDetailsRequest
    ) {
        disposable = mApiInterface.mCreateFareDetail(
            access_token,
            request = request
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mCreateFareDetailViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mCancelFareHitApi(
        access_token: String,
        request: CancelFareRequest
    ) {
        disposable = mApiInterface.mCancelFare(
            access_token,
            request = request
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mCancelFareViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mLogoutHitApi(
        access_token: String
    ) {
        disposable = mApiInterface.mLogoutFare(
            access_token,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mLogoutViewModel.value = it
            }, {
                mError.value = it
            })
    }
}