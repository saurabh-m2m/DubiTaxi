package com.dubitaxi.chooseCameraAndGallery

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.dubitaxi.R


class ChooseImageOrGalleryDialog(
    context: Context,
    private val chooseImageOrGalleryDialogListener: ChooseImageOrGalleryDialogListener
) : Dialog(context) {

    /*
        On Create
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialogue_imager_chooser)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window!!.setGravity(Gravity.CENTER)

        initViews()
        initControls()
    }

    /*
        All Initialization Here
    */
    private fun initViews() {

    }

    /*
        All Controls Defines Here
    */
    private fun initControls() {
        val mCm = findViewById<ImageView>(R.id.btnCamera)
        val mGl = findViewById<ImageView>(R.id.btnGallery)
        mCm.setOnClickListener {
            chooseImageOrGalleryDialogListener.cameraSelect()
            dismiss()
        }

        mGl.setOnClickListener {
            chooseImageOrGalleryDialogListener.gallerySelect()
            dismiss()
        }
    }
}

/*
    Choose Image Or Gallery Dialog Interface
*/
interface ChooseImageOrGalleryDialogListener {
    fun cameraSelect()
    fun gallerySelect()
}