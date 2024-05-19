package com.dubitaxi.ui.selectUserType.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R


class NewTripRequestAdapter(
    val context: Context,
    /*val mTripResult: ArrayList<String>*/
) : RecyclerView.Adapter<NewTripRequestAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_new_trip_request, parent, false)
        return NewTripRequestAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}