package com.basecamp.campong.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivityLoginBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants.TAG
import com.basecamp.campong.utils.Preference
import com.basecamp.campong.utils.SharedPreferenceManager

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Campong)
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(layoutInflater)

        // SharedPreference init
        SharedPreferenceManager().init(applicationContext)

        setContentView(mBinding.root)

    }

    private fun checkEmail(): Boolean = mBinding.emailEditText.text.toString().isNotEmpty()

    private fun checkPassword(): Boolean = mBinding.passwordEditText.text.toString().isNotEmpty()

    fun login(view: View) {
        Log.d(TAG, "LoginActivity : login()")

        if (mBinding.emailEditText.text.toString() == "1" && mBinding.passwordEditText.text.toString() == "1"
        ) {
            val loginIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(loginIntent)
        } else if (checkEmail() && checkPassword()) {
            val email = mBinding.emailEditText.text.toString()
            val pw = mBinding.passwordEditText.text.toString()
            RetrofitManager.instance.requestLogin(
                email,
                pw
            ) {
                when (it) {
                    0 -> {
                        val loginIntent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(loginIntent)
                        val preferences = SharedPreferenceManager.instance
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString(Preference.SHARED_PREFERENCE_NAME_EMAIL, email)
                        editor.putString(Preference.SHARED_PREFERENCE_NAME_PW, pw)
                        editor.apply()
                        finish()
                    }
                    else -> {
                        Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun goToSignup(view: View) {
        val intent = Intent(applicationContext, SignupActivity::class.java)
        startActivity(intent)
    }
}