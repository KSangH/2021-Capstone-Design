package com.basecamp.campong.model

import com.google.gson.annotations.SerializedName

data class ResultPostList(
    @SerializedName("n")
    val n: Int,

    val item: List<Post>
)

data class Post(
    @SerializedName("postid")
    val postid: String,

    @SerializedName("catename")
    val catename: String,

    @SerializedName("usernick")
    val usernick: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("itemphoto")
    val itemphoto: String,

    @SerializedName("uploaddate")
    val uploaddate: String,

    @SerializedName("fee")
    val fee: String
)