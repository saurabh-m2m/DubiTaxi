package com.dubitaxi.ui.selectUserType.driverSignUp.subscription

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dubitaxi.R
import com.dubitaxi.ui.selectUserType.driverSignUp.subscription.vo.GetSubscriptionPlansResponse

class AdapterSubscription(
    val context: Context,


    var mGetSubscriptionList: ArrayList<GetSubscriptionPlansResponse.Response.Plan>,
    var mPlanSelectedRecyclerView:mSelectSubscriptionPlan
) :
    RecyclerView.Adapter<AdapterSubscription.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
    var index: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_subscription, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGetSubscriptionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val mPlanName = findViewById<TextView>(R.id.tvBASICPLAN)
            val mPlanMonths = findViewById<TextView>(R.id.tvOneDay)
            val mPlanHours = findViewById<TextView>(R.id.tvHours)
            val mPlanPrice = findViewById<TextView>(R.id.mPlanPrice)
            val mPlanServiceType = findViewById<TextView>(R.id.mPlanServiceType)
            val mPlanVehicleType = findViewById<TextView>(R.id.mPlanVehicleType)
            val mPlanLayout = findViewById<ConstraintLayout>(R.id.ccMain)

            mPlanName.text = mGetSubscriptionList[position].planName
            mPlanMonths.text = mGetSubscriptionList[position].duration
            mPlanHours.text = mGetSubscriptionList[position].activeHours.toString() + "Hours"
            mPlanPrice.text = "€" + mGetSubscriptionList[position].charges
             mPlanServiceType.text = mGetSubscriptionList[position].serviceName.toString()
            mPlanVehicleType.text = mGetSubscriptionList[position].vehicleType?.vehicleType
            if (index == position) {
                mPlanLayout.setBackgroundResource(R.drawable.yellow_border)
            } else {
                mPlanLayout.setBackgroundResource(R.drawable.withe_bg)
            }

            mPlanLayout.setOnClickListener {
                mPlanSelectedRecyclerView.mPlanSelected(mGetSubscriptionList[position].id.toString(),position)
                index = position
                notifyDataSetChanged()
            }

        }

    }

    interface mSelectSubscriptionPlan {
        fun mPlanSelected(id: String, position: Int)
    }
}