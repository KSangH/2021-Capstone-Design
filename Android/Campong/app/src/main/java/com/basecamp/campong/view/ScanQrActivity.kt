package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityScanQrBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.basecamp.campong.utils.Keyword.QR_TYPE_RENTAL
import com.basecamp.campong.utils.Keyword.QR_TYPE_RETURN
import com.google.zxing.integration.android.IntentIntegrator

class ScanQrActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityScanQrBinding
    var qrType: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityScanQrBinding.inflate(layoutInflater)

        qrType = intent.getIntExtra(Keyword.QR_TYPE, -1)

        val qrScan = IntentIntegrator(this)
        qrScan.setOrientationLocked(false)
        qrScan.setPrompt("QR코드를 스캔해주세요.")
        qrScan.initiateScan()

        setContentView(mBinding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                val reserveid = result.contents.toLong()

                when (qrType) {
                    QR_TYPE_RENTAL -> {
                        requestRental(reserveid)
                    }
                    QR_TYPE_RETURN -> {
                        requestReturn(reserveid)
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun requestRental(reserveid: Long) {
        RetrofitManager.instance.requestReserveStateRental(reserveid) {
            when (it) {
                0 -> {
                    Log.d(Constants.TAG, "대여 처리 되었습니다.")
                    Toast.makeText(this, "대여 처리 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Log.d(Constants.TAG, "대여 처리 중 오류가 발생하였습니다.")
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestReturn(reserveid: Long) {
        RetrofitManager.instance.requestReserveStateReturn(reserveid) {
            when (it) {
                0 -> {
                    Log.d(Constants.TAG, "반납 처리 되었습니다.")
                    Toast.makeText(this, "반납 처리 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Log.d(Constants.TAG, "반납 처리 중 오류가 발생하였습니다.")
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}