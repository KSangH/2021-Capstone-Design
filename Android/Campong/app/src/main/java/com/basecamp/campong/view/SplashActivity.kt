package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Preference
import com.basecamp.campong.utils.SharedPreferenceManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SharedPreference init
        SharedPreferenceManager().init(applicationContext)

        // email, pw 가져오기
        val email: String =
            SharedPreferenceManager.instance.getString(
                Preference.SHARED_PREFERENCE_NAME_EMAIL, ""
            ) as String

        val pw: String =
            SharedPreferenceManager.instance.getString(
                Preference.SHARED_PREFERENCE_NAME_PW, ""
            ) as String

        // 값이 있는 경우 자동 로그인
        if (email != "" && pw != "") {
            login(email, pw)
        } else { // 그렇지 않은 경우 로그인 화면으로 이동
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun login(email: String, pw: String) {
        RetrofitManager.instance.requestLogin(email, pw) {
            when (it) {
                0 -> {
                    val loginIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                }
                else -> {
                    Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}