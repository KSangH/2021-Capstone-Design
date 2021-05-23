package com.basecamp.campong.view

import android.os.Bundle
import android.util.Log
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
    lateinit var mAdapter: RecyclerAdapter
    private var pageNum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchBinding.inflate(layoutInflater)

        val chipGroup = mBinding.chipGroup

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            category = getCategory(checkedId)
        }

        mAdapter = RecyclerAdapter()

        mBinding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        setContentView(mBinding.root)
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

    fun search(view: View) {
        val keyword = mBinding.searchInput.text.toString()
        requestSearch(pageNum, keyword, category)
    }

    private fun requestSearch(pageNum: Int, keyword: String?, category: String?) {

        RetrofitManager.instance.requestSearchPostList(pageNum, keyword, category) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(Constants.TAG, "HomeFragment - getPostList() : data is not null!!")
                        mAdapter.setList(data)
                    } else {
                        Log.d(Constants.TAG, "HomeFragment - getPostList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "HomeFragment - getPostList() : 통신 실패")
                }
            }
        }
        val toolbar = findViewById(R.id.searchtoolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    fun goToFilter(view: View) {
        val intent = Intent(applicationContext, FilterActivity::class.java)
        startActivity(intent)
    }
}