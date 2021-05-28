package com.basecamp.campong.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.basecamp.campong.R
import com.basecamp.campong.RecyclerAdapter
import com.basecamp.campong.databinding.ActivitySearchBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants

class SearchActivity : AppCompatActivity() {

    private var category: String? = null
    private lateinit var mBinding: ActivitySearchBinding
    private lateinit var mAdapter: RecyclerAdapter
    private var pageNum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchBinding.inflate(layoutInflater)

        initToolbar()
        initChipGroup()
        initRecyclerview()

        setContentView(mBinding.root)
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

    private fun initChipGroup() {
        mBinding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            category = getCategory(checkedId)
        }
    }

    private fun initToolbar() {
        val toolbar = mBinding.searchtoolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerview() {
        mAdapter = RecyclerAdapter()

        mBinding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun search(view: View) {
        val keyword = mBinding.searchInput.text.toString()
        requestSearch(pageNum, keyword, category)
    }

    private fun pageUp() {
        pageNum++
    }

    private fun pageReset() {
        pageNum = 0
    }

    private fun requestSearch(pageNum: Int, keyword: String?, category: String?) {

        RetrofitManager.instance.requestSearchPostList(pageNum, keyword, category) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(Constants.TAG, "HomeFragment - getPostList() : data is not null!!")
                        mAdapter.setList(data)
                        pageUp()
                    } else {
                        Log.d(Constants.TAG, "HomeFragment - getPostList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "HomeFragment - getPostList() : 통신 실패")
                }
            }
        }
    }

    private fun getCategory(checkedId: Int): String? {
        return when (checkedId) {
            R.id.chip0 -> "텐트/타프"
            R.id.chip1 -> "침낭/매트리스"
            R.id.chip2 -> "캠핑퍼니처"
            R.id.chip3 -> "화로/오븐/바베큐"
            R.id.chip4 -> "취사도구"
            R.id.chip5 -> "난로/난방/전기"
            R.id.chip6 -> "트레일러/카라반/차량용품"
            R.id.chip7 -> "기타"
            else -> null
        }
    }
}