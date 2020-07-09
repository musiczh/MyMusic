package com.example.mymusic.view.activity

import android.util.Log
import com.example.mymusic.model.entity.Music
import com.example.mymusic.service.MusicService
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MediaService() {
    private lateinit var mBinder : MusicService.MyBinder
    private val observes = HashMap<String,(music: Music)->Unit>()

    fun setBinder(binder: MusicService.MyBinder){ mBinder = binder}

    fun playMusic(list:ArrayList<Music>, position:Int){
        Log.d("MainActivity","播放音乐$position")
        mBinder.playMusicIndex(list,position) }
    fun playNextMusic() = mBinder.nextMusic()
    fun playPreviousMusic() = mBinder.previousMusic()
    fun pauseMusic() = mBinder.pausePlay()
    fun continueMusic() = mBinder.continuePlay()
    fun getCurrentTime():Int = mBinder.currentTime()
    fun subscribe(name: String,observer:(music: Music)->Unit) = mBinder.subscribe(name,observer)
    fun unSubscribe(name:String) = mBinder.unSubscribe(name)
    fun getCurrentMusic() = mBinder.getCurrentMusic()
    fun isPlaying() = mBinder.isPlaying()
    fun seekTo(progress:Int)=mBinder.seekToProcess(progress)
    fun playMusicWithUrl(url:String) = mBinder.playMusicWithUrl(url)

    //延迟订阅
    fun subscribeOnCreate(name: String,observer:(music: Music)->Unit) = observes.put(name,observer)
    fun putObserve(){
        observes.forEach {
            subscribe(it.key,it.value)
        }
    }

}