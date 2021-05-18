package com.basecamp.campong.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.basecamp.campong.RentalRecyclerAdapter
import com.basecamp.campong.databinding.FragmentRentalDetailBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants

class RentalState3Fragment : Fragment() {

    private var mBinding: FragmentRentalDetailBinding? = null
    private lateinit var mAdapter: RentalRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalDetailBinding.inflate(inflater, container, false)

        mBinding = binding

        mAdapter = RentalRecyclerAdapter()
        setRentalList(3)

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun setRentalList(state: Int) {
        RetrofitManager.instance.requestReserveList(state) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(
                            Constants.TAG,
                            "RentalState3Fragment - setList() : data is not null!!"
                        )
                        mAdapter.setList(data)
                    } else {
                        Log.d(Constants.TAG, "RentalState3Fragment - setList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "RentalState3Fragment - setList() : 통신 실패")
                }
            }
        }
    }
}