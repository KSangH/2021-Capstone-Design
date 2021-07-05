package com.basecamp.campong.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationList")
data class Location(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "location")
    val location: String
)