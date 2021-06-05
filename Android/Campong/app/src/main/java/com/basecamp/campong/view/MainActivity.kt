package com.basecamp.campong.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivityMainBinding
import com.basecamp.campong.utils.SharedPreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Campong)
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        // SharedPreference init
        SharedPreferenceManager().init(applicationContext)

        // 네비게이션들을 담는 호스트
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바텀 네비게이션 뷰와 네비게이션을 묶어준다.
        NavigationUI.setupWithNavController(mBinding.myBottomNav, navController)

    }
}