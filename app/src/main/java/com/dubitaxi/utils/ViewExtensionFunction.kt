package com.dubitaxi.utils


import android.Manifest
import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.media.MediaMetadataRetriever
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import android.text.Html
import android.text.format.DateFormat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dubitaxi.utils.AppConstants.Companion.EMAIL_PATTERN
import com.dubitaxi.utils.AppConstants.Companion.PASSWORD_PATTERN
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/*
    View Regrading Function
*/
/*
    Validation on Mobile Number EditText
*/
var toast: Toast? = null
var gson: Gson? = null

fun EditText.isValidMobileNumber(): Boolean {
    return this.getString().length in 8..16
}
fun getGsonInstance(): Gson {
    if (gson == null)
        gson = Gson()
    return gson!!
}
private var fromTimeStamp: Long? = null
private var toTimeStamp: Long? = null

fun convertFormFileToMultipartBody(key: String, file: File?): MultipartBody.Part? = file?.let {
    MultipartBody.Part.createFormData(
        key, it.name,
        it.asRequestBody("image/*".toMediaTypeOrNull())
    )

}


interface DayListener {
    fun getDate(dob: String, dobTimeStamp: String)
}
fun datePickerCurrentDate(context: Context,minDateTimeStamp:Long?,maxDateTimStamp:Long?,date: MyTimePicker) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val dpd =
        DatePickerDialog(
            context,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val tempMonth = mMonth + 1

                if (tempMonth > 9)
                    if (mDay > 9)
                        date.date(mDay.toString(), tempMonth.toString(), mYear.toString())
                    else
                        date.date("0$mDay", tempMonth.toString(), mYear.toString())
                else
                    if (mDay > 9)
                        date.date(mDay.toString(), "0$tempMonth", mYear.toString())
                    else
                        date.date("0$mDay", "0$tempMonth", mYear.toString())
            },
            year,
            month,
            day
        )
    if (minDateTimeStamp!=null)
        dpd.datePicker.minDate = minDateTimeStamp
  /*  if (maxDateTimStamp!=null)
        dpd.datePicker.maxDate = maxDateTimStamp*/
    dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dpd.show()
}

fun dateConvertIntoTimeStampWithTime(dob: String): Long {
    val formatter = SimpleDateFormat("dd/MM/yyyy ", Locale.getDefault())
    val date = formatter.parse(dob) as Date
    return date.time
}

























fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
    val win = activity.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}
fun Fragment.statusBarTransparent() {
    if (Build.VERSION.SDK_INT in 19..20) {
        setWindowFlag(
            requireActivity(),
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            true
        )
    }
    if (Build.VERSION.SDK_INT >= 19) {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(
            requireActivity(),
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        )
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }
}
fun AppCompatActivity.statusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.apply {
            statusBarColor = Color.TRANSPARENT
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        }
    }

}
fun status2(activity:Activity,context:Context){
    activity.window.setStatusBarColor(ContextCompat.getColor(context, R.color.black))
    val decorView: View = activity.window.getDecorView() //set status background black

    decorView.systemUiVisibility =
        decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
}
fun Long.getTimeFromTimeStamp(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy");
    val timeString = formatter.format(Date(this));
    return timeString
}
interface OnActionFilter {
    fun onClickData(view: View, fromDate: String, toDate: String)
}
interface MyTimePicker {
    fun date(dD: String, mM: String, yY: String)
}
/*
    Validation on Mobile Number TextView
*/
fun TextView.isValidMobileNumber(): Boolean {
    return this.getString().length in 8..16
}
/*
    Set View Background Color
*/
fun View.setBGColor(activity: Activity, color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(activity, color))
}

/*
    Set TextView Text Color
*/
fun TextView.setColor(activity: Activity, color: Int) {
    this.setTextColor(ContextCompat.getColor(activity, color))
}

/*
    Set Image Url Into ImageView
*/
fun ImageView.setImage(url: Uri) {
    Picasso.get().load(url).into(this)
}

