package com.basecamp.campong.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivityLoginBinding

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

    fun goToSignup(view: View) {
        val intent = Intent(applicationContext, SignupActivity::class.java)
        startActivity(intent)
    }
}