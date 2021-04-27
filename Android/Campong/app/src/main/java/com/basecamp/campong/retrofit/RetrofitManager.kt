package com.basecamp.campong.retrofit

import android.util.Log
import com.basecamp.campong.model.ResultSignup
import com.basecamp.campong.utils.Constants
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val service: RetrofitService? =
        RetrofitClient.getClient("")?.create(RetrofitService::class.java)

    fun requestSignup(
        email: String, password: String, usernick: String, phone: String, username: String
    ) {
        val call = service?.requestSignup(
            email, password, usernick, phone, username
        ) ?: return

        call.enqueue(
            object : retrofit2.Callback<ResultSignup> {
                override fun onResponse(
                    call: Call<ResultSignup>,
                    response: Response<ResultSignup>
                ) {
                    Log.d(Constants.TAG, response.raw().toString())
                }

                override fun onFailure(call: Call<ResultSignup>, t: Throwable) {
                    Log.d(Constants.TAG, t.toString())
                }

            }
        )
    }
}