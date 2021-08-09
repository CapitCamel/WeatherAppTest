package com.example.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.db.entity.WeatherEntity
import com.example.weatherapp.data.model.DataWeather

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "weather"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
