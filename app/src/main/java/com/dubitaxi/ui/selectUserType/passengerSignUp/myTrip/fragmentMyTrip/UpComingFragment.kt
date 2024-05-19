package com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragmentMyTrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubitaxi.databinding.FragmentUpComingBinding
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.MyTripActivity
import com.dubitaxi.ui.selectUserType.passengerSignUp.myTrip.fragAdpter.MyTripFragmentAdapter
import com.fluper.tiacabapp.base.BaseFragment

class UpComingFragment : BaseFragment(),View.OnClickListener, MyTripFragmentAdapter.ViewDetails {
    private var _binding: FragmentUpComingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpComingBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInitView()
        mInitControl()
        mObserver()
    }

    override fun mInitView() {
        binding.recyclerUpComing.adapter = MyTripFragmentAdapter(Activity(), this)
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


    }

}