package com.dubitaxi.utils
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun AppCompatActivity.showToast(message:String)
{
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.moveActivity(activity: Activity)
{
    val i = Intent(this, activity::class.java)
    startActivity(i)
}

fun Context.showToastContext(message:String)
{
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun Fragment.showToast(name:String)
{
    Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
}

