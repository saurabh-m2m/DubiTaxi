package com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.passengerSignUp.adapter.SavedAddressAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.setLocation.vo.SearchLocationResponse

class LocationAdapter(
    private val predictionsList: ArrayList<SearchLocationResponse.Predictions>,
    val myInterface: MyInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var secondary_text:String=""
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class ViewHolderSaved(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return SavedAddressAdapter.ViewHolder(view)

    }

    override fun getItemViewType(position: Int): Int {
        return predictionsList.size
    }

    override fun getItemCount(): Int {
        return predictionsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            val TvVistaTitle = findViewById<TextView>(R.id.tvAddress)
            val tvState = findViewById<TextView>(R.id.tvState)
            val prediction = predictionsList[position]
            val main_text = prediction.structured_formatting.main_text
            TvVistaTitle.text = main_text
            if (prediction.structured_formatting.secondary_text!=null) {
                  secondary_text = prediction.structured_formatting.secondary_text
                  tvState.text = secondary_text
            }
            setOnClickListener {
                myInterface.onClick(holder.adapterPosition, "$main_text $secondary_text")
            }


        }
    }


interface MyInterface {
    fun onClick(position: Int, name: String)
}
    }
