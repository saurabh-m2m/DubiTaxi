package com.dubitaxi.ui.selectUserType.passengerSignUp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo.GetSavedAddressResponse

class ChoosePlaceAdapter(var context: Context, var listenerSelect: onAddressSelection, var mGetAddressList: ArrayList<GetSavedAddressResponse.Response.Result>
) : RecyclerView.Adapter<ChoosePlaceAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_choose_place, parent, false)
        return ChoosePlaceAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGetAddressList.size
    }

    override fun onBindViewHolder(holder: ChoosePlaceAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            val mTvVista = findViewById<TextView>(R.id.TvVista)
            val mtvVistaLocation = findViewById<TextView>(R.id.tvVistaLocation)
            val ivVistaImage = findViewById<ImageView>(R.id.ivVista)
            mTvVista.text = mGetAddressList[position].addressName
            mtvVistaLocation.text = mGetAddressList[position].officialName
            val constraintChoosePlace = findViewById<ConstraintLayout>(R.id.constraintChoosePlace)
            if (mGetAddressList[position].addressType.toString() == "0") {
                mTvVista.text = "Home"
                ivVistaImage.setImageResource(R.drawable.home_ic)
            } else if (mGetAddressList[position].addressType.toString() == "1") {
                mTvVista.text = "Work"
                ivVistaImage.setImageResource(R.drawable.work_active_ic)
            } else if (mGetAddressList[position].addressType.toString() == "2") {
                mTvVista.text = mGetAddressList[position].addressName
                ivVistaImage.setImageResource(R.drawable.other_active)
            }
            constraintChoosePlace.setOnClickListener {
                listenerSelect.onSelectAddress(
                    mGetAddressList[position].officialName,
                    mGetAddressList[position].addressName,
                    mGetAddressList[position].lat.toDouble(),
                    mGetAddressList[position].long.toDouble(),
                    mGetAddressList[position].id.toString(),
                )
            }
        }
    }

    interface onAddressSelection {
        fun onSelectAddress(officialName: String, addressName: String, lat: Double, long: Double, id: String)
    }
}