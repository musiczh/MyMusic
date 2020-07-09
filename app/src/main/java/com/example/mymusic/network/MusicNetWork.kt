package com.example.mymusic.network

import android.util.Log
import com.example.mymusic.model.entity.SearchMusic
import com.example.mymusic.network.netbean.MusicUrlBean
import com.example.mymusic.network.netbean.SearchBean
import com.example.mymusic.network.retrofitApi.MusicRetroApi
import com.example.mymusic.util.MUSIC_URL
import com.example.mymusic.util.SEARCH_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.lang.StringBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MusicNetWork {
    suspend fun getSearchMusic(pagination:Int,num:Int,keyword:String):List<SearchMusic>{
        return suspendCoroutine { continuation ->
            RetrofitManager.retrofit.create(MusicRetroApi::class.java)
                .searchMusic(pagination,num,keyword,"json")
                .enqueue(object : Callback<SearchBean>{
                    override fun onFailure(call: Call<SearchBean>, t: Throwable) {
                        Log.e("huan_MusicNetWork","getSearchMusic onFailure")
                        continuation.resumeWithException(RuntimeException("getSearchMusic is onFailure"))
                    }

                    override fun onResponse(
                        call: Call<SearchBean>,
                        response: Response<SearchBean>
                    ) {
                        if (response.body()==null)
                            continuation.resumeWithException(RuntimeException("getSearchMusic's body is null"))
                        response.body()?.let { continuation.resume(getSearchMusicList(it)) }
                    }

                })
        }
    }

    private fun getSearchMusicList(searchBean: SearchBean):List<SearchMusic>{
        val list = searchBean.data.song.list
        val listResult = ArrayList<SearchMusic>()
        list.forEach {
            val name = it.songname
            val songmid = it.songmid
            val album = it.albumname
            val albumId = it.albummid
            val singer = StringBuilder().run {
                val singerList = it.singer
                singerList.forEach { it1 ->
                    append("${it1.name}/")
                }
                replace(length-1,length,"")
                toString()
            }
            val pay = it.pay.payplay==1
            val searchMusic = SearchMusic(songmid,name,singer,album,albumId,pay)
            listResult.add(searchMusic)
        }
        return listResult.toList()
    }

    suspend fun getPlayAddress(songmid:String):String{
        val data = StringBuilder().run {
            append(MUSIC_URL)
            append("%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22")
            append(songmid)
            append("%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D")
            toString()
        }
        return suspendCoroutine {
            RetrofitManager.retrofit.create(MusicRetroApi::class.java)
                .getPlayAddress(data)
                .enqueue(object : Callback<MusicUrlBean>{
                    override fun onFailure(call: Call<MusicUrlBean>, t: Throwable) {
                        Log.e("huan_MusicNetWork","getPlayAddress onFailure")
                        it.resumeWithException(RuntimeException("getPlayAddress is onFailure"))
                    }

                    override fun onResponse(
                        call: Call<MusicUrlBean>,
                        response: Response<MusicUrlBean>
                    ) {
                        if (response.body()==null)
                            it.resumeWithException(RuntimeException("getPlayAddress's body is null"))
                        response.body()?.let { musicUrlBean ->
                            it.resume(StringBuilder().run {
                                append(musicUrlBean.req_0.data.sip[0])
                                append(musicUrlBean.req_0.data.testfile2g)
                                toString()
                            })
                        }
                    }

                })
        }
    }
}