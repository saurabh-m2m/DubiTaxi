package com.dubitaxi.ui.selectUserType.passengerSignUp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragmentMyTrip.OnGoingFragment
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragmentMyTrip.PastFragment
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragmentMyTrip.UpComingFragment
import org.w3c.dom.Text

class MyTripPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                OnGoingFragment()
            }

            1 -> {
                UpComingFragment()
            }

            2 -> {
                PastFragment()
            }

            else -> {
                OnGoingFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "On-going"
            }
            1 -> {
                return "Up-coming"
            }
            2 -> {
                return "Past"
            }
        }
        return super.getPageTitle(position)
    }

}
