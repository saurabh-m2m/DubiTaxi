package com.dubitaxi.ui.selectUserType.driverSignUp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo.GetVehicleTypeListResponse


class VehicleTypeAdapter(
    context: Context,
    var mGetTypeVehiclesList: ArrayList<GetVehicleTypeListResponse.Response.Result>,
    var mVehicleItem: mShowVehicleData?,
    var mPosition: Int = -1
) : RecyclerView.Adapter<VehicleTypeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.vehicle_service_type_item, parent, false)
        return VehicleTypeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGetTypeVehiclesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val mVehicleTypeCheckBox = findViewById<CheckBox>(R.id.mVehicleTypeCheckBox)
            val TypeName = findViewById<TextView>(R.id.mTypeName)
            TypeName.text = mGetTypeVehiclesList[position].vehicleType
            if (mPosition == position) {
                mVehicleTypeCheckBox.isChecked = true
            } else {
                mVehicleTypeCheckBox.isChecked = false
            }
            mVehicleTypeCheckBox.setOnClickListener {
                mVehicleItem?.mVehicleItem(mGetTypeVehiclesList[position].id.toString(), position)
                mPosition = position
                notifyDataSetChanged()
            }
        }

    }

    interface mShowVehicleData {
        fun mVehicleItem(id: String, position: Int)
    }
}