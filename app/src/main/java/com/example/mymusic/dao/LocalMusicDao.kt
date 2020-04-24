package com.example.mymusic.dao

import android.content.ContentUris
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import com.example.mymusic.MainActivity
import com.example.mymusic.model.entity.Music
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.lang.RuntimeException

object LocalMusicDao{
    private const val TAG = "LocalMusicDao"

    /**
     * 获取本地音乐信息
     */
    fun getLocalMusicList():ArrayList<Music>{
        val mList = ArrayList<Music>()

        val mContentResolver  =  MainActivity.mContext.contentResolver
        val cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,null,null, null)

        if (cursor!=null){
            while (cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val duration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                } else 0
                val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                val uri = "http://"
                val music = Music(id,name,artist,album,duration,size,uri)
                mList.add(music)
            }
            cursor.close()
        }
        return mList
    }

    /**
     * 获取本地音乐的FileDescriptor
     */
    fun getLocalMusicFD(music:Music) :ParcelFileDescriptor{
        val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            music.id)
        val parcelFileDescriptor  = MainActivity.mContext.contentResolver.openFileDescriptor(uri,"r")
        if (parcelFileDescriptor==null){
            Log.e(TAG,"getMusicFD方法没有找到文件，parcelFileDescriptor==null")
            throw RuntimeException("parcelFileDescriptor is null")
        } else {
            return parcelFileDescriptor
        }
    }



}


