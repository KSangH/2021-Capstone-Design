package com.basecamp.campong.model

import com.google.gson.annotations.SerializedName

data class ResultUploadImage(
    @SerializedName("imageid")
    val imageid: String,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val errInfo: String,

    @SerializedName("errorcode")
    val errCode: Int
)