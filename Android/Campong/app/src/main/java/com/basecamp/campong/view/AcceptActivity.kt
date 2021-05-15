package com.basecamp.campong.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityAcceptBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants

class AcceptActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAcceptBinding
    private var reserveid: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAcceptBinding.inflate(layoutInflater)

        reserveid = intent.getLongExtra("reserveid", -1)

        setContentView(mBinding.root)
    }

    fun requestGrant(view: View) {
        RetrofitManager.instance.requestReserveStateGrant(reserveid!!) {
            when (it) {
                0 -> {
                    Log.d(Constants.TAG, "AcceptActivity - requestGrant() : 신청을 승인하였습니다.")
                    Toast.makeText(this, "예약을 승인하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Log.d(Constants.TAG, "AcceptActivity - requestGrant() : 오류가 발생하였습니다.")
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun requestCancel(view: View) {
        RetrofitManager.instance.requestReserveStateCancel(reserveid!!) {
            when (it) {
                0 -> {
                    Log.d(Constants.TAG, "AcceptActivity - requestCancel() : 신청을 거절하였습니다.")
                    Toast.makeText(this, "신청을 거절하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Log.d(Constants.TAG, "AcceptActivity - requestCancel() : 오류가 발생하였습니다.")
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}