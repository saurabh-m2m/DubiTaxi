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
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.UserRecentilyAddressResponse

class RecentPickupPlaceAdapter(
    var context: Context,
    var mUserRecentlyAddressList: ArrayList<UserRecentilyAddressResponse.Response.Result>,
    var mUserRecentltyAddressRecyclerView:mRecentPickLocation,
    var type:String = "0"
) : RecyclerView.Adapter<RecentPickupPlaceAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_pickup, parent, false)
        return RecentPickupPlaceAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUserRecentlyAddressList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mUserRecentlyAddressList.size == 0) {

        } else {
            var mUserRecentltyAddress = mUserRecentlyAddressList[position]
            holder.itemView.apply {
                val TvVistaTitle = findViewById<TextView>(R.id.TvVistaRecentily)
                val TvVistaRecentily = findViewById<TextView>(R.id.tvVistaLocationRecentily)
                val mConstraintPick = findViewById<ConstraintLayout>(R.id.constraintPick)
                val mHomeImage = findViewById<ImageView>(R.id.ivVistaImage)
                TvVistaTitle.text = mUserRecentlyAddressList[position]?.addressName
                TvVistaRecentily.text = mUserRecentlyAddressList[position]?.officialName
                if (mUserRecentlyAddressList[position].addressType.toString() == "0") {
                    TvVistaTitle.text = "Home"
                    mHomeImage.setImageResource(R.drawable.home_ic)
                } else if (mUserRecentlyAddressList[position].addressType.toString() == "1") {
                    TvVistaTitle.text = "Work"
                    mHomeImage.setImageResource(R.drawable.work_active_ic)
                } else if (mUserRecentlyAddressList[position].addressType.toString() == "2") {
                    TvVistaTitle.text = mUserRecentlyAddressList[position].addressName
                    mHomeImage.setImageResource(R.drawable.other_active)
                }
                mConstraintPick.setOnClickListener {
                    mUserRecentltyAddressRecyclerView.mRecentAddressClick(position, mUserRecentltyAddress, type)
                }
            }

        }

    }
    interface mRecentPickLocation{
        fun mRecentAddressClick(position: Int,mRecentAddressList:UserRecentilyAddressResponse.Response.Result,type:String)
    }
}
