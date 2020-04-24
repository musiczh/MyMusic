package com.example.mymusic.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymusic.MainActivity
import com.example.mymusic.model.entity.Music

@Database(version = 1,entities = [Music::class])
abstract class AppDatabase:RoomDatabase() {
    abstract fun hisMusicDao():HistoryMusicDao

    companion object{
        private var instance:AppDatabase?=null
        @Synchronized
        fun getAppDatabase():AppDatabase{
            instance?.let { return it }
            return Room.databaseBuilder(MainActivity.mContext,
                                        AppDatabase::class.java,
                                    "app_database")
                .build().apply { instance = this }
        }
    }
}