package com.dubitaxi.ui.selectUserType.driverSignUp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.driverSignUp.driverDetails.vo.DriverGetVehicleResponse


class VehicleDetailsAdapter(
    context: Context,
    var mGetDriverVehiclesList: ArrayList<DriverGetVehicleResponse.Response.Result>,
    var mVehicleItem: mShowVehicleData
) : RecyclerView.Adapter<VehicleDetailsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vehicle_layout, parent, false)
        return VehicleDetailsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGetDriverVehiclesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val mImage = findViewById<ImageView>(R.id.mImageA)
            val mTvmodelName = findViewById<TextView>(R.id.tvvihicleType2)
            val mTvtaxiNumber = findViewById<TextView>(R.id.tvtaxiNumber)
            val mTvcolorName = findViewById<TextView>(R.id.tvcolorName)
            val mVehicleClick = findViewById<ConstraintLayout>(R.id.mVehivleItem)
            val mSetCount = findViewById<TextView>(R.id.img_seatNumber)
            var mVehicleType = findViewById<TextView>(R.id.tvmodelName)
            Glide.with(context).load(mGetDriverVehiclesList[position].images.get(0)).into(mImage)
            mTvmodelName.text = mGetDriverVehiclesList[position].modelName
            mTvtaxiNumber.text = mGetDriverVehiclesList[position].vehicleNumber
            mTvcolorName.text = mGetDriverVehiclesList[position].color
            mSetCount.text = mGetDriverVehiclesList[position].seatsAvailable
            mVehicleType.text = mGetDriverVehiclesList[position].vehicle_type_name
            mVehicleClick.setOnClickListener {
                mVehicleItem.mVehicleItem(mGetDriverVehiclesList[position], position)
            }
        }

    }

    interface mShowVehicleData {
        fun mVehicleItem(data: DriverGetVehicleResponse.Response.Result, position: Int)
    }
}