package com.dubitaxi.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.dubitaxi.chooseCameraAndGallery.ChooseImageOrGalleryDialog
import com.dubitaxi.chooseCameraAndGallery.ChooseImageOrGalleryDialogListener


object ImageUtils { /*
        Choose Image Dialog
    */
    @SuppressLint("SuspiciousIndentation")
    fun Activity.chooseImageDialog(context: Context) {
        val chooseImageOrGalleryDialog = ChooseImageOrGalleryDialog(this, object :
                ChooseImageOrGalleryDialogListener {
                override fun cameraSelect() {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        (context as AppCompatActivity).startActivityForResult(
                            cameraIntent,
                            AppConstants.CAMERA
                        )
                }
                override fun gallerySelect() {
                    val galleryIntent = Intent(Intent.ACTION_PICK)
                        galleryIntent.type = "image/*"
                        (context as AppCompatActivity).startActivityForResult(
                            galleryIntent,
                            AppConstants.GALLERY
                        )

                }
            })
            chooseImageOrGalleryDialog.show()
        }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, context: Context?, mListener: ImageListener) {
        if (requestCode == AppConstants.GALLERY && resultCode == RESULT_OK) {
            val imageuri = data?.data
            val file = compressImageFile(context, imageuri!!)
            mListener.getImageData(imageuri, null, file)

        } else if (requestCode == AppConstants.CAMERA && resultCode == RESULT_OK) {
            val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
            val imageuri = (context as AppCompatActivity).getImageUri(bitmap)
            val file = compressImageFile(context, imageuri)
            mListener.getImageData(imageuri, bitmap, file)
        }

    }
}