/*
    Validation on Email Id
*/
fun EditText.isValidEmail(): Boolean {
    return if (this.getString().length < 3 || this.getString().length > 265)
        false
    else {
        this.getString().matches(EMAIL_PATTERN.toRegex())
    }
}

/*
    Validation on Password
*/
fun EditText.isValidPassword(): Boolean {
    return if (this.getString().length < 8) {
        false
    } else {
        val matcher: Matcher
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(this.getString())
        matcher.matches()
    }
}

/*
    Find String From Edittext
*/
fun EditText.getString(): String {
    return this.text.toString().trim()
}

/*
    Find String From TextView
*/
fun TextView.getString(): String {
    return this.text.toString().trim()
}

/*
    Set View Visibility to VISIBLE
*/
fun View.show() {
    this.visibility = View.VISIBLE
}

/*
    Set View Visibility to GONE
*/
fun View.hide() {
    this.visibility = View.GONE
}

/*
    Check EditText Text is a Number
*/
fun EditText.isANumber(): Boolean {
    return try {
        this.getString().toDouble()
        true
    } catch (e: Exception) {
        false
    }
}

/*
    Set View Background Color
*/
fun View.setBgColor(context: Context, color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}

/*
    Set View Background Drawable
*/
fun View.setBgDrawable(context: Context, drawable: Int) {
    this.background = ContextCompat.getDrawable(context, drawable)
}

/*
    Set TextView Text Color
*/
fun TextView.textColor(activity: Activity, color: Int) {
    this.setTextColor(ContextCompat.getColor(activity, color))
}

/*
    Set Html String in TextView
*/
fun TextView.setHtml(html: String) {
    this.text = Html.fromHtml(html)
}

/*
    Show Password on EditText
*/
fun EditText.showPassword() {
    this.transformationMethod = HideReturnsTransformationMethod.getInstance()
    this.isFocusableInTouchMode = true
    val length: Int = this.getString().length
    this.setSelection(length)
}

/*
    Hide Password on EditText
*/
fun EditText.hidePassword() {
    this.transformationMethod = PasswordTransformationMethod.getInstance()
    this.isFocusableInTouchMode = true
    val length: Int = this.getString().length
    this.setSelection(length)
}

/*
    Set Text With Request Focus EditText
*/
fun EditText.setTextWithRequestFocus(message: String) {
    this.setText(message)
    val length: Int = this.getString().length
    this.setSelection(length)
}


/*
TimeStamp Regarding Function
*/

/*
    Find Date In "Jan 15, 2021" Format
*/
fun findMMMdYYYYDate(timeStamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timeStamp
    return DateFormat.format("MMM d, yyyy", cal).toString()
}

/*
    Find Time In "1.30 am" Format
*/
fun findTimeFromTimeStamp(timeStamp: Long): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = timeStamp
    return DateFormat.format("h:mm a", cal).toString()
}

/*
    Find Time and Time In "11 Jan, 2021 | 09:30 am" Format
*/
fun findDateAndTime(timeStamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timeStamp
    val date = DateFormat.format("dd MMM yyyy", cal).toString()
    val time = DateFormat.format("h:mm a", cal).toString()
    return "$date | $time"
}

/*
    Find Date In "10 Mar 2021" Format
*/
fun findddMMMyyyyDate(timeStamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timeStamp
    return DateFormat.format("dd MMM yyyy", cal).toString()
}

/*
    Find Date In "18/01/2021" Format
*/
fun findShortDate(timeStamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timeStamp
    return DateFormat.format("dd/MMM/yyyy", cal).toString()
}

/*
    Find Today Date In "10 Mar 2021" Format
*/
fun findTodayDate(): String {
    val date: Date = Date()
    val currentDate = DateFormat.format("dd MMM yyyy", date.time)
    return currentDate.toString()
}

/*
    Find Current Time Stamp
*/
fun findCurrentTimeStamp(): Long {
    val date: Date = Date()
    return date.time
}

/*
    Find Date In "10 Mar 2021" Format From "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
*/
fun findDateAndTimeFromString(updatedAt: String): String? {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date: Date? = simpleDateFormat.parse(updatedAt)
    val time = date?.time
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(time)
}

