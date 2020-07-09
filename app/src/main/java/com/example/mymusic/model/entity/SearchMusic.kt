package com.example.mymusic.model.entity

data class SearchMusic(
    val songmid:String,
    val name:String,
    val singer:String,
    val album:String,
    val albumId:String,
    val pay:Boolean
)