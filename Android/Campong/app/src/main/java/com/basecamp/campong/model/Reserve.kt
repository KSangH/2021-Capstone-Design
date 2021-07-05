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
    @SerializedName(Keyword.REQUEST_STATE)
    val requeststate: Int,

    @SerializedName(Keyword.PAGE_NUM)
    val pageNum: Int
)

// 예약내역 Response
data class ResultReserveList(
    @SerializedName("num1")
    val num1: Int,

    @SerializedName("num2")
    val num2: Int,

    @SerializedName("num3")
    val num3: Int,

    @SerializedName("num4")
    val num4: Int,

    @SerializedName("num5")
    val num5: Int,

    @SerializedName("data")
    val data: List<ReserveItem>,

    @SerializedName(Keyword.ERROR)
    val error: Boolean,

    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,

    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)

data class ReserveItem(
    @SerializedName(Keyword.RESERVE_ID)
    val reserveid: Long,

    @SerializedName(Keyword.CATENAME)
    val catename: String,

    @SerializedName(Keyword.TITLE)
    val title: String,

    @SerializedName(Keyword.LOCATION)
    val location: String,

    @SerializedName(Keyword.RENTAL_DATE)
    val rentaldate: String,

    @SerializedName(Keyword.RETURN_DATE)
    val returndate: String,

    @SerializedName(Keyword.FEE)
    val fee: String,

    @SerializedName(Keyword.STATE)
    val state: Int,

    @SerializedName(Keyword.IMAGE_ID)
    val imageid: Long,

    @SerializedName(Keyword.USERNICK)
    val usernick: String,
)

data class ReqReserveState(
    @SerializedName(Keyword.RESERVE_ID)
    val reserveid: Long
)

data class ResultReserveView(
    @SerializedName("post")
    val post: Post,

    @SerializedName("reserve")
    val reserveItem: ReserveItem,

    @SerializedName(Keyword.ERROR)
    val error: Boolean,

    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,

    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)