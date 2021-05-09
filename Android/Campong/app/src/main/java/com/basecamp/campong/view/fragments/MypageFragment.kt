package com.basecamp.campong.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.basecamp.campong.R
import com.basecamp.campong.databinding.FragmentMypageBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.view.EditProfileActivity
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

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun goToEditProfile(view: View) {
        val intent = Intent(context, EditProfileActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.goToEditProfileButton -> {
                goToEditProfile(v)
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
}