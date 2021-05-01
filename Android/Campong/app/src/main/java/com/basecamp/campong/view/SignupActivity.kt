package com.basecamp.campong.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivitySignupBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mBinding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    fun signup(view: View) {

        Log.d(Constants.TAG, "signup!")

        val ddd = mBinding.emailEditText.text.toString()
        Log.d(Constants.TAG, ddd)
        Toast.makeText(applicationContext, ddd, Toast.LENGTH_SHORT)

        RetrofitManager.instance.requestSignup(
            mBinding.emailEditText.text.toString(),
            mBinding.passwordEditText.text.toString(),
            mBinding.usernickEditText.text.toString(),
            mBinding.phoneEditText.text.toString(),
            mBinding.usernameEditText.text.toString()
        )

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}