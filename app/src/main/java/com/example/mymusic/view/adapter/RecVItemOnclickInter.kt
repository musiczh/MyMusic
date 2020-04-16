package com.example.mymusic.view.adapter

import com.example.mymusic.model.entity.Music

interface RecVItemOnclickInter {
    fun onClick(list:ArrayList<Music>,position:Int)
    fun doubleClick()
    fun onLongClick()
}