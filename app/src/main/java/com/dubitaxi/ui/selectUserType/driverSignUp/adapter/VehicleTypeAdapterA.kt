package com.dubitaxi.ui.selectUserType.driverSignUp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.driverSignUp.addVehicle.vo.GetParticularVehicleResponse


class VehicleTypeAdapterA(
    context: Context,
    var mGetTypeVehiclesList: ArrayList<GetParticularVehicleResponse.Response.Result.AllOtherVehicleType>,
    var mPosition: Int = -1,
    var mVehicleItem: mShowVehicleData?,

    ) : RecyclerView.Adapter<VehicleTypeAdapterA.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.vehicle_service_type_item, parent, false)
        return VehicleTypeAdapterA.ViewHolder(view)
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
                mVehicleItem?.mVehicleItem(mGetTypeVehiclesList[position].id, position)
                mPosition = position
                notifyDataSetChanged()
            }
        }

    }

    interface mShowVehicleData {
        fun mVehicleItem(id: String, position: Int)
    }
}