package com.dubitaxi.ui.SplashScreen

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dubitaxi.R
import com.dubitaxi.base.BaseActivity
import com.dubitaxi.ui.selectUserType.SelectUserTypeActivity
import com.dubitaxi.ui.selectUserType.language.LanguageActivity
import com.dubitaxi.utils.ImageUtils.chooseImageDialog
import com.dubitaxi.utils.statusBar
import com.dubitaxi.utils.statusBarTransparent

class SplashActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
        statusBarTransparent()

    }

    override fun initView() {
        getExternalStoragePermission()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LanguageActivity::class.java))
            finish()
        }, 3000)
    }

    override fun initControl() {

    }

    override fun mObserver() {

    }

    //permission

    private fun getExternalStoragePermission() {
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        val requestCode = 101

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                requestCode
            )

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Can ask the user for permission again
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                val userAskedPermissionBefore = false

                if (!userAskedPermissionBefore) {
                    // If the user was asked for permission before and denied
                    val alertDialogBuilder = AlertDialog.Builder(this)

                    alertDialogBuilder.setTitle("Permission needed")
                    alertDialogBuilder.setMessage("Location permission is required")
                    alertDialogBuilder.setPositiveButton("Open Settings") { dialogInterface, _ ->
                        val intent = Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", packageName, null)
                            data = uri
                        }
                        startActivity(intent)
                    }
                } else {
                    // If the user is asked for permission for the first time
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(permission),
                        requestCode
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            103->{
                if (Build.VERSION.SDK_INT < 33) {
                    if (ActivityCompat.checkSelfPermission(
                            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(
                            this, Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(
                            this, Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED

                    ) {

                        requestPermissions(
                            arrayOf(
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.CAMERA,
                            ), 103
                        )
                    } else {
                        chooseImageDialog(this)
                    }
                } else {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_MEDIA_IMAGES
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        requestPermissions(
                            arrayOf(
                                android.Manifest.permission.READ_MEDIA_IMAGES,
                                android.Manifest.permission.CAMERA,
                            ), 103
                        )
                    } else {
                       // chooseImageDialog(this)
                    }

                }
            }
        }
    }




    override fun onClick(v0: View?) {

    }

}
