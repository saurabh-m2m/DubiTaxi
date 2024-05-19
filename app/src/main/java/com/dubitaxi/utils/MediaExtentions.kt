
package com.dubitaxi.utils
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dubitaxi.utils.AppConstants.Companion.IMAGE_MAX_SIZE


import java.io.*

fun AppCompatActivity.getImageUri(inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        this.contentResolver, inImage,
        "IMG_"+System.currentTimeMillis(), null
    )
    return Uri.parse(path)
}

fun Fragment.getImageUri(inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        context!!.contentResolver, inImage,
        "IMG_"+System.currentTimeMillis(), null
    )
    return Uri.parse(path)
}

fun compressImageFile(context: Context?, pathUri: Uri): File {
    var b: Bitmap? = null

    val realPath: String? =
        getRealPathFromURI(context!!, pathUri)
    val f: File = File(realPath)

    //Decode image size
    val o: BitmapFactory.Options = BitmapFactory.Options()
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
    if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
        scale = Math.pow(
            2.0,
            Math.ceil(
                Math.log(
                    IMAGE_MAX_SIZE / Math.max(
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
}

fun getImageFilePath(): String {
    val file =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/CareerPortalApp")
    if (!file.exists()) {
        file.mkdirs()
    }
    return file.absolutePath + "/IMG_" + System.currentTimeMillis() + ".jpg"
}

fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
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

}


fun getVideoThumbnails(context: Context, videoUri: Uri): java.util.ArrayList<Bitmap> {

    val thumbnailList = java.util.ArrayList<Bitmap>()

    try {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, videoUri)

        // Retrieve media data
        val videoLengthInMs = (Integer.parseInt(
            mediaMetadataRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_DURATION
            )
        ) * 1000).toLong()

        // Set thumbnail properties (Thumbs are squares)
        val thumbWidth = 512
        val thumbHeight = 512

        val numThumbs = 10

        val interval = videoLengthInMs / numThumbs

        for (i in 0 until numThumbs) {
            var bitmap = mediaMetadataRetriever.getFrameAtTime(
                i * interval,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
            )
            try {
                bitmap = Bitmap.createScaledBitmap(bitmap!!, thumbWidth, thumbHeight, false)
            } catch (e: Exception) {
                e.printStackTrace()
                return thumbnailList
            }
            thumbnailList.add(bitmap)
        }

        mediaMetadataRetriever.release()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return thumbnailList
}

@Throws(Throwable::class)
fun retriveVideoFrameFromVideo(videoPath: String): Bitmap? {
    var bitmap: Bitmap? = null
    var mediaMetadataRetriever: MediaMetadataRetriever? = null
    try {
        mediaMetadataRetriever = MediaMetadataRetriever()
        if (Build.VERSION.SDK_INT >= 14)
            mediaMetadataRetriever.setDataSource(videoPath, java.util.HashMap())
        else
            mediaMetadataRetriever.setDataSource(videoPath)
        //   mediaMetadataRetriever.setDataSource(videoPath);
        bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
    } catch (e: Exception) {
        e.printStackTrace()
        throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
    } finally {
        mediaMetadataRetriever?.release()
    }
    return bitmap
}


fun ImageView.loadImage(
    imageUrl: Any?,
    errorResId: Int?
) {


    if (imageUrl == null) return
    if (errorResId == null) return


}
