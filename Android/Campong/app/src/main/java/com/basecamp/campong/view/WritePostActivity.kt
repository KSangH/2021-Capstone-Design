package com.basecamp.campong.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.basecamp.campong.databinding.ActivityWritePostBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class WritePostActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWritePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWritePostBinding.inflate(layoutInflater)

        initAddPhotoButton()

        setContentView(mBinding.root)
    }

    private fun initAddPhotoButton() {
        mBinding.selectImageButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }

                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                    )
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setMessage("앱에서 사진을 가져오기 위해서는 저장소 권한이 필요합니다.")
            .setPositiveButton("확인") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                )
            }
            .setNegativeButton("취소") { _, _ -> }
            .create().show()
    }

    // 이미지 불러오기
    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    // 이미지 선택 후
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {

                    Toast.makeText(this, selectedImageUri.path, Toast.LENGTH_SHORT).show()
                    val file = selectedImageUri.toFile()

//                    try {
//                        val inputStream = contentResolver.openInputStream(data.data!!)
//                        val bitmap = BitmapFactory.decodeStream(inputStream)
//                        uploadImage(bitmap)
//                    } catch (e: FileNotFoundException) {
//                        e.printStackTrace()
//                    }
                    mBinding.selectImageButton.setImageURI(selectedImageUri)

                    RetrofitManager.instance.requestUploadImage(file) { code, image_id ->
                        when (code) {
                            0 -> {
                                Toast.makeText(this, "서버에 업로드 하였습니다.", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(this, "업로드에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getImageFile(bitmap: Bitmap, name: String): File {
        val filesDir = applicationContext.filesDir
        val imageFile = File(filesDir, "$name.jpg")

        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error writing Bitmap", e)
        }

        return imageFile
    }

    fun uploadImage(bitmap: Bitmap) {
        val imageFile = getImageFile(bitmap, "profile")

        RetrofitManager.instance.requestUploadImage(imageFile) { code, image_id ->
            when (code) {
                0 -> {
                    Toast.makeText(this, "서버에 업로드 하였습니다.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "업로드에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}