package com.basecamp.campong.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.LendPagerAdapter
import com.basecamp.campong.databinding.ActivityLendBinding
import com.google.android.material.tabs.TabLayoutMediator

class LendActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLendBinding
    private val tabTextList = arrayListOf("예약", "대여중", "반납완료", "취소")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLendBinding.inflate(layoutInflater)
        initToolbar()

        val tabLayout = mBinding.tabLayout
        val pager = mBinding.pager

        pager.adapter = LendPagerAdapter(this)

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        setContentView(mBinding.root)
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}