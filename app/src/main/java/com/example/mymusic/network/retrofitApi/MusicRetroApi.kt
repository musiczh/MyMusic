package com.example.mymusic.network.retrofitApi

import com.example.mymusic.network.netbean.MusicUrlBean
import com.example.mymusic.network.netbean.SearchBean
import com.example.mymusic.util.MUSIC_URL
import com.example.mymusic.util.SEARCH_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MusicRetroApi {

    @GET(SEARCH_URL)
    fun searchMusic(@Query("p")p:Int,
                    @Query("n")n:Int,
                    @Query("w")keyword:String,
                    @Query("format")format:String):Call<SearchBean>

    @GET
    fun getPlayAddress(@Url url:String):Call<MusicUrlBean>

}