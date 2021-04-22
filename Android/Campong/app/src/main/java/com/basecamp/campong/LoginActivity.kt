package com.basecamp.campong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.basecamp.campong.databinding.ActivityLoginBinding
import com.basecamp.campong.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mBinding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

    }

    fun login(view: View) {
        val loginIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(loginIntent)
    }
}