package com.example.mymusic.model.entity
import android.net.Uri

/**
 * 音乐列表信息类
 */
data class Music(
    val id :Long,        //歌曲id
    val name:String,    //名字
    val artist:String,  //作者
    val album:String,   //专辑
    val duration:Int,   //持续时长（只有android10才有，低版本为0）
    val size:Long,      //大小
    val uri:String      //文件路径（低于android10版本有效）
)