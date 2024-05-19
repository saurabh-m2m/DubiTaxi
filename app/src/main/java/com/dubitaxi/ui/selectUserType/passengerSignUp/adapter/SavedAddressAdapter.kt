package com.dubitaxi.ui.selectUserType.passengerSignUp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R

class SavedAddressAdapter(var context: Context):RecyclerView.Adapter<SavedAddressAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.saved_address_item,parent,false)
        return SavedAddressAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}