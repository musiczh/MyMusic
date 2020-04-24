package com.example.mymusic.model.entity
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 音乐列表信息类
 */
@Entity

data class Music(
    val id :Long,       //歌曲id
    @PrimaryKey
    val name:String,    //名字
    val artist:String,  //作者
    val album:String,   //专辑
    val duration:Int,   //持续时长（只有android10才有，低版本为0）
    val size:Long,      //大小
    val uri:String      //网络地址uri，非本地歌曲有效
)