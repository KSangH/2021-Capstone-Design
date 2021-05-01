package com.basecamp.campong.model

import com.google.gson.annotations.SerializedName

data class ReqSignUp(
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