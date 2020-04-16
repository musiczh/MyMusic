package com.example.mymusic.service

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import com.example.mymusic.MainActivity
import com.example.mymusic.model.entity.Music

/**
 * 后台播放服务
 * （未完善）
 */
class MusicService : Service() {
    var mMusicList = ArrayList<Music>() //歌曲列表信息
    var currentIndex = -1   //当前播放位置
    private val mMediaPlayer = MediaPlayer() //音频播放类
    private val mBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        mMediaPlayer.reset()
    }

    inner class MyBinder : Binder() {
        fun playMusicIndex(list:ArrayList<Music>,index:Int){
            if (list != mMusicList) mMusicList = list
            currentIndex = index
            playCurrentMusic()
        }

        fun nextMusic():Boolean{
            if (mMusicList.size==0||currentIndex==-1) return false
            //更新下标
            if (currentIndex==mMusicList.size-1) currentIndex = 0
            else currentIndex+=1
            playCurrentMusic()
            return true
        }

        fun previousMusic():Boolean{
            if (mMusicList.size==0||currentIndex==-1) return false
            //更新下标
            if (currentIndex==0) currentIndex = mMusicList.size-1
            else currentIndex-=1
            playCurrentMusic()
            return true
        }

        fun pausePlay(){
            if (mMediaPlayer.isPlaying) mMediaPlayer.pause()
        }

        fun seekToProcess(process:Int){
            if (mMediaPlayer.isPlaying) mMediaPlayer.seekTo(process)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    private fun playCurrentMusic(){
        Log.d("MusicService","播放音乐")
        mMediaPlayer.reset()
        //根据版本获取内容uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mMusicList[currentIndex].id)
            val parcelFileDescriptor  = MainActivity.mContext.contentResolver.openFileDescriptor(uri,"r")
            mMediaPlayer.setDataSource(parcelFileDescriptor?.fileDescriptor)
            parcelFileDescriptor?.close()
        }else{
            val uri = mMusicList[currentIndex].uri
            mMediaPlayer.setDataSource(uri)
        }
        mMediaPlayer.prepare()
        mMediaPlayer.start()

    }


}
