package com.basecamp.campong.retrofit

import android.util.Log
import com.basecamp.campong.model.*
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Response

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
            object : retrofit2.Callback<ResultSignup> {
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
            object : retrofit2.Callback<ResultCheckEmail> {
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
            object : retrofit2.Callback<ResultCheckNick> {
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


}