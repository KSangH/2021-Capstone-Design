package com.basecamp.campong.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(interceptor)
        client.interceptors().add(ReceivedCookiesInterceptor()) // 쿠키 Preference에 저장
        client.interceptors().add(AddCookiesInterceptor()) // 저장된 쿠키 가져옴

        if (retrofitClient == null) {

            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }

        return retrofitClient
    }
}