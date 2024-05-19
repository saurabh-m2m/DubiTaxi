package com.dubitaxi.ui.selectUserType.driverSignUp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.vo.DriverGetVehicleResponse

class ShowImageAdapter(
    val context: Context,
    val mShowImage: ArrayList<String>
) : RecyclerView.Adapter<ShowImageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.show_image_item, parent, false)
        return ShowImageAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val image = findViewById<ImageView>(R.id.mShowImage)
            Glide.with(context).load(mShowImage[position]).into(image)
        }
    }

    override fun getItemCount(): Int {
        return mShowImage.size
    }
}