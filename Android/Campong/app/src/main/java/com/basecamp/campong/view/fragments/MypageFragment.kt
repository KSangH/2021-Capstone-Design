package com.basecamp.campong.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.basecamp.campong.R
import com.basecamp.campong.databinding.FragmentMypageBinding
import com.basecamp.campong.view.EditProfileActivity

class MypageFragment : Fragment(), View.OnClickListener {

    private var mBinding: FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding

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
}