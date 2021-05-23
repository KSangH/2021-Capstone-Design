package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat.getCategory
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivitySearchBinding
import com.basecamp.campong.databinding.ActivityWritePostBinding

class SearchActivity : AppCompatActivity() {

    private var category: String? = null
    private lateinit var mBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mBinding = ActivitySearchBinding.inflate(layoutInflater)

        val chipGroup = mBinding.chipGroup

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            category = getCategory(checkedId)
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