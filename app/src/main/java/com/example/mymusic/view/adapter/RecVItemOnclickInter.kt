package com.example.mymusic.view.adapter

import com.example.mymusic.model.entity.Music

interface  RecVItemOnclickInter<T> {
    fun onClick(list:ArrayList<T>,position:Int)
    fun doubleClick()
    fun onLongClick()
}