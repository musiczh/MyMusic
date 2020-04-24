package com.example.mymusic.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.mymusic.MainActivity
import com.example.mymusic.model.Repository
import com.example.mymusic.model.entity.Music
import java.io.IOException
import kotlin.random.Random

/**
 * 后台播放服务
 * （未完善）
 */
class MusicService : Service() {
    var mMusicList = ArrayList<Music>() //歌曲列表信息
    var currentIndex = -1   //当前播放位置
    private val mMediaPlayer = MediaPlayer() //音频播放类
    private val mBinder = MyBinder()
    private var playMode = 1

    //观察者。当播放歌曲发生改变的时候会通知这些观察者
    val mapObservers = HashMap<String,(music:Music)->Unit>()

   companion object{
       const val LIST_CYCLE_MODE = 1
       const val SINGLE_CYCLE_MODE = 2
       const val RANDOM_MODE = 3
   }
    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()

    }

    /**
     * 播放下一首
     * 根据播放模式去选择下一首歌曲
     */
    fun playNextMusic():Boolean{
        if (mMusicList.size==0||currentIndex==-1) return false
        //更新下标
        when {
            playMode== RANDOM_MODE -> currentIndex = Random.nextInt(mMusicList.size)
            currentIndex==mMusicList.size-1 -> currentIndex = 0
            else -> currentIndex+=1
        }
        playCurrentMusic()
        return true
    }

    inner class MyBinder : Binder() {
        fun playMusicIndex(list:ArrayList<Music>,index:Int){
            if (list != mMusicList) mMusicList = list
            currentIndex = index
            playCurrentMusic()
        }
        fun nextMusic() = playNextMusic()
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
        fun continuePlay() = mMediaPlayer.start()
        fun seekToProcess(process:Int){ if (mMediaPlayer.isPlaying) mMediaPlayer.seekTo(process) }
        fun setPlayMode(mode:Int){
            if (mode in 1..3) playMode = mode
            if (mode == SINGLE_CYCLE_MODE) mMediaPlayer.isLooping = true
        }

        fun currentTime() = if (mMediaPlayer.isPlaying) mMediaPlayer.currentPosition else 0
        fun isPlaying() = mMediaPlayer.isPlaying
        fun getCurrentMusic() =
            if (mMusicList.size == 0){
                Log.d("huan_MusicService_music","返回值是0")
                null
            } else{
                Log.d("huan_MusicService_music","返回值是不是空")
                mMusicList[currentIndex]
            }

        fun subscribe(name:String,observer:(music:Music)->Unit) = mapObservers.put(name,observer)
        fun unSubscribe(name:String) = mapObservers.remove(name)


    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    /**
     * 播放当前歌曲。currentIndex是更新后的下标
     * 播放失败则捕获并显示toast，自动播放下一首
     */
    private fun playCurrentMusic(){
        Log.d("MusicService","播放音乐")
        noticeObserver()
        Repository.insertHisMusic(mMusicList[currentIndex])
        mMediaPlayer.reset()
        //根据版本获取内容uri
        try {
            val parcelFileDescriptor = Repository.getMusicFD(mMusicList[currentIndex])
            mMediaPlayer.setDataSource(parcelFileDescriptor.fileDescriptor)
            mMediaPlayer.prepare()
            mMediaPlayer.start()
            parcelFileDescriptor.close()
        } catch(e:Exception){
            Log.e("huan_MusicService","播放音乐的时候出现了异常$e")
            e.printStackTrace()
            Toast.makeText(MainActivity.mContext,"播放失败自动播放下一首",Toast.LENGTH_SHORT).show()
            playNextMusic()
        }
    }

    /**
     * 初始化MediaPlayer
     */
    private fun initMediaPlayer(){
        mMediaPlayer.setOnCompletionListener {
            if (!mMediaPlayer.isLooping) playNextMusic()
        }
        mMediaPlayer.setOnErrorListener { _, what, extra ->
            Log.e("huan_MediaPlayer_Error","MediaPlayer出错了，what=$what,extra=$extra")
            mMediaPlayer.reset()
            true
        }
        mMediaPlayer.reset()
    }

    //通知观察者播放歌曲变化了
    private fun noticeObserver(){
        mapObservers.forEach{
            it.value(mMusicList[currentIndex])
        }
    }

}
