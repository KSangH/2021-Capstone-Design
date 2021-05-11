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
    ): Call<ResultBase>

    @Headers("content-type: application/json")
    @POST(API.USER_CHECK_EMAIL)
    fun requestCheckEmail(
        @Body body: ReqCheckEmail
    ): Call<ResultBase>

    @Headers("content-type: application/json")
    @POST(API.USER_CHECK_NICK)
    fun requestCheckNick(
        @Body body: ReqCheckNick
    ): Call<ResultBase>

    @Headers("content-type: application/json")
    @POST(API.USER_LOGIN)
    fun requestLogin(
        @Body body: ReqLogin
    ): Call<ResultBase>

    @Headers("content-type: application/json")
    @POST(API.USER_LOGOUT)
    fun requestLogout(
    ): Call<ResultBase>

    @Headers("content-type: application/json")
    @POST(API.USER_UPDATE_USER)
    fun requestUpdateUser(
        @Body body: ReqUpdateUser
    ): Call<ResultBase>

    @POST(API.USER_INFO)
    fun requestUserInfo(): Call<ResultUserInfo>

    @Multipart
    @POST(API.UPLOAD_IMAGE)
    fun requestUploadImage(
        @Part image: MultipartBody.Part
    ): Call<ResultUploadImage>

    @Headers("content-type: application/json")
    @GET(API.POST_LIST)
    fun requestPostList(
        @Query("pagenum") pagenum: Int
    ): Call<ResultPostList>

    @Headers("content-type: application/json")
    @POST(API.POST_UPLOAD)
    fun requestUploadPost(
        @Body body: ReqUploadPost
    ): Call<ResultUploadPost>

    @Headers("content-type: application/json")
    @POST(API.POST_VIEW)
    fun requestPostView(
        @Body body: ReqPostView
    ): Call<ResultPostView>


}