package com.basecamp.campong.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityEditProfileBinding
import com.basecamp.campong.retrofit.RetrofitManager

class EditProfileActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityEditProfileBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    fun editProfile(view: View) {
        RetrofitManager.instance.requestUpdateNick(mBinding.usernickEditText.text.toString()) {
            when (it) {
                0 -> {
                    Toast.makeText(applicationContext, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                else -> {
                    Toast.makeText(applicationContext, "닉네임 변경에 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}