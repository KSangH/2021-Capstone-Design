package com.basecamp.campong.model

import com.google.gson.annotations.SerializedName

/* 회원가입 요청 */
data class ReqSignup(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("usernick")
    val usernick: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("username")
    val username: String
)

/* 회원가입 결과 */
data class ResultSignup(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val errInfo: String,
    @SerializedName("errorcode")
    val errCode: Int
)

/* 이메일 중복확인 요청 */
data class ReqCheckEmail(
    @SerializedName("email")
    val email: String
)

/* 이메일 중복확인 결과 */
data class ResultCheckEmail(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val errInfo: String,
    @SerializedName("errorcode")
    val errCode: Int
)

/* 닉네임 중복확인 요청*/
data class ReqCheckNick(
    @SerializedName("usernick")
    val usernick: String
)

/* 닉네임 중복확인 결과*/
data class ResultCheckNick(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val errInfo: String,
    @SerializedName("errorcode")
    val errCode: Int
)

/* 로그인 요청 */
data class ReqLogin(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

/* 로그인 결과 */
data class ResultLogin(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val errInfo: String,
    @SerializedName("errorcode")
    val errCode: Int
)

/* 닉네임 변경 요청*/
data class ReqUpdateNick(
    @SerializedName("usernick")
    val usernick: String
)

/* 닉네임 변경 결과*/
data class ResultUpdateNick(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val errInfo: String,
    @SerializedName("errorcode")
    val errCode: Int
)