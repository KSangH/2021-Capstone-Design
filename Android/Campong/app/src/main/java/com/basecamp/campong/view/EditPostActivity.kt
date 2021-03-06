package com.basecamp.campong.view

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivityEditPostBinding
import com.basecamp.campong.model.Post
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.basecamp.campong.utils.RequestCode.PICK_PHOTO
import com.basecamp.campong.utils.RequestCode.SELECT_LOCATION
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class EditPostActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditPostBinding
    private var image_id: Long? = null
    private var category: String? = null
    private var postid: Long? = null
    private var post: Post? = null
    private var baseAddress: String? = null
    private var lat: Double? = -1.0
    private var lon: Double? = -1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityEditPostBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        postid = intent.getLongExtra(Keyword.POST_ID, -1)

        if (postid != null) {
            getPost(postid!!)
        }

        initToolbar()
        initAddPhotoButton()

        val chipGroup = mBinding.chipGroup

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            category = getCategory(checkedId)
        }
    }
    private fun initToolbar() {
        val toolbar = mBinding.writePostToolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // ????????? ?????? ??????, ????????? ??????
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
        startActivityForResult(intent, PICK_PHOTO)
    }

    fun selectMapLocation(view: View) {
        val intent = Intent(applicationContext, SetMapActivity::class.java)
        startActivityForResult(intent, SELECT_LOCATION)
    }

    // ????????? ?????? ???
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return

        when (requestCode) {
            PICK_PHOTO -> { // ????????? ?????? ???
                if (resultCode == RESULT_OK) {
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
                        Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                }

            }
            SELECT_LOCATION -> { // ?????? ?????? ???
                if (requestCode == SELECT_LOCATION) {
                    if (resultCode == RESULT_OK) {
                        lat = data?.getDoubleExtra("lat", -1.0)
                        lon = data?.getDoubleExtra("lon", -1.0)
                        baseAddress = data?.getStringExtra("baseAddr")

                        if (lat != null && lon != null) {
                            Log.d(Constants.TAG, "lat : $lat, lon: $lon")
                            mBinding.setMapLocationButton.text = "$baseAddress ?????? ??????"
                        }
                    }
                }
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
            R.id.chip0 -> "??????/??????"
            R.id.chip1 -> "??????/????????????"
            R.id.chip2 -> "???????????????"
            R.id.chip3 -> "??????/??????/?????????"
            R.id.chip4 -> "????????????"
            R.id.chip5 -> "??????/??????/??????"
            R.id.chip6 -> "????????????/?????????/????????????"
            R.id.chip7 -> "??????"
            else -> null
        }
    }

    // ????????? ????????? ??????
    private fun checkNoBlank(): Boolean {
        val msg = "?????? ???????????????."
        var result = true

        if (image_id == null) {
            Toast.makeText(this, "???????????? ??????????????????.(??????)", Toast.LENGTH_SHORT).show()
            result = false
        }

        if (category.isNullOrBlank()) {
            Toast.makeText(this, "??????????????? ??????????????????.(??????)", Toast.LENGTH_SHORT).show()
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
        if (baseAddress.isNullOrBlank()) {
            Toast.makeText(this, "?????? ????????? ??????????????????.(??????)", Toast.LENGTH_SHORT).show()
            result = false
        }
        if (mBinding.feeEditText.text.isNullOrBlank()) {
            mBinding.feeTextInput.error = msg
            result = false
        }
        return result
    }

    // ????????? ????????????
    fun updatePost(view: View) {
        if (checkNoBlank()) {
            RetrofitManager.instance.requestUpdatePost(
                postid!!,
                category!!,
                mBinding.titleEditText.text.toString(),
                mBinding.contentEditText.text.toString(),
                mBinding.feeEditText.text.toString(),
                lat.toString(), lon.toString(), baseAddress.toString(),
                image_id
            ) { code, id ->
                when (code) {
                    0 -> {
                        if (id != null) { // post id??? null??? ?????????
                            Toast.makeText(this, "????????? ?????? ??????!", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        } else {
                            Log.d(
                                Constants.TAG,
                                "WritePostActivity : uploadPost() - Result : post id is null"
                            )
                        }
                    }
                    else -> {
                        Toast.makeText(this, "????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getPost(postid: Long) {
        RetrofitManager.instance.requestPostView(postid) { code, _, post ->
            when (code) {
                0 -> {
                    if (post != null) {
                        this.post = post
                        category = post.catename
                        image_id = post.imageid
                        baseAddress = post.location
                        val url = "${API.BASE_URL}/image/${post.imageid}"

                        Glide.with(this)
                            .load(url)
                            .centerCrop()
                            .into(mBinding.selectImageButton)

                        mBinding.apply {
                            postItem = post
                        }
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "ShowPostActivity - getPost() : ???????????? ???????????? ???????????????.")
                    Toast.makeText(this, "???????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}