/*
    Show Date Picker Dialog
*/
fun Activity.showDatePicker(editText: EditText) {
    val calendar = Calendar.getInstance()
    val year1 = calendar.get(Calendar.YEAR)
    val month1 = calendar.get(Calendar.MONTH)
    val day1 = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        this, { view, year, monthOfYear, dayOfMonth ->
            val date = String.format("%02d", dayOfMonth)
            val month = String.format("%02d", (monthOfYear + 1))
            val fullDate = "$date/$month/$year"
            editText.setText(fullDate)
        },
        year1,
        month1,
        day1
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    datePickerDialog.show()
}

/*
    Show Time Picker Dialog
*/
fun Activity.showHourPicker(editText: EditText) {
    val mCurrentTime = Calendar.getInstance()
    val hour = mCurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mCurrentTime[Calendar.MINUTE]
    val mTimePicker = TimePickerDialog(
        this, { timePicker, selectedHour, selectedMinute ->
            val time = "$selectedHour:$selectedMinute"
            editText.setText(time)
        },
        hour,
        minute,
        false
    )
    mTimePicker.setTitle("Select Time")
    mTimePicker.show()
}

/*
    Find the Date or Time Difference From Current TimeStamp
*/
fun findDifferenceFromCurrentTimeStamp(date: Long): String {
    val currentTimeStamp: Long = findCurrentTimeStamp()
    val endTimeStamp: Long = date + 86399999
    val difference: Long = endTimeStamp - currentTimeStamp

    val diffSeconds: Int = (difference / 1000 % 60).toInt()
    val diffMinutes: Int = (difference / (60 * 1000) % 60).toInt()
    val diffHours: Int = (difference / (60 * 60 * 1000)).toInt()
    val diffInDays: Int = (difference / (1000 * 60 * 60 * 24)).toInt()
    val diffInMonths: Int = (diffInDays / 30)
    val diffInYear: Int = (diffInMonths / 12)

    var returnString = ""
    when {
        diffInYear >= 1 -> {
            returnString = "$diffInYear Year"
        }

        diffInMonths >= 1 -> {
            returnString = "$diffInMonths Month"
        }

        diffInDays >= 1 -> {
            returnString = "$diffInDays Day"
        }

        diffHours >= 1 -> {
            returnString = "$diffHours Hour"
        }

        diffMinutes >= 1 -> {
            returnString = "$diffMinutes Minute"
        }

        diffSeconds >= 1 -> {
            returnString = "$diffSeconds Second"
        }
    }
    return returnString
}

/*
    Find the TimeStamp Difference From Current TimeStamp
*/
fun findTimeDifference(timeStamp: Long): Long {
    val currentTimeStamp: Long = System.currentTimeMillis()
    return timeStamp - currentTimeStamp
}


/*
Android Permission Check
*/

/*
    Check Camera and Storage Read Write Permission
*/

/*fun checkCameraAndStoragePermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
        }
    })
    return granted
}*/


/*
    Check Storage Read Write Permission
*/
/*fun checkStoragePermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
        }
    })
    return granted
}*/

/*
    Check Contact Read Write Permission
*/
/*fun checkContactPermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
        }
    })
    return granted
}*/

/*
    Check Phone Call Permission
*/
/*fun checkPhonePermission(context: Context): Boolean {
    var granted = false
    Permissions.check(context, Manifest.permission.CALL_PHONE, null,
        object : PermissionHandler() {
            override fun onGranted() {
                granted = true
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                granted = false
            }
        })
    return granted
}*/

/*
    Check Record Audio Permission
*/
/*fun checkAudioPermission(context: Context): Boolean {
    var granted = false
    Permissions.check(context, Manifest.permission.RECORD_AUDIO, null,
        object : PermissionHandler() {
            override fun onGranted() {
                granted = true
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                granted = false
            }
        })
    return granted
}*/

/*
    Check Record Audio And Camera Permission For Video Call
*/
/*fun checkVideoCallPermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
        }
    })
    return granted
}*/

/*
    Check Record Audio Permission For Audio Call
*/
/*fun checkAudioCallPermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
        }
    })
    return granted
}*/

