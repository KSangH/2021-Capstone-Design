package com.basecamp.campong.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.LendRecyclerAdapter
import com.basecamp.campong.databinding.FragmentRentalDetailBinding
import com.basecamp.campong.model.ReserveItem
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.basecamp.campong.utils.RequestCode
import com.basecamp.campong.view.AcceptActivity
import com.basecamp.campong.view.ReserveViewActivity

class LendStateFragment(val state: Int) : Fragment() {

    private var mBinding: FragmentRentalDetailBinding? = null
    private lateinit var mAdapter: LendRecyclerAdapter
    private var pageNum: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalDetailBinding.inflate(inflater, container, false)

        mBinding = binding

        mAdapter = LendRecyclerAdapter()
        pageReset()
        mAdapter.removeAll()
        mAdapter.setOnItemClickListener(object : LendRecyclerAdapter.ClickListener {
            override fun onWaitItemClicked(view: View, reserveItem: ReserveItem) {
                val intent = Intent(context, AcceptActivity::class.java)
                intent.putExtra(Keyword.RESERVE_ID, reserveItem.reserveid)
                startActivityForResult(intent, RequestCode.ACCEPT_REQUEST)
            }

            override fun onBaseItemClicked(view: View, reserveItem: ReserveItem) {
                Log.d(Constants.TAG, "onBaseItemClicked")
                val intent = Intent(context, ReserveViewActivity::class.java)
                intent.putExtra(Keyword.RESERVE_ID, reserveItem.reserveid)
                startActivity(intent)
            }

        })
        setLendList(state)

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)

            // ????????? ?????????
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // ?????? ????????? ????????? ????????? ??? ????????? ??????
                    val lastVisibleItemPosition: Int =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    // ????????? ????????? ??????
                    val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                    // ???????????? ???????????? ?????? ??? ????????????
                    if (lastVisibleItemPosition == itemTotalCount) {
                        setLendList(state)
                    }
                }
            })
        }

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun pageUp() {
        pageNum++
    }

    private fun pageReset() {
        pageNum = 0
    }

    private fun setLendList(state: Int) {
        RetrofitManager.instance.requestReserveMyList(
            state = state,
            pagenum = pageNum
        ) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(
                            Constants.TAG,
                            "LendStateFragment - setList() : data is not null!!"
                        )
                        mAdapter.setList(data)
                        pageUp()
                    } else {
                        Log.d(Constants.TAG, "LendStateFragment - setList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "LendStateFragment - setList() : ?????? ??????")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode.ACCEPT_REQUEST) {
            if (resultCode == RESULT_OK) {
                pageReset()
                mAdapter.removeAll()
                setLendList(state)
            }
        }
    }
}