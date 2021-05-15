package com.basecamp.campong.model

import com.basecamp.campong.utils.Keyword
import com.google.gson.annotations.SerializedName

data class ResultPostList(
    @SerializedName("num")
    val num: Int,

    @SerializedName("data")
    val item: List<Post>,

    @SerializedName(Keyword.ERROR)
    val error: Boolean,

    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,

    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)

data class Post(
    @SerializedName(Keyword.POST_ID)
    val postid: Long,

    @SerializedName(Keyword.CATENAME)
    val catename: String,

    @SerializedName(Keyword.USERNICK)
    val usernick: String,

    @SerializedName(Keyword.TITLE)
    val title: String,

    @SerializedName(Keyword.LOCATION)
    val location: String,

    @SerializedName(Keyword.IMAGE_ID)
    val imageid: Long?,

    @SerializedName(Keyword.UPLOAD_DATE)
    val uploaddate: String,

    @SerializedName(Keyword.FEE)
    val fee: String
)

data class ReqUploadPost(
    @SerializedName(Keyword.CATENAME)
    val catename: String,

    @SerializedName(Keyword.TITLE)
    val title: String,

    @SerializedName(Keyword.CONTENTS)
    val contents: String,

    @SerializedName(Keyword.FEE)
    val fee: String,

    @SerializedName(Keyword.LAT)
    val lat: String,

    @SerializedName(Keyword.LON)
    val lon: String,

    @SerializedName(Keyword.LOCATION)
    val location: String,

    @SerializedName(Keyword.IMAGE_ID)
    val imageid: Long?
)

data class ResultUploadPost(
    @SerializedName(Keyword.POST_ID)
    val postid: Long,

    @SerializedName(Keyword.ERROR)
    val error: Boolean,

    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,

    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)

data class ReqPostView(
    @SerializedName(Keyword.POST_ID)
    val postid: Long
)

data class ResultPostView(
    @SerializedName(Keyword.POST_ID)
    val postid: Long,

    @SerializedName(Keyword.CATENAME)
    val catename: String,

    @SerializedName(Keyword.USERNICK)
    val usernick: String,

    @SerializedName(Keyword.TITLE)
    val title: String,

    @SerializedName(Keyword.LOCATION)
    val location: String,

    @SerializedName(Keyword.IMAGE_ID)
    val imageid: Long?,

    @SerializedName(Keyword.UPLOAD_DATE)
    val uploaddate: String,

    @SerializedName(Keyword.FEE)
    val fee: String,

    @SerializedName(Keyword.CONTENTS)
    val contents: String,

    @SerializedName(Keyword.ERROR)
    val error: Boolean,

    @SerializedName(Keyword.ERROR_MESSAGE)
    val errInfo: String,

    @SerializedName(Keyword.ERROR_CODE)
    val errCode: Int
)