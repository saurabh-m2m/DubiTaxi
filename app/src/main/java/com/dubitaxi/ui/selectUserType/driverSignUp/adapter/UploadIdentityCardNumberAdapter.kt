package com.dubitaxi.ui.selectUserType.driverSignUp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.utils.setImage

class UploadIdentityCardNumberAdapter(
    var context: Context,
    var list: ArrayList<Uri>,
    var mImageClick: mCancleImageClick
) :
    RecyclerView.Adapter<UploadIdentityCardNumberAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.uploaded_identiy_card_item, parent, false)
        return UploadIdentityCardNumberAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list.size < 2) {
            return list.size
        } else {
            return 2
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val mImage = findViewById<ImageView>(R.id.mImage)
            val mImageClose = findViewById<ImageView>(R.id.mImageClose)
            mImage.setImage(list[position])

            mImageClose.setOnClickListener {
                mImageClick.imageCancle(position)
                list.removeAt(position)
                notifyDataSetChanged()
            }

        }
    }

    interface mCancleImageClick {
        fun imageCancle(position: Int)
    }
}