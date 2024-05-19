package com.dubitaxi.ui.selectUserType.passengerSignUp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.passengerSignUp.vo.GetBannerResponse

class ViewPagerBannerAdapter(var context: Context,
    var bannerList: ArrayList<GetBannerResponse.Response.Result>
    ):RecyclerView.Adapter<ViewPagerBannerAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_slider,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val mBannerImage=findViewById<ImageView>(R.id.ivSlider)
            Glide.with(this).load(bannerList[position].image).into(mBannerImage)
        }
    }
}