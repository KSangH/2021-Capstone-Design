package com.basecamp.campong.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.basecamp.campong.databinding.ActivityEditProfileBinding
import com.basecamp.campong.retrofit.RetrofitManager
import java.io.File

class EditProfileActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityEditProfileBinding.inflate(layoutInflater)

        initAddPhotoButton()

        setContentView(mBinding.root)
    }

    fun editProfile(view: View) {
        RetrofitManager.instance.requestUpdateNick(mBinding.usernickEditText.text.toString()) {
            when (it) {
                0 -> {
                    Toast.makeText(applicationContext, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                else -> {
                    Toast.makeText(applicationContext, "닉네임 변경에 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun initAddPhotoButton() {
        mBinding.profileImageView.setOnClickListener {
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

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    mBinding.profileImageView.setImageURI(selectedImageUri)

                    Toast.makeText(this, selectedImageUri.path, Toast.LENGTH_SHORT).show()
                    val file = File(selectedImageUri.path!!)

                    RetrofitManager.instance.requestUploadImage(file) {
                        when (it) {
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
}