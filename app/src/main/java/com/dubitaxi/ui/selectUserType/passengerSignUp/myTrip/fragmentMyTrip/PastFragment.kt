package com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragmentMyTrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubitaxi.databinding.FragmentPastBinding
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.MyTripActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragAdpter.MyTripFragmentAdapter
import com.dubitaxi.utils.showToast
import com.fluper.tiacabapp.base.BaseFragment


class PastFragment : BaseFragment(),View.OnClickListener, MyTripFragmentAdapter.ViewDetails {
    private var _binding:FragmentPastBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPastBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInitView()
        mInitControl()
        mObserver()
    }

    override fun mInitView() {
binding.recyclerPast.adapter = MyTripFragmentAdapter(Activity(), this)
    }

    override fun mInitControl() {

    }

    override fun mObserver() {

    }
    override fun onClick(v: View?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun mViewDetails() {
        binding.recyclerPast.setOnClickListener {

        }
    }
}