package com.dubitaxi.ui.selectUserType.passengerSignUp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.passengerSignUp.vehicleList.GetVehicleTypeDataResponse

class VehicleListAdapter(
    var context: Context,
    var getVehicleList: ArrayList<GetVehicleTypeDataResponse.Response.Result>,
    var RideVehicle: selectVehicle
) : RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {
    var index: Int? = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_choose_vehicle, parent, false)
        return VehicleListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return getVehicleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val selectVehicle = findViewById<ConstraintLayout>(R.id.ccSelectVehicle)
            val vehicleName = findViewById<TextView>(R.id.tvDubiPrime)
            val vehicleTime = findViewById<TextView>(R.id.ivUserCount)
            val vehicleColor = findViewById<TextView>(R.id.tvWhitecar)
            val vehicleDoller = findViewById<TextView>(R.id.tvDollerCar)
            val vehicleImage = findViewById<ImageView>(R.id.ivCar)
            if (index == position) {
                holder.itemView.isSelected = true
              //  selectVehicle.setBackgroundColor(Color.parseColor("F1F6FF"))
                selectVehicle.setBackgroundResource(R.drawable.vehicle_select_bg)
            } else {
                holder.itemView.isSelected = false
               selectVehicle.setBackgroundResource(R.drawable.bg_language)
            }
            selectVehicle.setOnClickListener {
                RideVehicle.selectRide(
                    position = position,
                    price = getVehicleList[position].fareDetails?.amount ?: 0,
                    id = getVehicleList[position].id?:""

                )
                index = position
                notifyDataSetChanged()
            }
            vehicleName.text = getVehicleList[position].description
            vehicleDoller.text = "€ " + getVehicleList[position].fareDetails?.amount
            vehicleTime.text = getVehicleList[position].nearbyDriver?.time + " Min"
            Glide.with(this).load(getVehicleList[position].icon).placeholder(R.drawable.car)
                .into(vehicleImage)
            // vehicleColor.text = getVehicleList[position].nearbyDriver?.time+" Min"

        }


    }

    interface selectVehicle {
        fun selectRide(position: Int, price: Int, id: String)


    }
}