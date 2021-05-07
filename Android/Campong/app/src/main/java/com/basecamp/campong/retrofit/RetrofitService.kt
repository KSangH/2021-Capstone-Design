package com.basecamp.campong.retrofit

import com.basecamp.campong.model.*
import com.basecamp.campong.utils.API
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

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
    ): Call<ResultLogin>

    @Headers("content-type: application/json")
    @POST(API.USER_UPDATE_NICK)
    fun requestUpdateNick(
        @Body body: ReqUpdateNick
    ): Call<ResultUpdateNick>

    @Multipart
    @POST(API.UPLOAD_IMAGE)
    fun requestUploadImage(
        @Part file: MultipartBody.Part
    ): Call<ResultUploadImage>
}