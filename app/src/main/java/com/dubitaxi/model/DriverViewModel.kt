package com.dubitaxi.model

import androidx.lifecycle.MutableLiveData
import com.dubitaxi.base.BaseViewModel
import com.dubitaxi.ui.selectUserType.createmobilenumber.vo.DriverCreateAccountResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.AddVehicleRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.EditVehicleRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.GetParticularVehicleRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.request.GetVehicleTypeRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo.AddVehicleResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo.EditvehicleResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo.GetParticularVehicleResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo.GetVehicleTypeListResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails.request.AddBankDetailsRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.bankDetails.vo.AddDriverBankDetailsResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.request.DriverDetailsRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.vo.DriverDetailsResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.vo.DriverGetVehicleResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.request.DriverPersonalDetailsRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.vo.CityResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.vo.DriverPersonalDetailResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.driverPersonaldetails.vo.DriverUpLoadImageResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.request.DriverCreateAccountRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.request.DriverOTPVerificationRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.subscription.request.DriverPurchaseSubscriptionRequest
import com.dubitaxi.ui.selectUserType.driverSignUp.subscription.vo.DriverPurchaseSubscriptionResponse
import com.dubitaxi.ui.selectUserType.driverSignUp.subscription.vo.GetSubscriptionPlansResponse
import com.dubitaxi.ui.selectUserType.home.request.DriverToggleActiveRequest
import com.dubitaxi.ui.selectUserType.home.vo.DriverToggleActiveResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.DriverOTPVerificationResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.DriverResendOTPResponse
import com.dubitaxi.utils.convertFormFileToMultipartBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class DriverViewModel : BaseViewModel() {
    lateinit var disposable: Disposable
    val mError = MutableLiveData<Throwable>()
    var mProgress = MutableLiveData<Boolean>()
    val mDriverCreateAccount = MutableLiveData<DriverCreateAccountResponse>()
    val mDriverOTPVerificationViewModel = MutableLiveData<DriverOTPVerificationResponse>()
    var mDriverUpLoadImageViewModel = MutableLiveData<DriverUpLoadImageResponse>()
    var mDriverPersonalDetailViewModel = MutableLiveData<DriverPersonalDetailResponse>()
    var mDriverGetCityViewModel = MutableLiveData<CityResponse>()
    var mDriverGetVehiclesListViewModel = MutableLiveData<DriverGetVehicleResponse>()
    var mDriverDetailsViewModel = MutableLiveData<DriverDetailsResponse>()
    var mAddVehicleViewModel = MutableLiveData<AddVehicleResponse>()
    var mAddDriverBankDetailsViewModel = MutableLiveData<AddDriverBankDetailsResponse>()
    var mGetVehicleListViewModel = MutableLiveData<GetVehicleTypeListResponse>()
    var mEditVehicleViewModel = MutableLiveData<EditvehicleResponse>()
    var mDriverResendOTPViewModel = MutableLiveData<DriverResendOTPResponse>()
    var mDriverParticularVehicleViewModel = MutableLiveData<GetParticularVehicleResponse>()
    var mDriverToggleActiveViewModel = MutableLiveData<DriverToggleActiveResponse>()
    var mDriverGetSubscriptionPlan = MutableLiveData<GetSubscriptionPlansResponse>()
    var mDriverSubsriptionPurchasePlanViewModel = MutableLiveData<DriverPurchaseSubscriptionResponse>()


    fun mDriverCreateAccountHitApi(request: DriverCreateAccountRequest) {
        disposable =
            mApiInterface.mDriverCreateAccount(request = request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    mProgress.value = true
                }.doOnTerminate {
                    mProgress.value = false
                }.subscribe({
                    mDriverCreateAccount.value = it
                }, {
                    mError.value = it
                })
    }

    fun mDriverOTPVerificationHitApi(access_token: String, request: DriverOTPVerificationRequest) {
        disposable = mApiInterface.mDriverOTPVerificationHitApi(
            access_token = access_token, request = request
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mProgress.value = true
        }.doOnTerminate {
            mProgress.value = false
        }.subscribe({
            mDriverOTPVerificationViewModel.value = it
        }, {
            mError.value = it
        })
    }

    fun mDriverUpLoadImageHitApi(file: File?) {
        disposable =
            mApiInterface.mDriverUpLoadImage(file = convertFormFileToMultipartBody("file", file))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mProgress.value = true
                }.doOnTerminate {
                    mProgress.value = false
                }.subscribe({
                    mDriverUpLoadImageViewModel.value = it
                }, {
                    mError.value = it
                })
    }

    fun mDriverPersonalDetailsHitAPi(access_token: String, request: DriverPersonalDetailsRequest) {
        disposable = mApiInterface.mDriverPersonalDetailsHitApi(
            access_token = access_token, request = request
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mProgress.value = true
        }.doOnTerminate {
            mProgress.value = false
        }.subscribe({
            mDriverPersonalDetailViewModel.value = it
        }, {
            mError.value = it
        })
    }

    fun mDriverCityHitAPi(access_token: String) {
        disposable = mApiInterface.mDriverCityHitApi(
            access_token = access_token
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mProgress.value = true
        }.doOnTerminate {
            mProgress.value = false
        }.subscribe({
            mDriverGetCityViewModel.value = it
        }, {
            mError.value = it
        })
    }
    fun mDriverGetVehiclesListHitAPi(access_token: String) {
        disposable = mApiInterface.mDriverGetAllVehiclesHitApi(access_token = access_token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
            mProgress.value = true
        }.doOnTerminate {
            mProgress.value = false
        }.subscribe({
            mDriverGetVehiclesListViewModel.value = it
        }, {
            mError.value = it
        })
    }
    fun mDriverDetailsHitApi(access_token:String, request: DriverDetailsRequest){
        disposable = mApiInterface.mDriverDetailsHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mDriverDetailsViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mAddVehicleHitApi(access_token:String,request:AddVehicleRequest){
        disposable = mApiInterface.mAddVehicleHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mAddVehicleViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mAddDriverBankDetailsHitApi(access_token:String,request:AddBankDetailsRequest){
        disposable = mApiInterface.mAddDriverBankDetailsHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mAddDriverBankDetailsViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mGetVehicleListHitAPi(access_token:String,request: GetVehicleTypeRequest){
        disposable = mApiInterface.mGetVehicleTypeList(access_token = access_token,request=request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mGetVehicleListViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mEditVehicleHitAPi(access_token:String,request: EditVehicleRequest){
        disposable = mApiInterface.mEditVehicleHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mEditVehicleViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mDriverResendOTPHitAPi(access_token:String){
        disposable = mApiInterface.mDriverResendOTPHitApi(access_token = access_token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mDriverResendOTPViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mGetParticularVehicleHitAPi(access_token:String,request: GetParticularVehicleRequest){
        disposable = mApiInterface.mGetParticularVehicleHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mDriverParticularVehicleViewModel.value = it
            }, {
                mError.value = it
            })
    }
    fun mDriverToggleActiveHitAPi(access_token:String,request: DriverToggleActiveRequest){
        disposable = mApiInterface.mDriverToggleActiveHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mDriverToggleActiveViewModel.value = it
            }, {
                mError.value = it
            })
    }

    fun mDriverSubscriptionPlanHitAPi(access_token:String){
        disposable = mApiInterface.mGetDriverSubcriptionPlanHitApi(access_token = access_token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mDriverGetSubscriptionPlan.value = it
            }, {
                mError.value = it
            })
    } fun mDriverSubscriptionPurchasePlanHitAPi(access_token:String,request:DriverPurchaseSubscriptionRequest){
        disposable = mApiInterface.mDriverPurchasePlanHitApi(access_token = access_token, request = request)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                mProgress.value = true
            }.doOnTerminate {
                mProgress.value = false
            }.subscribe({
                mDriverSubsriptionPurchasePlanViewModel.value = it
            }, {
                mError.value = it
            })
    }
}