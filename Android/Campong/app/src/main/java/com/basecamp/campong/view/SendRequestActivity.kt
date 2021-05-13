package com.basecamp.campong.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivitySendRequestBinding

class SendRequestActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySendRequestBinding
    private var postid: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySendRequestBinding.inflate(layoutInflater)

        postid = intent.getLongExtra("postid", -1)




        setContentView(mBinding.root)
    }
}