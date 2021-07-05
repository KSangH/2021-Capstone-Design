package com.basecamp.campong.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityReserveViewBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.bumptech.glide.Glide

class ReserveViewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityReserveViewBinding
    private var reserveid: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityReserveViewBinding.inflate(layoutInflater)

        reserveid = intent.getLongExtra(Keyword.RESERVE_ID, -1)

        initToolbar()
        getReserveItem()

        setContentView(mBinding.root)
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getReserveItem() {
        RetrofitManager.instance.requestReserveView(reserveid!!) { code, reserveItem ->
            when (code) {
                0 -> {
                    if (reserveItem != null) {
                        val url = "${API.BASE_URL}/image/${reserveItem.imageid}"

                        Glide.with(this)
                            .load(url)
                            .centerCrop()
                            .into(mBinding.imageView)

                        mBinding.apply {
                            this.reserveItem = reserveItem
                        }
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "AcceptActivity - getReserveItem() : 예약 내역을 불러오지 못했습니다.")
                    Toast.makeText(this, "예약 내역을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}