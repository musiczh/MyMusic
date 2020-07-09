package com.example.mymusic.model

import android.os.ParcelFileDescriptor
import androidx.lifecycle.LiveData
import com.example.mymusic.dao.AppDatabase
import com.example.mymusic.dao.LocalMusicDao
import com.example.mymusic.model.entity.Music
import com.example.mymusic.model.entity.SearchMusic
import com.example.mymusic.network.MusicNetWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object  Repository {
    val appDatabase = AppDatabase.getAppDatabase()
    val historyMusicDao = appDatabase.hisMusicDao()
    val scope = CoroutineScope(Job())

    fun getMusicFD(music: Music):ParcelFileDescriptor{
        return LocalMusicDao.getLocalMusicFD(music)
    }

    fun getLocalMusicList():ArrayList<Music>{
        return LocalMusicDao.getLocalMusicList()
    }

    fun insertHisMusic(music: Music){
        scope.launch {
            historyMusicDao.deleteMusic(music)
            historyMusicDao.insertMusic(music)
        }

    }

    fun deleteHisMusic(music: Music){
        scope.launch {
            historyMusicDao.deleteMusic(music)
        }
    }

    fun getHisList():LiveData<List<Music>>{
        return historyMusicDao.getHisList()
    }

    suspend fun getSearchList(pagination:Int,num:Int,keyword:String):List<SearchMusic>{
        return MusicNetWork.getSearchMusic(pagination, num, keyword)
    }

    suspend fun getPlayAddress(songmid:String):String{
        return MusicNetWork.getPlayAddress(songmid)
    }
}