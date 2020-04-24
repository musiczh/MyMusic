package com.example.mymusic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mymusic.model.entity.Music

@Dao
interface HistoryMusicDao {
    @Insert
    fun insertMusic(music: Music)

    @Delete
    fun deleteMusic(music:Music)

    @Query("select * from Music")
    fun getHisList(): LiveData<List<Music>>

    @Query("select * from Music where name = :musicName")
    fun getThisMusic(musicName:String):List<Music>
}