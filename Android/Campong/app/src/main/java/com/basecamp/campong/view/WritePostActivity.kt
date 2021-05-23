package com.basecamp.campong.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivityWritePostBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class WritePostActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWritePostBinding
    private var image_id: Long? = null
    private var category: String? = null

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWritePostBinding.inflate(layoutInflater)

        initAddPhotoButton()

        val chipGroup = mBinding.chipGroup

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            category = getCategory(checkedId)
        }

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

                    try {
                        val inputStream = contentResolver.openInputStream(data.data!!)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        uploadImage(bitmap)
                        mBinding.selectImageButton.setImageURI(selectedImageUri)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
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

    private fun uploadImage(bitmap: Bitmap) {
        val imageFile = getImageFile(bitmap, "itemphoto")

        RetrofitManager.instance.requestUploadImage(imageFile) { code, id ->
            when (code) {
                0 -> {
                    if (id != null) {
                        Log.d(Constants.TAG, "uploadImage(): Result - image_id is not null!!")
                        image_id = id
                    } else {
                        Log.d(Constants.TAG, "uploadImage(): Result - image_id is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "uploadImage(): Result code is not 0")
                }
            }
        }
    }

    private fun getCategory(checkedId: Int): String? {
        return when (checkedId) {
            R.id.chip0 -> "텐트/타프"
            R.id.chip1 -> "침낭/매트리스"
            R.id.chip2 -> "캠핑퍼니처"
            R.id.chip3 -> "화로/오븐/바베큐"
            R.id.chip4 -> "취사도구"
            R.id.chip5 -> "난로/난방/전기"
            R.id.chip6 -> "트레일러/카라반/차량용품"
            R.id.chip7 -> "기타"
            else -> null
        }
    }

    private fun checkNoBlank(): Boolean {
        val msg = "필수 항목입니다."
        var result: Boolean = true

        if (image_id == null) {
            Toast.makeText(this, "이미지를 선택해주세요.(필수)", Toast.LENGTH_SHORT).show()
            result = false
        }

        if (category.isNullOrBlank()) {
            Toast.makeText(this, "카테고리를 선택해주세요.(필수)", Toast.LENGTH_SHORT).show()
            result = false
        }
        if (mBinding.titleEditText.text.isNullOrBlank()) {
            mBinding.titleTextInput.error = msg
            result = false
        }
        if (mBinding.contentEditText.text.isNullOrBlank()) {
            mBinding.contentTextInput.error = msg
            result = false
        }
        if (mBinding.locationEditText.text.isNullOrBlank()) {
            mBinding.locationTextInput.error = msg
            result = false
        }
        if (mBinding.feeEditText.text.isNullOrBlank()) {
            mBinding.feeTextInput.error = msg
            result = false
        }
        return result
    }

    fun uploadPost(view: View) {
        if (checkNoBlank()) {
            RetrofitManager.instance.requestUploadPost(
                category!!,
                mBinding.titleEditText.text.toString(),
                mBinding.contentEditText.text.toString(),
                mBinding.feeEditText.text.toString(),
                "37.541", "126.986", "종로구 종로2가", // TODO
                image_id
            ) { code, id ->
                when (code) {
                    0 -> {
                        if (id != null) { // post id가 null이 아니면
                            Toast.makeText(this, "장비 등록 완료!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, ShowPostActivity::class.java)
                            intent.putExtra(Keyword.POST_ID, id)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.d(
                                Constants.TAG,
                                "WritePostActivity : uploadPost() - Result : post id is null"
                            )
                        }
                    }
                    else -> {
                        Toast.makeText(this, "게시물 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}