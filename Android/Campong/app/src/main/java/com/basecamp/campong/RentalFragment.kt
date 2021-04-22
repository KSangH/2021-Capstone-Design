package com.basecamp.campong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.basecamp.campong.databinding.FragmentRentalBinding
import com.google.android.material.tabs.TabLayoutMediator

class RentalFragment : Fragment() {

    private var mBinding: FragmentRentalBinding? = null
    private val tabTextList = arrayListOf("신청", "대여중", "반납완료", "취소")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = mBinding?.tabLayout
        val pager = mBinding?.pager

        pager?.adapter = RentalPagerAdapter(this)

        if (tabLayout != null && pager != null) {
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = tabTextList[position]
            }.attach()
        }

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}