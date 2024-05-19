package com.dubitaxi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPreferenceUtil
constructor(val context: Context) {
    private val tag = SharedPreferenceUtil::class.java.simpleName

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.PREFRENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreferenceUtil? = null

        fun getInstance(ctx: Context): SharedPreferenceUtil {
            if (instance == null) {
                instance = SharedPreferenceUtil(ctx)
            }
            return instance!!
        }
    }

    var isFromForgotPass: Boolean
        get() = sharedPreferences["isFromForgotPass", true]!!
        set(value) = sharedPreferences.set("isFromForgotPass", value)

    var deviceToken: String
        get() = sharedPreferences["deviceToken", ""]!!
        set(value) = sharedPreferences.set("deviceToken", value)

    var isNotify: Boolean
        get() = sharedPreferences["isNotify", true]!!
        set(value) = sharedPreferences.set("isNotify", value)

    var userImage: String
        get() = sharedPreferences["userImage", ""]!!
        set(value) = sharedPreferences.set("userImage", value)
    var driverImage: String
        get() = sharedPreferences["driverImage", ""]!!
        set(value) = sharedPreferences.set("driverImage", value)
    var userID: String
        get() = sharedPreferences["userID", ""]!!
        set(value) = sharedPreferences.set("userID", value)
    var accessToken: String
        get() = sharedPreferences["accessToken", ""]!!
        set(value) = sharedPreferences.set("accessToken", value)
    var is_active: Boolean
        get() = sharedPreferences["is_active", false]!!
        set(value) = sharedPreferences.set("is_active", value)
    var chat_userId: String
        get() = sharedPreferences["chat_userId", ""]!!
        set(value) = sharedPreferences.set("chat_userId", value)
    var currentaddress:String
        get() = sharedPreferences["currentaddress", ""]!!
        set(value) = sharedPreferences.set("currentaddress", value)

    var userHolderName: String
        get() = sharedPreferences["userHolderName", ""]!!
        set(value) = sharedPreferences.set("userHolderName", value)

    var isLogin: String
        get() = sharedPreferences["isLogin", ""]!!
        set(value) = sharedPreferences.set("IsLogin", value)

     var isDutyOn: Boolean
        get() = sharedPreferences["isDutyOn", false]!!
        set(value) = sharedPreferences.set("isDutyOn", value)





    var isLoginData: String?
        get() = sharedPreferences["isLoginData",""]!!
        set(value) = sharedPreferences.set("isLoginData", value)

    var mobileNumber: String
        get() = sharedPreferences["MobileNumber", ""]!!
        set(value) = sharedPreferences.set("MobileNumber", value)

    var mobileNumberCountry: String
        get() = sharedPreferences["mobileNumberCountry", ""]!!
        set(value) = sharedPreferences.set("mobileNumberCountry", value)


    var mobileNumberCountryCode: String
        get() = sharedPreferences["MobileNumberCountryCode", ""]!!
        set(value) = sharedPreferences.set("MobileNumberCountryCode", value)

    var minExp: String
        get() = sharedPreferences["minExp", ""]!!
        set(value) = sharedPreferences.set("minExp", value)
    var maxExp: String
        get() = sharedPreferences["maxExp", ""]!!
        set(value) = sharedPreferences.set("maxExp", value)


    var userAccountNumber: String
        get() = sharedPreferences["userAccountNumber", ""]!!
        set(value) = sharedPreferences.set("userAccountNumber", value)






    var isCard: String
        get() = sharedPreferences["isCard",""]!!
        set(value) = sharedPreferences.set("isCard", value)
    var userNumber: String
        get() = sharedPreferences["userNumber", ""]!!
        set(value) = sharedPreferences.set("userNumber", value)
    var userName: String
        get() = sharedPreferences["userName", ""]!!
        set(value) = sharedPreferences.set("userName", value)



    var driverName: String
        get() = sharedPreferences["driverName", ""]!!
        set(value) = sharedPreferences.set("driverName", value)




    var useremail: String
        get() = sharedPreferences["useremail", ""]!!
        set(value) = sharedPreferences.set("useremail", value)

    var userprofilePic: String
        get() = sharedPreferences["userprofilePic",""]!!
        set(value) = sharedPreferences.set("userprofilePic", value)

    var driverprofilePic: String
        get() = sharedPreferences["driverprofilePic",""]!!
        set(value) = sharedPreferences.set("driverprofilePic", value)

    var ProdutPic: String
        get() = sharedPreferences["ProdutPic", ""]!!
        set(value) = sharedPreferences.set("ProdutPic", value)

    var isEmail: String
        get() = sharedPreferences["isEmail", ""]!!
        set(value) = sharedPreferences.set("isEmail", value)


    var mUserPasswird: String
        get() = sharedPreferences["muserPasswird", ""]!!
        set(value) = sharedPreferences.set("muserPasswird", value)



    var userPickLatitude: String?
        get() = sharedPreferences["userPickLatitude", ""]!!
        set(value) = sharedPreferences.set("userPickLatitude", value)
    var userPickLongitude: String?
        get() = sharedPreferences["userPickLongitude", ""]!!
        set(value) = sharedPreferences.set("userPickLongitude", value)


    var userStopLatitude: String?
        get() = sharedPreferences["userStopLatitude", ""]!!
        set(value) = sharedPreferences.set("userStopLatitude", value)
    var userStopLongitude: String?
        get() = sharedPreferences["userStopLongitude", ""]!!
        set(value) = sharedPreferences.set("userStopLongitude", value)


    var userSavedLatitude: String?
        get() = sharedPreferences["userSavedLatitude", ""]!!
        set(value) = sharedPreferences.set("userSavedLatitude", value)
    var userSavedLongitude: String?
        get() = sharedPreferences["userSavedLongitude", ""]!!
        set(value) = sharedPreferences.set("userSavedLongitude", value)

    var userDropLatitude: String?
        get() = sharedPreferences["userDropLatitude", ""]!!
        set(value) = sharedPreferences.set("userDropLatitude", value)
    var userDropLongitude: String?
        get() = sharedPreferences["userDropLongitude", ""]!!
        set(value) = sharedPreferences.set("userDropLongitude", value)










    var isDriverActive: Boolean
        get() = sharedPreferences["isDriverActive",false]!!
        set(value) = sharedPreferences.set("isDriverActive", value)

    var canSeeUpcomingRequestDash: Boolean
        get() = sharedPreferences["canSeeUpcomingRequestDash"]!!
        set(value) = sharedPreferences.set("canSeeUpcomingRequestDash", value)

    var canApproveRejectRequestDash: Boolean
        get() = sharedPreferences["canApproveRejectRequestDash"]!!
        set(value) = sharedPreferences.set("canApproveRejectRequestDash", value)

    var isCountry: String
        get() = sharedPreferences["isCountry",""]!!
        set(value) = sharedPreferences.set("isCountry", value)

    var isVisibleProduct: Boolean
        get() = sharedPreferences["isVisibleProduct"]!!
        set(value) = sharedPreferences.set("isVisibleProduct", value)

    var viewItemProduct: Boolean
        get() = sharedPreferences["viewItemProduct"]!!
        set(value) = sharedPreferences.set("viewItemProduct", value)

    var addItemProduct: Boolean
        get() = sharedPreferences["addItemProduct"]!!
        set(value) = sharedPreferences.set("addItemProduct", value)

    var editItemProduct: Boolean
        get() = sharedPreferences["editItemProduct"]!!
        set(value) = sharedPreferences.set("editItemProduct", value)

    var deleteItemProduct: Boolean
        get() = sharedPreferences["deleteItemProduct"]!!
        set(value) = sharedPreferences.set("deleteItemProduct", value)

    var isVisibleReport: Boolean
        get() = sharedPreferences["isVisibleReport"]!!
        set(value) = sharedPreferences.set("isVisibleReport", value)

    var canExportItem: Boolean
        get() = sharedPreferences["canExportItem"]!!
        set(value) = sharedPreferences.set("canExportItem", value)

    var showQrCode: Boolean
        get() = sharedPreferences["showQrCode"]!!
        set(value) = sharedPreferences.set("showQrCode", value)

    var isVisibleOrder: Boolean
        get() = sharedPreferences["isVisibleOrder"]!!
        set(value) = sharedPreferences.set("isVisibleOrder", value)

    var generateInvoiceOrder: Boolean
        get() = sharedPreferences["generateInvoiceOrder"]!!
        set(value) = sharedPreferences.set("generateInvoiceOrder", value)

    var isVisibleDelivery: Boolean
        get() = sharedPreferences["isVisibleDelivery"]!!
        set(value) = sharedPreferences.set("isVisibleDelivery", value)

    var isVisibleInventory: Boolean
        get() = sharedPreferences["isVisibleInventory"]!!
        set(value) = sharedPreferences.set("isVisibleInventory", value)

    var isUpdateInventory: Boolean
        get() = sharedPreferences["isUpdateInventory"]!!
        set(value) = sharedPreferences.set("isUpdateInventory", value)

    var isVisibleFinance: Boolean
        get() = sharedPreferences["isVisibleFinance"]!!
        set(value) = sharedPreferences.set("isVisibleFinance", value)

    var canExportItemFinance: Boolean
        get() = sharedPreferences["canExportItemFinance"]!!
        set(value) = sharedPreferences.set("canExportItemFinance", value)

    var isVisibleOffer: Boolean
        get() = sharedPreferences["isVisibleOffer"]!!
        set(value) = sharedPreferences.set("isVisibleOffer", value)

    var viewItemOffer: Boolean
        get() = sharedPreferences["viewItemOffer"]!!
        set(value) = sharedPreferences.set("viewItemOffer", value)

    var productName:String
        get() = sharedPreferences["productName"]!!
        set(value) = sharedPreferences.set("productName", value)
    var productSellingPrice:String
        get() = sharedPreferences["productSellingPrice"]!!
        set(value) = sharedPreferences.set("productSellingPrice", value)

    var productPrice:String
        get() = sharedPreferences["productPrice"]!!
        set(value) = sharedPreferences.set("productPrice", value)

    var editItemOffer: Boolean
        get() = sharedPreferences["editItemOffer"]!!
        set(value) = sharedPreferences.set("editItemOffer", value)

    var deleteItemOffer: Boolean
        get() = sharedPreferences["deleteItemOffer"]!!
        set(value) = sharedPreferences.set("deleteItemOffer", value)


    var userType: String
        get() = sharedPreferences["userType", ""]?:""
        set(value) = sharedPreferences.set("userType", value)






    var isLoggedIn: Boolean
        get() = sharedPreferences["isLoggedIn", false]!!
        set(value) = sharedPreferences.set("isLoggedIn", value)
    var changeStatusOffer: Boolean
        get() = sharedPreferences["changeStatusOffer"]!!
        set(value) = sharedPreferences.set("changeStatusOffer", value)

    var isVisibleBranch: Boolean
        get() = sharedPreferences["isVisibleBranch"]!!
        set(value) = sharedPreferences.set("isVisibleBranch", value)

    var countryCode: String
        get() = sharedPreferences["countryCode"]!!
        set(value) = sharedPreferences.set("countryCode", value)

    var editItemBranch: Boolean
        get() = sharedPreferences["editItemBranch"]!!
        set(value) = sharedPreferences.set("editItemBranch", value)

    var deleteItemBranch: Boolean
        get() = sharedPreferences["deleteItemBranch"]!!
        set(value) = sharedPreferences.set("deleteItemBranch", value)

    var isVisibleCategory: Boolean
        get() = sharedPreferences["isVisibleCategory"]!!
        set(value) = sharedPreferences.set("isVisibleCategory", value)

    var mOTPExpTime: String
        get() = sharedPreferences["mOTPExpTime"]!!
        set(value) = sharedPreferences.set("mOTPExpTime", value)

    var editItemCategory: Boolean
        get() = sharedPreferences["editItemCategory"]!!
        set(value) = sharedPreferences.set("editItemCategory", value)

    var deleteItemCategory: Boolean
        get() = sharedPreferences["deleteItemCategory"]!!
        set(value) = sharedPreferences.set("deleteItemCategory", value)

    var changeStatusCategory: Boolean
        get() = sharedPreferences["changeStatusCategory"]!!
        set(value) = sharedPreferences.set("changeStatusCategory", value)

    var isVisibleRole: Boolean
        get() = sharedPreferences["isVisibleRole"]!!
        set(value) = sharedPreferences.set("isVisibleRole", value)

    var addItemRole: Boolean
        get() = sharedPreferences["addItemRole"]!!
        set(value) = sharedPreferences.set("addItemRole", value)

    var editItemRole: Boolean
        get() = sharedPreferences["editItemRole"]!!
        set(value) = sharedPreferences.set("editItemRole", value)

    var deleteItemRole: Boolean
        get() = sharedPreferences["deleteItemRole"]!!
        set(value) = sharedPreferences.set("deleteItemRole", value)

    var viewItemRole: Boolean
        get() = sharedPreferences["viewItemRole"]!!
        set(value) = sharedPreferences.set("viewItemRole", value)

    var isVisibleUser: Boolean
        get() = sharedPreferences["isVisibleUser"]!!
        set(value) = sharedPreferences.set("isVisibleUser", value)

    var mUserId: String
        get() = sharedPreferences["mUserId"]!!
        set(value) = sharedPreferences.set("mUserId", value)

    var editItemUser: Boolean
        get() = sharedPreferences["editItemUser"]!!
        set(value) = sharedPreferences.set("editItemUser", value)

    var deleteItemUser: Boolean
        get() = sharedPreferences["deleteItemUser"]!!
        set(value) = sharedPreferences.set("deleteItemUser", value)


    var scanQrCodeDriver: Boolean
        get() = sharedPreferences["scanQrCodeDriver"]!!
        set(value) = sharedPreferences.set("scanQrCodeDriver", value)


    var isStoreOnline: Boolean
        get() = sharedPreferences["isStoreOnline"]!!
        set(value) = sharedPreferences.set("isStoreOnline", value)


    fun userPermissionArray(
        array: ArrayList<Boolean?>,
        arrayName: String,
        mContext: Context
    ): Boolean {
        val prefs = mContext.getSharedPreferences("userPermissionArray", 0)
        val editor = prefs.edit()
        editor.putInt(arrayName + "_size", array.size)
        for (i in array.indices) editor.putBoolean(arrayName + "_" + i, array[i]!!)
        return editor.commit()
    }

    fun loadArray(arrayName: String, mContext: Context): Array<Boolean?> {
        val prefs: SharedPreferences = mContext.getSharedPreferences("userPermissionArray", 0)
        val size = prefs.getInt(arrayName + "_size", 0)
        val array = arrayOfNulls<Boolean>(size)
        for (i in 0 until size) array[i] = prefs.getBoolean(arrayName + "_" + i, false)
        return array
    }

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }

            is Int -> edit { it.putInt(key, value) }

            is Boolean -> edit { it.putBoolean(key, value) }

            is Float -> edit { it.putFloat(key, value) }

            is Long -> edit { it.putLong(key, value) }

            else -> Log.e(tag, "Setting shared pref failed for key: $key and value: $value ")
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun deletePreferences() {
        editor.clear()
        editor.apply()
    }
}