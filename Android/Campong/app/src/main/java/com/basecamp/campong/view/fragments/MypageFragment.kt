package com.basecamp.campong.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.R
import com.basecamp.campong.RecyclerAdapter
import com.basecamp.campong.databinding.FragmentMypageBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Preference
import com.basecamp.campong.utils.RequestCode.GO_TO_EDIT_PROFILE
import com.basecamp.campong.utils.RequestCode.GO_TO_LEND_LIST
import com.basecamp.campong.utils.SharedPreferenceManager
import com.basecamp.campong.view.EditProfileActivity
import com.basecamp.campong.view.LendActivity
import com.basecamp.campong.view.LoginActivity
import com.bumptech.glide.Glide

class MypageFragment : Fragment(), View.OnClickListener {

    private var mBinding: FragmentMypageBinding? = null
    private var image_id: Long? = null
    private lateinit var mAdapter: RecyclerAdapter
    private var pageNum: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding

        getMyPageInfo()

        mAdapter = RecyclerAdapter()
        getPostList(pageNum)

        binding.goToEditProfileButton.setOnClickListener(this)
        binding.logoutButton.setOnClickListener(this)
        binding.goToLendListButton.setOnClickListener(this)
        binding.lendListButton.setOnClickListener(this)

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)

            // 스크롤 리스너
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // 현재 화면에 보이는 아이템 중 마지막 위치
                    val lastVisibleItemPosition: Int =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    // 마지막 아이템 위치
                    val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                    // 스크롤이 마지막인 경우 더 불러오기
                    if (lastVisibleItemPosition == itemTotalCount) {
                        getPostList(pageNum)
                    }
                }
            })
        }

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

                    // 이메일, 패스워드 삭제
                    val preferences = SharedPreferenceManager.instance
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_EMAIL, null)
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_PW, null)
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_LOCATION, null)
                    editor.putStringSet(Preference.SHARED_PREFERENCE_NAME_COOKIE, null)
                    editor.apply()

                    activity?.finish()

                    val loginIntent = Intent(context, LoginActivity::class.java)
                    startActivity(loginIntent)
                }
                else -> {
                    Toast.makeText(context, "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()

                    // 이메일, 패스워드 삭제
                    val preferences = SharedPreferenceManager.instance
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_EMAIL, null)
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_PW, null)
                    editor.putString(Preference.SHARED_PREFERENCE_NAME_LOCATION, null)
                    editor.putStringSet(Preference.SHARED_PREFERENCE_NAME_COOKIE, null)
                    editor.apply()

                    activity?.finish()

                    val loginIntent = Intent(context, LoginActivity::class.java)
                    startActivity(loginIntent)
                }
            }
        }
    }

    private fun goToLendList(view: View) {
        val intent = Intent(context, LendActivity::class.java)
        startActivityForResult(intent, GO_TO_LEND_LIST)
    }

    private fun pageUp() {
        pageNum++
    }

    private fun pageReset() {
        pageNum = 0
    }

    /* 서버에서 게시물 목록 가져오기 */
    private fun getPostList(pageNum: Int) {
        RetrofitManager.instance.requestPostMyList(pageNum) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(Constants.TAG, "MyPageFragment - getPostList() : data is not null!!")
                        mAdapter.setList(data)
                        pageUp() // 통신에 성공하면 다음 요청을 위해 pageNum++
                    } else {
                        Log.d(Constants.TAG, "MyPageFragment - getPostList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "MyPageFragment - getPostList() : 통신 실패")
                }
            }
        }
    }
}