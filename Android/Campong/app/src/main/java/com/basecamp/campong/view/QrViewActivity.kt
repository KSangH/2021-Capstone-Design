package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityQrViewBinding
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrViewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityQrViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityQrViewBinding.inflate(layoutInflater)

        init(intent)
        setUpQR(
            intent.getLongExtra(Keyword.RESERVE_ID, -1),
            intent.getIntExtra(Keyword.QR_TYPE, -1)
        )

        setContentView(mBinding.root)
    }

    private fun init(intent: Intent) {
        mBinding.title.text = intent.getStringExtra(Keyword.TITLE)
        mBinding.town.text = intent.getStringExtra(Keyword.LOCATION)
        mBinding.price.text = intent.getStringExtra(Keyword.FEE)
        mBinding.startdate.text = intent.getStringExtra(Keyword.RENTAL_DATE)
        mBinding.enddate.text = intent.getStringExtra(Keyword.RETURN_DATE)
    }

    private fun setUpQR(reserveid: Long, qrType: Int) {
        val multiFormatWriter = MultiFormatWriter()
        if (reserveid > 0 && qrType != -1) {
            // qr코드에 인코딩할 데이터 - 콜론(:)으로 구분
            val data = "$qrType:${reserveid}"
            try {
                // qr코드 인코딩
                val bitMatrix =
                    multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.createBitmap(bitMatrix)
                mBinding.qrimageview.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e(Constants.TAG, e.toString())
            }
        }
    }
}