package com.basecamp.campong.view.fragments

import android.app.Activity.RESULT_OK
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
import com.basecamp.campong.utils.RequestCode.GO_TO_EDIT_PROFILE
import com.basecamp.campong.utils.RequestCode.GO_TO_LEND_LIST
import com.basecamp.campong.view.EditProfileActivity
import com.basecamp.campong.view.LendActivity
import com.basecamp.campong.view.LoginActivity
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

        getMyPageInfo()

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
        if (resultCode != RESULT_OK) return

        when (requestCode) {
            GO_TO_EDIT_PROFILE, GO_TO_LEND_LIST -> {
                getMyPageInfo()
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.goToEditProfileButton -> {
                goToEditProfile(v)
            }
            R.id.logoutButton -> {
                logout(v)
            }
            R.id.goToLendListButton -> {
                goToLendList(v)
            }
            R.id.lendListButton -> {
                goToLendList(v)
            }
        }
    }

    private fun getUserInfo() {
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

    private fun getMyPageInfo() {
        RetrofitManager.instance.requestMyPageInfo { code, usernick, imageid, list ->
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
                        // 예약 상태 표기
                        mBinding!!.state1.text = list?.get(0).toString()
                        mBinding!!.state3.text = list?.get(1).toString()
                        mBinding!!.state4.text = list?.get(2).toString()
                        mBinding!!.state5.text = list?.get(3).toString()
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
        startActivityForResult(intent, GO_TO_LEND_LIST)
    }
}