/*
    Check Access Coarse and Fine Location  Permission
*/
//Check Location Permission
/*fun checkLocationPermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
            super.onDenied(context, deniedPermissions)
        }
    })
    return granted
}*/

/*
    Check GPS Permission
*/
//Check Gps and Network Permission
fun checkGpsPermission(context: Context): Boolean {
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var gpsEnabled = false
    var networkEnabled = false
    try {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    try {
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }

    var gpsAndNetworkEnable = false
    if (gpsEnabled && networkEnabled) {
        gpsAndNetworkEnable = true
    }
    return gpsAndNetworkEnable
}
fun enableDeviceGPS(context: Context) {
    val alertDialog = AlertDialog.Builder(context).setMessage("GPS Enable")
        .setPositiveButton("Settings") { dialog, which ->
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        .setNegativeButton("Cancel") { dialog, which ->
            /* showToast("Please open your phone setting and enable your location", context)*/
        }
    alertDialog.show()
}


fun checkLocationPermission(context: Context): Boolean {
    var granted = false
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    com.nabinbhandari.android.permissions.Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            granted = true
        }
        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            granted = false
            super.onDenied(context, deniedPermissions)
        }
    })
    return granted
}
fun dateConvertIntoTimeStamp(dob: String): String {
    val formatter: java.text.DateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = formatter.parse(dob.replace(" ", "")) as Date
    return date.time.toString()
}
fun datePicker(context: Context, sendDOB: DayListener, startingTime: Long) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val dpd = DatePickerDialog(
        context,
        { view, mYear, mMonth, mDay ->
            val tempMonth = mMonth + 1
            if (tempMonth > 9) {
                if (mDay > 9)
                    sendDOB.getDate(
                        "$mDay/${tempMonth}/$mYear",
                        dateConvertIntoTimeStamp("$mDay/${tempMonth}/$mYear")
                    )
                else
                    sendDOB.getDate(
                        "0${mDay}/${tempMonth}/$mYear",
                        dateConvertIntoTimeStamp("0${mDay}/${tempMonth}/$mYear")
                    )
            } else {
                if (mDay > 9)
                    sendDOB.getDate(
                        "$mDay/0${tempMonth}/$mYear",
                        dateConvertIntoTimeStamp("$mDay/0${tempMonth}/$mYear")
                    )
                else
                    sendDOB.getDate(
                        "0${mDay}/0${tempMonth}/$mYear",
                        dateConvertIntoTimeStamp("0${mDay}/0${tempMonth}/$mYear")
                    )
            }
        },
        year,
        month,
        day
    )
    //dpd.datePicker.maxDate = System.currentTimeMillis()
    dpd.show()
}
fun checkGpsPermission2(context: Context): Boolean {
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var gpsEnabled = false
    var networkEnabled = false
    try {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    try {
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }

    var gpsAndNetworkEnable = false
    if (gpsEnabled && networkEnabled) {
        gpsAndNetworkEnable = true
    }
    return gpsAndNetworkEnable
}




/*
    Enable Device GPS
*/
/*fun enableDeviceGPS(context: Context) {
    val alertDialog = AlertDialog.Builder(context).setMessage("GPS Enable")
        .setPositiveButton("Settings") { dialog, which ->
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        .setNegativeButton("Cancel") { dialog, which ->
            showToast("Please open your phone setting and enable your location", context)
        }
    alertDialog.show()
}*/

/*
    Check Network Connected
*/
fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
}








/*
Firebase Regarding Function
*/

/*
    Set Firebase Database Reference
*/
/*fun setDatabaseRef(orderId: String): DatabaseReference {
    return FirebaseDatabase.getInstance().getReference(IntentConstant.ORDER).child(orderId)
}*/

/*
    Update Order Status on Firebase
*/
/*fun DatabaseReference.setOrderStatus(status: String) {
    this.child(IntentConstant.ORDER_STATUS).setValue(status)
}*/

/*
    Get Device Token
*/
/*fun getDeviceToken(sharedPreference: SharedPreferenceUtil) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener {
        if (it.isComplete) {
            val token = it.result.toString()
            sharedPreference.deviceToken = token
        }
    }
}*/


