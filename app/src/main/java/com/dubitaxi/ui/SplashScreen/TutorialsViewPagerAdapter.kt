package com.dubitaxi.ui.SplashScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R

import com.dubitaxi.ui.SplashScreen.vo.ImageModel
import kotlinx.coroutines.NonDisposableHandle.parent


class TutorialsViewPagerAdapter(private var images: List<ImageModel>) :
    RecyclerView.Adapter<TutorialsViewPagerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.apply {
            val lottiiii=findViewById<ImageView>(R.id.lottiiii)
            val mTitleA=findViewById<TextView>(R.id.mTitleA)
            val mTitle=findViewById<TextView>(R.id.mTitle)
            lottiiii.setImageResource(images[position].images)
            mTitleA.text = images[position].text.toString()
            mTitle.text = images[position].Title

        }
    }

    override fun getItemCount(): Int = images.size

    class MyViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_tutorial_gif_layout,
                parent, false
            )
        )
    }
}