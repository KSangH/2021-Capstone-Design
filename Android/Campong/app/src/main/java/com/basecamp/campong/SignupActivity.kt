package com.basecamp.campong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.basecamp.campong.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mBinding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    fun signup(view: View) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}