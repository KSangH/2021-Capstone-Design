package com.basecamp.campong.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * FROM locationList")
    fun getAll(): LiveData<List<Location>>
}