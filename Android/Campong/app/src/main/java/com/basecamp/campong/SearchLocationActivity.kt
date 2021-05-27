package com.basecamp.campong

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.basecamp.campong.databinding.ActivitySearchLocationBinding
import com.basecamp.campong.model.Location
import com.basecamp.campong.repository.LocationDatabase
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Preference
import com.basecamp.campong.utils.SharedPreferenceManager

class SearchLocationActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySearchLocationBinding
    private lateinit var mAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchLocationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initToolbar()

        mBinding.searchView.onActionViewExpanded()

        val db = LocationDatabase.getDatabase(applicationContext, lifecycleScope)

        val locationList = ArrayList<Location>()

        db.locationDao().getAll().observe(this, { list ->
            locationList.addAll(list)
            Log.d(Constants.TAG, "위치 추가함")
            Log.d(Constants.TAG, "$list")

            mAdapter = LocationAdapter(locationList)
            mAdapter.setOnItemClickListener(object : LocationAdapter.ClickListener {
                override fun onItemClicked(view: View, location: String) {
                    val preferences = SharedPreferenceManager.instance
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_LOCATION, location)
                    editor.apply()
                    setResult(RESULT_OK)
                    finish()
                }
            })

            mBinding.locationRecyclerView.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(applicationContext)
            }
        })

        mBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (mAdapter as Filterable).filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter as Filterable).filter.filter(newText)
                return true
            }

        })

    }

    fun initToolbar() {
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