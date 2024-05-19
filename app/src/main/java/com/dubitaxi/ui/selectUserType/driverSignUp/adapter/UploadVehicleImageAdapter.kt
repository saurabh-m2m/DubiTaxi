package com.dubitaxi.ui.selectUserType.driverSignUp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubitaxi.R

class UploadVehicleImageAdapter(var context: Context, var list: ArrayList<ImageUpdateModal>,var checkstatus:Int, var mImageClick: mCancleImageClick) :
    RecyclerView.Adapter<UploadVehicleImageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.uploaded_vehivle_image_item, parent, false)
        return UploadVehicleImageAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val mImage = findViewById<ImageView>(R.id.mVehicleImage)
            val mImageClose = findViewById<ImageView>(R.id.mVehicleImage)

            if (list.get(position).selectstatus==0) {

                mImage.setImageURI(list.get(position).imagename.toUri())

            }else if (list.get(position).selectstatus==1){

                Glide.with(this).load(list.get(position).imagename).into(mImage)
            }
            mImageClose.setOnClickListener {
                mImageClick.imageCancle(position,list.get(position).deletesataus)
                list.removeAt(position)
                notifyDataSetChanged()

            }
        }
    }

    interface mCancleImageClick {
        fun imageCancle(position: Int,status:Int)
    }
}