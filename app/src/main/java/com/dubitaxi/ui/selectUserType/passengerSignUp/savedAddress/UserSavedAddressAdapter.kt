package com.dubitaxi.ui.selectUserType.passengerSignUp.savedAddress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.passengerSignUp.choosePlace.vo.GetSavedAddressResponse

class UserSavedAddressAdapter(
    var context: Context,
    var mUserSavedGetAddressList: ArrayList<GetSavedAddressResponse.Response.Result>,
    var mUserEditAddress: mEditAddress
) : RecyclerView.Adapter<UserSavedAddressAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_saved_address_item, parent, false)
        return UserSavedAddressAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUserSavedGetAddressList.size
    }

    override fun onBindViewHolder(holder: UserSavedAddressAdapter.ViewHolder, position: Int) {
        var mAddressList = mUserSavedGetAddressList[position]
        holder.itemView.apply {
            val mTvVista = findViewById<TextView>(R.id.UserTitle)
            val mtvVistaLocation = findViewById<TextView>(R.id.mUserAddress)
            val mSavedAddressImageImage = findViewById<ImageView>(R.id.mSavedAddressImage)
            val menu = findViewById<ImageView>(R.id.menu)
            mTvVista.text = mUserSavedGetAddressList[position].addressName
            mtvVistaLocation.text = mUserSavedGetAddressList[position].officialName
            var constraintChoosePlace = findViewById<ConstraintLayout>(R.id.constraintChoosePlace)

            if (mUserSavedGetAddressList[position].addressType.toString() == "0") {
                mTvVista.text = "Home"
                mSavedAddressImageImage.setImageResource(R.drawable.home_ic)

            } else if (mUserSavedGetAddressList[position].addressType.toString() == "1") {
                mTvVista.text = "Work"
                mSavedAddressImageImage.setImageResource(R.drawable.work_active_ic)
            } else if (mUserSavedGetAddressList[position].addressType.toString() == "2") {
                mTvVista.text = mUserSavedGetAddressList[position].addressName
                mSavedAddressImageImage.setImageResource(R.drawable.other_active)
            }
            menu.setOnClickListener {
                val popup = PopupMenu(context, menu)
                val list: ArrayList<String> = arrayListOf("Edit", "Remove")
                for (element in list) {
                    popup.menu.add(element)
                }
                popup.setOnMenuItemClickListener { item ->
                    mUserEditAddress.mUserEditAddress(position, item.title.toString(), mAddressList)
                    true
                }
                popup.show()
            }
        }
    }

    interface mEditAddress {
        fun mUserEditAddress(
            position: Int,
            listener: String,
            mAddressList: GetSavedAddressResponse.Response.Result? = null
        )
    }
}