/*
Maths Function
*/

/*
    Floor Round of a Decimal Number
*/
fun roundOffDecimal(number: Double): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toDouble()
}


/*
Open Project Screen
*/

/*
    Start Home Screen With Clear Stack
*/
/*
fun Activity.homeScreen() {
    startActivity(Intent(this, HomeActivity::class.java))
    finishAffinity()
}
*/

/*
    Show Image in Full Screen From Activity
*/
/*fun Activity.showImage(url: String) {
    startActivity(
        Intent(this, FullScreenActivity::class.java)
            .putExtra(IntentConstant.IMAGE_URL, url)
    )
}*/

/*
    Show Image in Full Screen From Fragment
*/
/*fun Fragment.showImage(url: String) {
    startActivity(
        Intent(requireContext(), FullScreenActivity::class.java)
            .putExtra(IntentConstant.IMAGE_URL, url)
    )
}*/

/*
    Show Image Array in Full Screen From Activity
*/
fun Activity.showImageArray(position: Int, url: ArrayList<String>) {
    /*startActivity(
        Intent(this, FullScreenImageArrayActivity::class.java)
            .putExtra(IntentConstant.IMAGE_URL_ARRAY, url)
            .putExtra(IntentConstant.CURRENT_POSITION, position)
    )*/
}

/*
    Show Image Array in Full Screen From Fragment
*/
fun Fragment.showImageArray(position: Int, url: ArrayList<String>) {
    /*startActivity(
        Intent(requireContext(), FullScreenImageArrayActivity::class.java)
            .putExtra(IntentConstant.IMAGE_URL_ARRAY, url)
            .putExtra(IntentConstant.CURRENT_POSITION, position)
    )*/
}

/*
    Show Toast From Activity
*/
fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/*
    Show Toast From Fragment
*/
/*fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}*/

/*
    Show Toast From Dialog
*/
fun Dialog.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/*
    Show Long Toast From Activity
*/
fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/*
    Show Long Toast From Fragment
*/
fun Fragment.showLongToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

/*
    Show Long Toast From Dialog
*/
fun Dialog.showLongToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


/*
Device Function
*/

/*
    Number Copy to Device Call Pad from Activity
*/
/*fun Activity.makeACall(mobile: String) {
    if (checkPhonePermission(this)) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$mobile")
        startActivity(callIntent)
    }
}*/

/*
    Number Copy to Device Call Pad from Fragment
*/
fun Fragment.makeACall(mobile: String) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:$mobile")
    startActivity(callIntent)
}

/*
    Start Google Map from Activity
*/
fun Activity.startGoogleMap(lat: Double, long: Double) {
    val uri = "http://maps.google.com/maps?daddr=$lat,$long"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

/*
    Start Google Map from Fragment
*/
fun Fragment.startGoogleMap(lat: Double, long: Double) {
    val uri = "http://maps.google.com/maps?daddr=$lat,$long"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

/*
    Open Keyboard
*/
fun openKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInputFromWindow(
        activity.currentFocus!!.getApplicationWindowToken(),
        InputMethodManager.SHOW_FORCED, 0
    )
}

/*
    Hide Keyboard
*/
fun hideKeyboard(context: Context?) {
    if (context is Activity) {
        val focusedView = context.currentFocus
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            focusedView?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

/*
    Dismiss Keyboard
*/
fun dismissKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (null != activity.currentFocus)
        imm.hideSoftInputFromWindow(
            activity.currentFocus!!
                .applicationWindowToken, 0
        )
}

/*
    Show Full Screen Window
*/
fun fullScreenWindow(window: Window) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

/*
    Splash Screen Full Window
*/
fun Activity.statusBar() {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        } else {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusBarColor = Color.TRANSPARENT
        }
    }
}

/*
    Find Screen Width
*/
fun findScreenWidth(window: Window): Int {
    val disPlayMetric = DisplayMetrics()
    window.windowManager.defaultDisplay.getMetrics(disPlayMetric)
    return disPlayMetric.widthPixels
}

