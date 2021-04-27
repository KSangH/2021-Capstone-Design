package com.basecamp.campong.retrofit

import com.basecamp.campong.model.ResultSignup
import com.basecamp.campong.utils.API
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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

    // @POST()
    @FormUrlEncoded
    @POST(API.USER_SIGN_UP)
    fun requestSignup(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("usernick") usernick: String,
        @Field("phone") phone: String,
        @Field("username") username: String
    ): Call<ResultSignup>
}