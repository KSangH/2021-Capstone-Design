package com.basecamp.campong.model

import com.basecamp.campong.utils.Keyword
import com.google.gson.annotations.SerializedName

/* 회원가입 요청 */
data class ReqSignup(
    @SerializedName(Keyword.EMAIL)
    val email: String,

    @SerializedName(Keyword.PASSWORD)
    val password: String,

    @SerializedName(Keyword.USERNICK)
    val usernick: String,

    @SerializedName(Keyword.PHONE)
    val phone: String,

    @SerializedName(Keyword.USERNAME)
    val username: String
)

/* 결과 (기본형) */
data class ResultBase(
    @SerializedName(Keyword.ERROR)
    val error: Boolean,
    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,
    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)

/* 이메일 중복확인 요청 */
data class ReqCheckEmail(
    @SerializedName(Keyword.EMAIL)
    val email: String
)

/* 닉네임 중복확인 요청*/
data class ReqCheckNick(
    @SerializedName(Keyword.USERNICK)
    val usernick: String
)

/* 로그인 요청 */
data class ReqLogin(
    @SerializedName(Keyword.EMAIL)
    val email: String,

    @SerializedName(Keyword.PASSWORD)
    val password: String
)

/* 닉네임 변경 요청*/
data class ReqUpdateNick(
    @SerializedName(Keyword.USERNICK)
    val usernick: String
)