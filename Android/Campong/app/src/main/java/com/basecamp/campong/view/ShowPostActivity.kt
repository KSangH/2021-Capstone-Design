package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityShowPostBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants

class ShowPostActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityShowPostBinding
    private var postid: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityShowPostBinding.inflate(layoutInflater)

        postid = intent.getLongExtra("postid", -1)

        if (postid != null) {
            getPost(postid!!)
        }

        setContentView(mBinding.root)
    }

    private fun getPost(postid: Long) {
        RetrofitManager.instance.requestPostView(postid) { code, post ->
            when (code) {
                0 -> {
                    if (post != null) {
                        mBinding.apply {
                            postItem = post
                        }
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "ShowPostActivity - getPost() : 게시물을 불러오지 못했습니다.")
                    Toast.makeText(this, "게시물을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun goToReserve() {
        val intent = Intent(this, ReqReserveActivity::class.java)
        intent.putExtra("postid", postid)

        startActivity(intent)
    }

    fun goToReserveList() {
        //  val intent = Intent(this, )
    }
}