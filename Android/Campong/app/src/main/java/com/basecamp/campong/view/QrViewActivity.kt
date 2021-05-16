package com.basecamp.campong.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityQrViewBinding
import com.basecamp.campong.utils.Constants
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrViewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityQrViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityQrViewBinding.inflate(layoutInflater)

        setUpQR("https://www.naver.com")
        setContentView(mBinding.root)
    }

    fun setUpQR(text: String) {
        val multiFormatWriter = MultiFormatWriter()

        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            mBinding.qrimageview.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e(Constants.TAG, e.toString())
        }
    }
}