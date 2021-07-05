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
import com.basecamp.campong.RentalRecyclerAdapter
import com.basecamp.campong.databinding.FragmentRentalDetailBinding
import com.basecamp.campong.model.ReserveItem
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.basecamp.campong.utils.RequestCode.RESERVE_ITEM
import com.basecamp.campong.view.QrViewActivity
import com.basecamp.campong.view.ReserveViewActivity

class RentalStateFragment(val state: Int) : Fragment() {

    private var mBinding: FragmentRentalDetailBinding? = null
    private lateinit var mAdapter: RentalRecyclerAdapter
    private var pageNum: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalDetailBinding.inflate(inflater, container, false)

        mBinding = binding

        mAdapter = RentalRecyclerAdapter()
        mAdapter.removeAll()
        pageReset()
        mAdapter.setOnItemClickListener(object : RentalRecyclerAdapter.RentalClickListener {
            // 해당 post로 이동
            override fun onBaseItemClicked(view: View, reserveItem: ReserveItem) {
                val intent = Intent(context, ReserveViewActivity::class.java)
                intent.putExtra(Keyword.RESERVE_ID, reserveItem.reserveid)
                startActivity(intent)
            }

            // 대여 QR 화면으로 이동
            override fun onRentalQRClicked(view: View, reserveItem: ReserveItem) {
                val intent = Intent(context, QrViewActivity::class.java)
                intent.putExtra(Keyword.QR_TYPE, Keyword.QR_TYPE_RENTAL)
                intent.putExtra(Keyword.RESERVE_ID, reserveItem.reserveid)
                intent.putExtra(Keyword.TITLE, reserveItem.title)
                intent.putExtra(Keyword.LOCATION, reserveItem.location)
                intent.putExtra(Keyword.FEE, reserveItem.fee)
                intent.putExtra(Keyword.RENTAL_DATE, reserveItem.rentaldate)
                intent.putExtra(Keyword.RETURN_DATE, reserveItem.returndate)
                intent.putExtra(Keyword.USERNICK, reserveItem.usernick)
                startActivityForResult(intent, RESERVE_ITEM)
            }

            // 반납 QR 화면으로 이동
            override fun onReturnQRClicked(view: View, reserveItem: ReserveItem) {
                val intent = Intent(context, QrViewActivity::class.java)
                intent.putExtra(Keyword.QR_TYPE, Keyword.QR_TYPE_RETURN)
                intent.putExtra(Keyword.RESERVE_ID, reserveItem.reserveid)
                intent.putExtra(Keyword.TITLE, reserveItem.title)
                intent.putExtra(Keyword.LOCATION, reserveItem.location)
                intent.putExtra(Keyword.FEE, reserveItem.fee)
                intent.putExtra(Keyword.RENTAL_DATE, reserveItem.rentaldate)
                intent.putExtra(Keyword.RETURN_DATE, reserveItem.returndate)
                intent.putExtra(Keyword.USERNICK, reserveItem.usernick)
                startActivityForResult(intent, RESERVE_ITEM)
            }

        })
        setRentalList(state)

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)

            // 스크롤 리스너
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // 현재 화면에 보이는 아이템 중 마지막 위치
                    val lastVisibleItemPosition: Int =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    // 마지막 아이템 위치
                    val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                    // 스크롤이 마지막인 경우 더 불러오기
                    if (lastVisibleItemPosition == itemTotalCount) {
                        setRentalList(state)
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

    private fun setRentalList(state: Int) {
        RetrofitManager.instance.requestReserveList(
            state = state,
            pagenum = pageNum
        ) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(
                            Constants.TAG,
                            "RentalStateFragment - setList() : data is not null!!"
                        )
                        mAdapter.setList(data)
                        pageUp()
                    } else {
                        Log.d(Constants.TAG, "RentalStateFragment - setList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "RentalStateFragment - setList() : 통신 실패")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return
        when (requestCode) {
            RESERVE_ITEM -> {
                mAdapter.removeAll()
                pageReset()
                setRentalList(state)
            }
        }
    }
}