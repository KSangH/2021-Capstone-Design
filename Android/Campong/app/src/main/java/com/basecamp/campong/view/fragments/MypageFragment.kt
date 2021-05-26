package com.basecamp.campong.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.basecamp.campong.R
import com.basecamp.campong.databinding.FragmentMypageBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.view.EditProfileActivity
import com.basecamp.campong.view.LendActivity
import com.basecamp.campong.view.LoginActivity
import com.basecamp.campong.view.SetMapActivity
import com.bumptech.glide.Glide

class MypageFragment : Fragment(), View.OnClickListener {

    private var mBinding: FragmentMypageBinding? = null
    private var image_id: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding

        getUserInfo()

        binding.goToEditProfileButton.setOnClickListener(this)
        binding.logoutButton.setOnClickListener(this)
        binding.goToLendListButton.setOnClickListener(this)
        binding.lendListButton.setOnClickListener(this)

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun goToEditProfile(view: View) {
        val intent = Intent(context, EditProfileActivity::class.java)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getUserInfo()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.goToEditProfileButton -> {
                goToEditProfile(v)
            }
            R.id.logoutButton -> {
                // logout(v)
                val intent = Intent(context, SetMapActivity::class.java)
                startActivity(intent)
            }
            R.id.goToLendListButton -> {
                goToLendList(v)
            }
            R.id.lendListButton -> {
                goToLendList(v)
            }
        }
    }

    fun getUserInfo() {
        RetrofitManager.instance.requestUserInfo { code, usernick, imageid ->
            when (code) {
                0 -> {
                    if (mBinding != null) {
                        if (usernick != null) {
                            mBinding!!.nicknameTextView.text = usernick
                        }
                        if (imageid != null) {
                            image_id = imageid
                            val url = "${API.BASE_URL}/image/$image_id"

                            Glide.with(this)
                                .load(url)
                                .centerCrop()
                                .into(mBinding!!.profile)
                        }
                    }

                }
            }
        }
    }

    fun logout(view: View) {
        Log.d(Constants.TAG, "MypageFragment : logout()")

            RetrofitManager.instance.requestLogout(
            ) {
                when (it) {
                    0 -> {
                        Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show()
                        val loginIntent = Intent(context, LoginActivity::class.java)
                        startActivity(loginIntent)
                    }
                    else -> {
                        Toast.makeText(context, "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }

    private fun goToLendList(view: View) {
        val intent = Intent(context, LendActivity::class.java)
        startActivity(intent)
    }
}