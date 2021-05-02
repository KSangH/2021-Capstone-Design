package com.basecamp.campong.retrofit

import com.basecamp.campong.model.*
import com.basecamp.campong.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    // @GET()
//    fun getVillageFcst(
//        @Query("base_date") base_date: String,
//        @Query("base_time") base_time: String,
//        @Query("nx") nx: Int,
//        @Query("ny") ny: Int,
//
//        // fixed value
//        @Query("serviceKey", encoded = true)
//        @Query("dataType") dataType: String = "JSON"
//    ): Call<>

    @Headers("content-type: application/json")
    @POST(API.USER_SIGN_UP)
    fun requestSignup(
        @Body body: ReqSignup
    ): Call<ResultSignup>

    @Headers("content-type: application/json")
    @POST(API.USER_CHECK_EMAIL)
    fun requestCheckEmail(
        @Body body: ReqCheckEmail
    ): Call<ResultCheckEmail>

    @Headers("content-type: application/json")
    @POST(API.USER_CHECK_NICK)
    fun requestCheckNick(
        @Body body: ReqCheckNick
    ): Call<ResultCheckNick>

    @Headers("content-type: application/json")
    @POST(API.USER_LOGIN)
    fun requestLogin(
        @Body body: ReqLogin
    ): Call<ResultSignup> // TODO 세션 관련
}