/*
    Set Locale of Android App
*/
fun Activity.setLocale(locale: Locale) {
    val configuration = resources.configuration
    val displayMetrics = resources.displayMetrics
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        configuration.setLocale(locale)
    } else {
        configuration.locale = locale
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val config = resources.configuration
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocales(LocaleList(locale))
        } else {
            config.locale = locale
        }
        baseContext.resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    } else {
        resources.updateConfiguration(configuration, displayMetrics)
    }
}


/*
MultiMedia Function
*/

/*
    Convert File Into MultipartBody.Part
*/
fun findMultipart(currentImage: File, type: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        type,
        currentImage.name,
        currentImage.asRequestBody("image/*".toMediaTypeOrNull())
    )
}

/*
    Retrieve Thumbnail form Video Url
*/
fun retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
    val bitmap: Bitmap?
    var mediaMetadataRetriever: MediaMetadataRetriever? = null
    try {
        mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(videoPath, HashMap())
        bitmap = mediaMetadataRetriever.frameAtTime
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        throw Throwable("Exception in retrieve thumbnail from video url" + e.message)
    } finally {
        mediaMetadataRetriever?.release()
    }
    return bitmap
}

/*
    Retrieve Audio Duration form Audio Url
*/
fun getAudioDuration(context: Context, filePath: String): Int {
    return try {
        val uri = Uri.parse(filePath)
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, uri)
        var durationStr = ""
        if (mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) != null) {
            durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
            mmr.release()
        }
        Integer.parseInt(durationStr)
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

/*
    Convert String Into RequestBody
*/
fun findRequestBody(string: String): RequestBody {
    return string.toRequestBody("text".toMediaTypeOrNull())
}


/*
String Function
*/

/*
    Convert String to Upper Word String
*/
fun String.toUpperWord(): String {
    return this.toUpperCase(Locale.getDefault())
}


/*
Map And Location Regarding Function
*/

/*
    Find Address From LatLng
*/
/*fun findAddressFromLatLng(
    context: Context,
    latLng: LatLng,
    addressFindListener: AddressFindListener
) {
    try {
        val addresses: List<Address>
        val geoCoder = Geocoder(context, Locale.getDefault())
        addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        val address = addresses[0].getAddressLine(0)
        addressFindListener.addressFind(address)
    } catch (e: Exception) {
        if (checkGpsPermission(context)) {
            if (checkLocationPermission(context)) {
                showToast(
                    context.getString(R.string.need_location_permission_for_fetch_current_location),
                    context
                )
            }
        } else {
            enableDeviceGPS(context)
        }
    }
}*/

/*
    Find Address Interface
*/
interface AddressFindListener {
    fun addressFind(address: String)
}

/*
    Find LatLng From Location
*/
/*fun Location.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}*/

/*
    Calculate Distance Between Two LatLng
*/
/*fun calculateDistance(startLatLng: LatLng, endLatLng: LatLng): Double {
    val radius = 6371// radius of earth in Km
    val startLat = startLatLng.latitude
    val startLong = startLatLng.longitude
    val endLat = endLatLng.latitude
    val endLong = endLatLng.longitude
    val latDifference = Math.toRadians(endLat - startLat)
    val longDifference = Math.toRadians(endLong - startLong)
    val a = sin(latDifference / 2) * sin(latDifference / 2) + (cos(Math.toRadians(startLat)) * cos(
        Math.toRadians(endLat)
    ) * sin(longDifference / 2) * sin(longDifference / 2))
    val c = 2 * asin(sqrt(a))
    val valueResult = radius * c
    return valueResult / 1
}*/

/*
    Calculate Distance Between Two LatLng in Meter
*/
fun calculateMeterDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
    val theta: Double = lon1 - lon2
    var dist = (Math.sin(deg2rad(lat1))
            * Math.sin(deg2rad(lat2))
            + (Math.cos(deg2rad(lat1))
            * Math.cos(deg2rad(lat2))
            * Math.cos(deg2rad(theta))))
    dist = Math.acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
    val meterDistance: Double = dist * 1609.34
    return meterDistance.toInt()
}



