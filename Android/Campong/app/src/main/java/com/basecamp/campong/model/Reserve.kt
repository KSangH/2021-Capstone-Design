package com.basecamp.campong.model

import com.basecamp.campong.utils.Keyword
import com.google.gson.annotations.SerializedName

data class ReqReserveInit(
    @SerializedName(Keyword.POST_ID)
    val postid: Long
)

data class ReqReserveRequest(
    @SerializedName(Keyword.POST_ID)
    val postid: Long,
    @SerializedName(Keyword.RENTAL_DATE)
    val rentaldate: String,
    @SerializedName(Keyword.RETURN_DATE)
    val returndate: String
)

data class ReqReserveList(
    @SerializedName(Keyword.STATE)
    val state: Int
)

data class ReqReserveState(
    @SerializedName(Keyword.RESERVE_ID)
    val reserveid: Long
)