package com.basecamp.campong.retrofit

import android.util.Log
import com.basecamp.campong.model.*
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants.TAG
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val service: RetrofitService? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitService::class.java)

    // 회원가입
    fun requestSignup(
        email: String, password: String, usernick: String, phone: String, username: String,
        completion: (Int) -> Unit
    ) {
        val req = ReqSignup(email, password, usernick, phone, username)
        val call = service?.requestSignup(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {  // 통신 성공
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                completion(1)
                            }
                        }
                        else -> { // 통신 실패
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }

            }
        )
    }

    // 이메일 중복확인
    fun requestCheckEmail(email: String, completion: (Int) -> Unit) {
        val req = ReqCheckEmail(email)
        val call = service?.requestCheckEmail(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                if (response.body()?.errCode == 1002) {
                                    completion(1) // 중복 이메일
                                } else {
                                    completion(2)
                                }
                            }
                        }
                        else -> {
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }

            }
        )
    }

    // 닉네임 중복확인
    fun requestCheckNick(usernick: String, completion: (Int) -> Unit) {
        val req = ReqCheckNick(usernick)
        val call = service?.requestCheckNick(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                if (response.body()?.errCode == 1003) {
                                    completion(1) // 중복 닉네임
                                } else {
                                    completion(2)
                                }
                            }
                        }
                        else -> {
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }
            }
        )
    }

    // 로그인
    fun requestLogin(email: String, password: String, completion: (Int) -> Unit) {
        val req = ReqLogin(email, password)
        val call = service?.requestLogin(
            req
        ) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1005) { // 로그인 오류
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 사용자 정보 요청
    fun requestUserInfo(completion: (Int, usernick: String?, imageid: Long?) -> Unit) {
        val call = service?.requestUserInfo() ?: return

        call.enqueue(object : Callback<ResultUserInfo> {
            override fun onResponse(
                call: Call<ResultUserInfo>,
                response: Response<ResultUserInfo>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.usernick, response.body()!!.image_id)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null, null)
                            } else {
                                completion(2, null, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUserInfo>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null, null)
            }

        })
    }

    // 프로필 변경
    fun requestUpdateUser(usernick: String, image_id: Long?, completion: (Int) -> Unit) {
        val req = ReqUpdateUser(usernick, image_id)
        val call = service?.requestUpdateUser(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                if (response.body()?.errCode == 1007) {
                                    completion(1) // 닉네임 변경 중 오류
                                } else {
                                    completion(2)
                                }
                            }
                        }
                        else -> {
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }
            }
        )
    }

    // 이미지 업로드
    fun requestUploadImage(file: File, completion: (Int, image_id: Long?) -> Unit) {

        val fileBody = MultipartBody.Part.createFormData(
            "image", file.name, file.asRequestBody("image/jpeg".toMediaType())
        )

        val call = service?.requestUploadImage(
            fileBody
        ) ?: return

        call.enqueue(object : Callback<ResultUploadImage> {
            override fun onResponse(
                call: Call<ResultUploadImage>,
                response: Response<ResultUploadImage>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.imageid)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUploadImage>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }
        })
    }

    // 게시물 등록
    fun requestUploadPost(
        catename: String,
        title: String,
        contents: String,
        fee: String,
        lat: String,
        lon: String,
        location: String, completion: (Int) -> Unit
    ) {
        val req = ReqUploadPost(catename, title, contents, fee, lat, lon, location)
        val call = service?.requestUploadPost(req) ?: return

        call.enqueue(object : Callback<ResultUploadPost> {
            override fun onResponse(
                call: Call<ResultUploadPost>,
                response: Response<ResultUploadPost>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUploadPost>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }
}