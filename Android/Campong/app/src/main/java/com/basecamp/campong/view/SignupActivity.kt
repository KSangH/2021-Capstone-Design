package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivitySignupBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants.TAG

class SignupActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignupBinding
    var emailCheck: Boolean = false
    var nicknameCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mBinding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    fun checkEmail(view: View) {
        Log.d(TAG, "SignupActivity : checkEmail()")

        RetrofitManager.instance.requestCheckEmail(mBinding.emailEditText.text.toString()) {
            when (it) {
                0 -> {
                    mBinding.emailTextInput.helperText = "사용가능한 이메일입니다."
                    emailCheck = true
                }
                1 -> {
                    mBinding.emailTextInput.error = "이미 사용중인 이메일입니다."
                    emailCheck = false
                }
                else -> {
                    mBinding.emailTextInput.error = "오류가 발생하였습니다."
                }
            }
        }
    }

    fun checkNickname(view: View) {
        Log.d(TAG, "SignupActivity : checkNickname()")

        RetrofitManager.instance.requestCheckNick(mBinding.usernickEditText.text.toString()) {
            when (it) {
                0 -> {
                    mBinding.usernickTextInput.helperText = "사용가능한 닉네임입니다."
                    nicknameCheck = true
                }
                1 -> {
                    mBinding.usernickTextInput.error = "이미 사용중인 닉네임입니다."
                    nicknameCheck = false
                }
                else -> {
                    mBinding.usernickTextInput.error = "오류가 발생하였습니다."
                }
            }
        }
    }

    fun signup(view: View) {
        Log.d(TAG, "SignupActivity : signup()")

        if (emailCheck && nicknameCheck) { // 중복확인을 모두 한 경우에만 회원가입 요청
            RetrofitManager.instance.requestSignup(
                mBinding.emailEditText.text.toString(),
                mBinding.passwordEditText.text.toString(),
                mBinding.usernickEditText.text.toString(),
                mBinding.phoneEditText.text.toString(),
                mBinding.usernameEditText.text.toString()
            ) {
                when (it) {
                    0 -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        Toast.makeText(applicationContext, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } else if (!emailCheck && !nicknameCheck) {
            Toast.makeText(applicationContext, "이메일과 닉네임 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show()
        } else if (!emailCheck) {
            Toast.makeText(applicationContext, "이메일 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "닉네임 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }
}