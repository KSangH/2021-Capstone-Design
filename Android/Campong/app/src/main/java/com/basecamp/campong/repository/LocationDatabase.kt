package com.basecamp.campong.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.basecamp.campong.model.Location
import com.basecamp.campong.model.LocationDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        private var INSTANCE: LocationDatabase? = null
        private const val DATABASE_NAME = "location_database"
        fun getDatabase(context: Context, scope: CoroutineScope): LocationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    DATABASE_NAME
                )
                    .createFromAsset("databases/locationList.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}