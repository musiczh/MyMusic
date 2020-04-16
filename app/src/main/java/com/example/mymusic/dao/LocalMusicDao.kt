package com.example.mymusic.dao

import android.os.Build
import android.provider.MediaStore
import com.example.mymusic.MainActivity
import com.example.mymusic.model.entity.Music

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
            val uri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val music = Music(id,name,artist,album,duration,size,uri)
            mList.add(music)
        }
        cursor.close()
    }
    return mList
}