/*
    Degree to Radius
*/
private fun deg2rad(deg: Double): Double {
    return deg * Math.PI / 180.0
}

/*
    Radius to Degree
*/
private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / Math.PI
}


/*
    Show Message
*/

/*
    Convert File Into MultipartBody.Part
*/



/*
    Show Log
*/
fun showLog(message: String) {
    Log.e("###", message)
}

/*
    Show Toast
*/
/*fun showToast(message: OTPVerificatonActivity, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}*/



fun AppCompatActivity.statusBarTransparentWithWhite() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
}

/*fun compressImageFileA(context: Context?, pathUri: Uri): File {
    var b: Bitmap? = null

    var realPath: String? =
        getRealPathFromURI(context!!, pathUri)
    var f: File = File(realPath)

    //Decode image size
    var o: BitmapFactory.Options = BitmapFactory.Options()
    o.inJustDecodeBounds = true

    var fis: FileInputStream
    try {
        fis = FileInputStream(f)
        BitmapFactory.decodeStream(fis, null, o)
        fis.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }


    var scale = 1
    if (o.outHeight > AppConstants.IMAGE_MAX_SIZE || o.outWidth > AppConstants.IMAGE_MAX_SIZE) {
        scale = Math.pow(
            2.0,
            Math.ceil(
                Math.log(
                    AppConstants. IMAGE_MAX_SIZE / Math.max(
                        o.outHeight,
                        o.outWidth
                    ).toDouble()
                ) / Math.log(0.5)
            )
        ).toInt()
    }

    //Decode with inSampleSize
    var o2: BitmapFactory.Options = BitmapFactory.Options()
    o2.inSampleSize = scale
    try {
        fis = FileInputStream(f)
        b = BitmapFactory.decodeStream(fis, null, o2)
        fis.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    var destFile = File(getImageFilePath())
    try {
        var out: FileOutputStream = FileOutputStream(destFile)
        b?.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return destFile
}*/

/*fun getImageFilePath(): String {
    val file =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/RamClinicUser")
    if (!file.exists()) {
        file.mkdirs()
    }
    return file.absolutePath + "/IMG_" + System.currentTimeMillis() + ".jpg"
}*/

/*fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
    var contentUri = contentUri
    var cursor: Cursor?
    var filePath: String? = ""
    when (contentUri) {
        null -> return filePath
        else -> {
            val file = File(contentUri.path)
            when {
                file.exists() -> filePath = file.path
            }
            when {
                !TextUtils.isEmpty(filePath) -> return filePath
                else -> {
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                            try {
                                val wholeID = DocumentsContract.getDocumentId(contentUri)
                                val id: String
                                when {
                                    wholeID.contains(":") -> id =
                                        wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                                            .toTypedArray()[1]
                                    else -> id = wholeID
                                }
                                cursor = context.contentResolver.query(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    proj,
                                    MediaStore.Images.Media._ID + "='" + id + "'",
                                    null,
                                    null
                                )
                                when {
                                    cursor != null -> {
                                        val columnIndex = cursor.getColumnIndex(proj[0])
                                        when {
                                            cursor.moveToFirst() -> filePath =
                                                cursor.getString(columnIndex)
                                        }
                                        when {
                                            !TextUtils.isEmpty(filePath) -> contentUri =
                                                Uri.parse(filePath)
                                        }
                                    }
                                }
                            } catch (e: IllegalArgumentException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    when {
                        !TextUtils.isEmpty(filePath) -> return filePath
                        else -> {
                            try {
                                cursor = context.contentResolver.query(
                                    contentUri!!,
                                    proj,
                                    null,
                                    null,
                                    null
                                )
                                when {
                                    cursor == null -> return contentUri.path
                                    cursor.moveToFirst() -> filePath =
                                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                                }

                                when {
                                    !cursor!!.isClosed -> cursor.close()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                filePath = contentUri!!.path
                            }

                            when (filePath) {
                                null -> filePath = ""
                            }
                            return filePath
                        }
                    }

                }
            }

        }
    }

}*/




