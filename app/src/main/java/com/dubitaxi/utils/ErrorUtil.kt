package com.dubitaxi.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.dubitaxi.R
import com.dubitaxi.utils.GsonUtil.getGsonInstance
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {
    private val tag = ErrorUtil::class.simpleName

    fun handlerGeneralError(context: Context?, throwable: Throwable) {
        throwable.printStackTrace()

        if (context == null) return

        when (throwable) {
            //For Display Toast
            is ConnectException -> Toast.makeText(
                context,
                context.getString(R.string.network_error_please_try_later),
                Toast.LENGTH_SHORT
            ).show()

            is SocketTimeoutException -> Toast.makeText(
                context,
                context.getString(R.string.connection_lost_please_try_later),
                Toast.LENGTH_SHORT
            ).show()

            is UnknownHostException, is InternalError -> Toast.makeText(
                context,
                context.getString(R.string.server_error_please_try_later),
                Toast.LENGTH_SHORT
            ).show()

            is HttpException -> {
                try {
                    when (throwable.code()) {
                        401 -> {
                            //Logout
                            forceLogout(context)
                        }
                        500 -> {
                            //Logout
                            displayError(context, throwable)
                        }
                        403 -> {
                            displayError(context, throwable)
                        }
                        else -> {
                            displayError(context, throwable)
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("error", exception.toString())
                }
            }
            else -> {
                // Log.e("error",exception.toString())
            }
        }
    }

    // Perform logout for both the success and error case (force logout)
    private fun forceLogout(context: Context) {
        SharedPreferenceUtil.getInstance(context).deletePreferences()
        context.apply {
            Toast.makeText(
                context, getString(R.string.please_enter_account_name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun displayError(context: Context, exception: HttpException) {
        try {
            val errorBody = getGsonInstance().fromJson(
                exception.response()!!.errorBody()?.charStream(),
                ErrorBean::class.java
            )
            Toast.makeText(context, errorBody.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {

        }
    }
    fun showErrorMessage(message:String, context:Context){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.setContentView(R.layout.dialog_error_message)
       /* dialog.btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.tvMessage.text = message*/
        dialog.show()
    }
}