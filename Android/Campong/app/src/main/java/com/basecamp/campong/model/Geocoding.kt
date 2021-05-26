package com.basecamp.campong.model

import com.google.gson.annotations.SerializedName

data class ResultReverseGeocoding(
    @SerializedName("results")
    val results: List<Result>
)

data class Result(
    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: Region,

    @SerializedName("land")
    val land: Land
)

data class Region(
    @SerializedName("area0")
    val area0: Area,
    @SerializedName("area1")
    val area1: Area,
    @SerializedName("area2")
    val area2: Area,
    @SerializedName("area3")
    val area3: Area,
    @SerializedName("area4")
    val area4: Area
)

data class Area(
    @SerializedName("name")
    val name: String
)

data class Land(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("number1")
    val number1: String,
    @SerializedName("number2")
    val number2: String
)