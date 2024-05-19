package com.dubitaxi.webservice

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
import com.dubitaxi.ui.selectUserType.home.request.logout_Response
import com.dubitaxi.ui.selectUserType.home.vo.DriverToggleActiveResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.DriverOTPVerificationResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.DriverResendOTPResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.ResendOTPResponse
import com.dubitaxi.ui.selectUserType.otpVerifiction.vo.UserOTPVerifyResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo.GetSavedAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.request.UserRemoveAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress.vo.UserRemoveAddressResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.request.UsermarkandUnmarkAddressRequest
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.PlaceDetailsResponse
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.SearchLocationResponse
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
import com.dubitaxi.utils.AppConstants
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface mApiInterface {
    //Login Api Response
    @FormUrlEncoded
    @POST(AppConstants.USER_CREATE_ACCOUNT)
    fun mUserCreateAccountResponseHitApi(
        @Field("country_code") country_code: String,
        @Field("phone_number") phone_number: String,
        @Field("device_token") device_token: String,
        @Field("device_type") device_type: String,
        @Field("lat") lat: Double,
        @Field("long") long: Double,
        @Field("country") country: String,
    ): Observable<CreateAccountPassengerResponse>

    @FormUrlEncoded
    @POST(AppConstants.USER_OTP_VERIFICATION)
    fun UserOTPVerifyHitApi(
        @Header("access_token") access_token: String,
        @Field("otp") otp: String,
    ): Observable<UserOTPVerifyResponse>

    @GET(AppConstants.USER_RESEND_OTP)
    fun mResendOTPHitApi(
        @Header("access_token") access_token: String
    ): Observable<ResendOTPResponse>


    @FormUrlEncoded
    @POST(AppConstants.USER_PROFILE)
    fun mUserProfileHitApi(
        @Header("access_token") access_token: String,
        @Field("profile_picture") profile_picture: String,
        @Field("full_name") full_name: String,
        @Field("email") email: String,
        @Field("country_code") country_code: String,
        @Field("phone_number") phone_number: String,
        @Field("gender") gender: String,
        @Field("is_profile_complete") is_profile_complete: String,
    ): Observable<UserProfileResponse>

    @GET(AppConstants.GET_BANNERS)
    fun mUserGetBannersHitApi(
        @Header("access_token") access_token: String,
    ): Observable<GetBannerResponse>


    @POST(AppConstants.SAVED_ADDRESS)
    fun mSavedAddressHitApi(
        @Header("access_token") access_token: String,
        @Body request: SavedAddressRequest
    ): Observable<SavedAddressResponse>

    @POST(AppConstants.UPDATE_ADDRESS)
    fun mUserUpdateAddressHitApi(
        @Header("access_token") access_token: String,
        @Body request: UpdateAddressRequest
    ): Observable<UpdateAddressResponse>


    @GET(AppConstants.GET_SAVED_ADDRESS)
    fun mGetSavedAddressHitApi(
        @Header("access_token") access_token: String,
    ): Observable<GetSavedAddressResponse>

    @POST(AppConstants.USER_REMOVE_ADDRESS)
    fun mUserRemoveAddressHitApi(
        @Header("access_token") access_token: String,
        @Body request: UserRemoveAddressRequest
    ): Observable<UserRemoveAddressResponse>

    @GET(AppConstants.USER_RECENTLY_ADDRESS)
    fun mUserRecentlyAddressHitApi(
        @Header("access_token") access_token: String,
    ): Observable<UserRecentilyAddressResponse>

    @POST(AppConstants.USER_MARK_UNMARK_ADDRESS)
    fun mUserMarkandUnMarkAddressHitApi(
        @Header("access_token") access_token: String,
        @Body request: UsermarkandUnmarkAddressRequest
    ): Observable<UserMarkandUnMarkAddresRespons>

    //User Serach Address -------------------------------------------
    @GET("autocomplete/json")
    fun getLocation(
        @Query("key") key: String,
        @Query("input") input: String
    ): Observable<SearchLocationResponse>

    @GET("details/json")
    fun getPlaceDetails(
        @Query("key") key: String,
        @Query("placeid") placeid: String
    ): Observable<PlaceDetailsResponse>





    // Diriver Api Response
    @POST(AppConstants.DRIVER_CREATE_ACCOUNT)
    fun mDriverCreateAccount(
        @Body request: DriverCreateAccountRequest
    ): Observable<DriverCreateAccountResponse>

    @POST(AppConstants.DRIVER_OTP_VERIFICATION)
    fun mDriverOTPVerificationHitApi(
        @Header("access_token") access_token: String,
        @Body request: DriverOTPVerificationRequest
    ): Observable<DriverOTPVerificationResponse>

    @Multipart
    @POST(AppConstants.DRIVER_UPLOAD_IMAGE)
    fun mDriverUpLoadImage(
        @Part file: MultipartBody.Part? = null
    ): Observable<DriverUpLoadImageResponse>


    @POST(AppConstants.DRIVER_PERSONAL_DETAILS)
    fun mDriverPersonalDetailsHitApi(
        @Header("access_token") access_token: String,
        @Body request: DriverPersonalDetailsRequest
    ): Observable<DriverPersonalDetailResponse>

    @GET(AppConstants.DRIVER_CITY)
    fun mDriverCityHitApi(
        @Header("access_token") access_token: String
    ): Observable<CityResponse>

    @GET(AppConstants.DRIVER_GET_ALL_VEHICLES)
    fun mDriverGetAllVehiclesHitApi(
        @Header("access_token") access_token: String
    ): Observable<DriverGetVehicleResponse>

    @POST(AppConstants.DRIVER_DETAILS)
    fun mDriverDetailsHitApi(
        @Header("access_token") access_token: String,
        @Body request: DriverDetailsRequest
    ): Observable<DriverDetailsResponse>

    @POST(AppConstants.ADD_VEHICLE)
    fun mAddVehicleHitApi(
        @Header("access_token") access_token: String,
        @Body request: AddVehicleRequest
    ): Observable<AddVehicleResponse>

    @POST(AppConstants.ADD_DRIVER_BANK_DETAILS)
    fun mAddDriverBankDetailsHitApi(
        @Header("access_token") access_token: String,
        @Body request: AddBankDetailsRequest
    ): Observable<AddDriverBankDetailsResponse>

    @POST(AppConstants.GET_VEHICLE_TYPE)
    fun mGetVehicleTypeList(
        @Header("access_token") access_token: String,
        @Body request: GetVehicleTypeRequest
    ): Observable<GetVehicleTypeListResponse>

    @POST(AppConstants.EDIT_VEHICLE)
    fun mEditVehicleHitApi(
        @Header("access_token") access_token: String,
        @Body request: EditVehicleRequest
    ): Observable<EditvehicleResponse>

    @GET(AppConstants.DRIVER_RESEND_OTP)
    fun mDriverResendOTPHitApi(
        @Header("access_token") access_token: String,
    ): Observable<DriverResendOTPResponse>

    @POST(AppConstants.DRIVER_GET_PARTICULAR_VEHICLE_DETAILS)
    fun mGetParticularVehicleHitApi(
        @Header("access_token") access_token: String,
        @Body request: GetParticularVehicleRequest
    ): Observable<GetParticularVehicleResponse>


    //M2-----------------------------------------
    @POST(AppConstants.DRIVER_TOGGLE_ACTIVE)
    fun mDriverToggleActiveHitApi(
        @Header("access_token") access_token: String,
        @Body request: DriverToggleActiveRequest
    ): Observable<DriverToggleActiveResponse>

    @GET(AppConstants.DRIVER_GET_SUBSCRIPTION_PLANS)
    fun mGetDriverSubcriptionPlanHitApi(
        @Header("access_token") access_token: String,
    ): Observable<GetSubscriptionPlansResponse>

    @POST(AppConstants.DRIVER_PURCHASE_SUBSCRIPTION_PLAN)
    fun mDriverPurchasePlanHitApi(
        @Header("access_token") access_token: String,
        @Body request: DriverPurchaseSubscriptionRequest
    ): Observable<DriverPurchaseSubscriptionResponse>

    @POST(AppConstants.GetVehicleTypeList)
    fun mGetVehicleTypeListUser(
        @Header("access_token") access_token: String,
        @Body request: GetVehicleTypeListRequest
    ): Observable<GetVehicleTypeDataResponse>

    @POST(AppConstants.CreateFareConfirm)
    fun mCreateFareConfirmUser(
        @Header("access_token") access_token: String,
        @Body request: CreateFareConfirmRequest
    ): Observable<CreateFareConfirmResponse>

    @POST(AppConstants.GetFareDetails)
    fun mCreateFareDetail(
        @Header("access_token") access_token: String,
        @Body request: GetFareDetailsRequest
    ): Observable<GetFareDetailResponse>

    @POST(AppConstants.CancelFare)
    fun mCancelFare(
        @Header("access_token") access_token: String, @Body request: CancelFareRequest
    ): Observable<CancelFareResponse>

    @POST(AppConstants.mLogout)
    fun mLogoutFare(
        @Header("access_token") access_token: String
    ): Observable<logout_Response>


}