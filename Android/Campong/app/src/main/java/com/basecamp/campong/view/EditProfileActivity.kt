package com.basecamp.campong.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.basecamp.campong.databinding.ActivityEditProfileBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class EditProfileActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditProfileBinding
    private var image_id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityEditProfileBinding.inflate(layoutInflater)

        getUserInfo()

        initAddPhotoButton()

        setContentView(mBinding.root)
    }

    fun getUserInfo() {
        RetrofitManager.instance.requestUserInfo { code, usernick, imageid ->
            when (code) {
                0 -> {
                    if (usernick != null) {
                        mBinding.usernickEditText.setText(usernick)
                    }
                    if (imageid != null) {
                        image_id = imageid
                        val url = "${API.BASE_URL}/image/$image_id"

                        Glide.with(this)
                            .load(url)
                            .centerCrop()
                            .into(mBinding.profileImageView)
                    }
                }
            }
        }
    }

    fun editProfile(view: View) {
        RetrofitManager.instance.requestUpdateUser(
            mBinding.usernickEditText.text.toString(),
            image_id
        ) {
            when (it) {
                0 -> {
                    Toast.makeText(applicationContext, "???????????? ?????????????????????.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                else -> {
                    Toast.makeText(applicationContext, "????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initAddPhotoButton() {
        mBinding.profileImageView.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> { // ????????? ??????????????? ???????????? ?????? ??????
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup() // ????????? ????????? ?????? ?????? ?????? ?????? ???????????????
                }

                else -> {
                    requestPermissions( // ?????? ??????
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                    )
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setMessage("????????? ????????? ???????????? ???????????? ????????? ????????? ???????????????.")
            .setPositiveButton("??????") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                )
            }
            .setNegativeButton("??????") { _, _ -> }
            .create().show()
    }

    // ????????? ????????????
    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    // ????????? ?????? ???
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {

                    Log.d(Constants.TAG, "uri not null")
                    //Toast.makeText(this, selectedImageUri.path, Toast.LENGTH_SHORT).show()

                    try {
                        val inputStream = contentResolver.openInputStream(data.data!!)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        uploadImage(bitmap)
                        mBinding.profileImageView.setImageURI(selectedImageUri)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                } else {
                    Log.d(Constants.TAG, "uri is null")
                    Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
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
        val imageFile = getImageFile(bitmap, "profile")

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
}