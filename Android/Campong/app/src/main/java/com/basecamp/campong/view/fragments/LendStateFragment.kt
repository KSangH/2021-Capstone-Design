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

class LendStateFragment(val state: Int) : Fragment() {

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
        setLendList(state)

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

    private fun setLendList(state: Int) {
        RetrofitManager.instance.requestReserveMyList(state) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(
                            Constants.TAG,
                            "LendStateFragment - setList() : data is not null!!"
                        )
                        mAdapter.setList(data)
                    } else {
                        Log.d(Constants.TAG, "LendStateFragment - setList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "LendStateFragment - setList() : 통신 실패")
                }
            }
        }
    }
}