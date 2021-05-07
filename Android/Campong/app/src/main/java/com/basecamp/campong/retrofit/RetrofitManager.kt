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
            object : Callback<ResultSignup> {
                override fun onResponse(
                    call: Call<ResultSignup>,
                    response: Response<ResultSignup>
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

                override fun onFailure(call: Call<ResultSignup>, t: Throwable) {
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
            object : Callback<ResultCheckEmail> {
                override fun onResponse(
                    call: Call<ResultCheckEmail>,
                    response: Response<ResultCheckEmail>
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

                override fun onFailure(call: Call<ResultCheckEmail>, t: Throwable) {
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
            object : Callback<ResultCheckNick> {
                override fun onResponse(
                    call: Call<ResultCheckNick>,
                    response: Response<ResultCheckNick>
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

                override fun onFailure(call: Call<ResultCheckNick>, t: Throwable) {
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

        call.enqueue(object : Callback<ResultLogin> {
            override fun onResponse(call: Call<ResultLogin>, response: Response<ResultLogin>) {
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

            override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 닉네임 변경
    fun requestUpdateNick(usernick: String, completion: (Int) -> Unit) {
        val req = ReqUpdateNick(usernick)
        val call = service?.requestUpdateNick(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultUpdateNick> {
                override fun onResponse(
                    call: Call<ResultUpdateNick>,
                    response: Response<ResultUpdateNick>
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

                override fun onFailure(call: Call<ResultUpdateNick>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }
            }
        )
    }

    fun requestUploadImage(file: File, completion: (Int) -> Unit) {

        val fileBody = MultipartBody.Part.createFormData(
            "photo", file.name, file.asRequestBody("image/*".toMediaType())
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

            override fun onFailure(call: Call<ResultUploadImage>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }
        })
    }
}