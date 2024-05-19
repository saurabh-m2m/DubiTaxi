package com.dubitaxi.utils

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ImageListener {
    fun getImageData(uri: Uri?, bm: Bitmap?, file: File?)
}