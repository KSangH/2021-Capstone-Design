package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    fun goToFilter(view: View) {
        val intent = Intent(applicationContext, FilterActivity::class.java)
        startActivity(intent)
    }
}