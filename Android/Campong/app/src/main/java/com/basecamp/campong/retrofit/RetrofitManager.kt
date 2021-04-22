package com.basecamp.campong.retrofit

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val service: RetrofitService? =
        RetrofitClient.getClient("")?.create(RetrofitService::class.java)

}