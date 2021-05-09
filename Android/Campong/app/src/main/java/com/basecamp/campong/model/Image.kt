package com.basecamp.campong.model

import com.basecamp.campong.utils.Keyword
import com.google.gson.annotations.SerializedName

data class ResultUploadImage(
    @SerializedName(Keyword.IMAGE_ID)
    val imageid: Long?,

    @SerializedName(Keyword.ERROR)
    val error: Boolean,

    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,

    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)