package com.example.mymusic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusic.model.entity.Music

class FragmentPlayViewModel :ViewModel() {

    companion object{
        const val STATE_PLAYING = 1
        const val STATE_PAUSE = 2
        const val STATE_NULL = 0
    }

    var musicName = MutableLiveData<String>()
    var currentTime  = MutableLiveData<Int>()
    var duration  = MutableLiveData<Int>()
    var state = MutableLiveData<Int>()

    var observe = { music:Music->
        musicName.value = music.name
        duration.value = music.duration
        state.value = STATE_PLAYING
        currentTime.value = 0}



    init{
        musicName.value = "当前没有播放音乐"
        currentTime.value = 0
        duration.value = 0
        state.value = STATE_NULL
    }



}