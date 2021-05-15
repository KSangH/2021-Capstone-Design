package com.basecamp.campong.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityReqReserveBinding

class ReqReserveActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityReqReserveBinding
    private var postid: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityReqReserveBinding.inflate(layoutInflater)

        postid = intent.getLongExtra("postid", -1)




        setContentView(mBinding.root)
    }
}