package com.basecamp.campong.model

import com.google.gson.annotations.SerializedName

data class ReqLogin(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)