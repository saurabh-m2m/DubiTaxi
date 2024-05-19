package com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragAdpter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.MyTripActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.detailing.MyTripDetailingActivity


class MyTripFragmentAdapter(var context: Context, val viewDetail: ViewDetails): RecyclerView.Adapter<MyTripFragmentAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_past,parent,false)
        return MyTripFragmentAdapter.ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val click = findViewById<TextView>(R.id.btnRateReviewPast)
            click.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, MyTripDetailingActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    interface ViewDetails{
        fun mViewDetails()


    }
}