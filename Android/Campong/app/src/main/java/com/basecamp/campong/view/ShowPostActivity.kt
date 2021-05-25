package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityShowPostBinding
import com.basecamp.campong.model.Post
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.bumptech.glide.Glide

class ShowPostActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityShowPostBinding
    private var postid: Long? = null
    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityShowPostBinding.inflate(layoutInflater)

        postid = intent.getLongExtra(Keyword.POST_ID, -1)

        if (postid != null) {
            getPost(postid!!)
        }

        setContentView(mBinding.root)
    }

    private fun getPost(postid: Long) {
        RetrofitManager.instance.requestPostView(postid) { code, mypost, post ->
            when (code) {
                0 -> {
                    if (mypost != null) {
                        if (mypost) {
                            mBinding.button.text = "예약 내역 보기"
                            mBinding.button.setOnClickListener {
                                goToReserveList()
                            }
                        } else {
                            mBinding.button.text = "예약하기"
                            mBinding.button.setOnClickListener {
                                goToReserve()
                            }
                        }
                    }
                    if (post != null) {
                        this.post = post
                        val url = "${API.BASE_URL}/image/${post.imageid}"

                        Glide.with(this)
                            .load(url)
                            .centerCrop()
                            .into(mBinding.imageView)

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

    private fun goToReserve() {
        val intent = Intent(this, ReqReserveActivity::class.java)
        intent.putExtra(Keyword.POST_ID, postid)

        RetrofitManager.instance.requestReserveInit(postid!!) {
            when (it) {
                0 -> {
                    if (post != null) {
                        intent.putExtra(Keyword.TITLE, post!!.title)
                        intent.putExtra(Keyword.LOCATION, post!!.location)
                        intent.putExtra(Keyword.FEE, post!!.fee)
                        intent.putExtra(Keyword.IMAGE_ID, post!!.imageid)
                        startActivity(intent)
                    }
                }
                else -> {
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToReserveList() {
        //  val intent = Intent(this, )
    }
}