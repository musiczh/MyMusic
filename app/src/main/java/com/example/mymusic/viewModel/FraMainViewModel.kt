package com.example.mymusic.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusic.model.entity.Music

class FraMainViewModel : ViewModel() {

    companion object{
        const val STATE_PLAYING = 1
        const val STATE_PAUSE = 2
        const val STATE_NULL = 3
    }

    var musicName = MutableLiveData<String>()
    var singerImage = MutableLiveData<Bitmap>()
    var playState = MutableLiveData<Int>()

    var observer = {music:Music->
        musicName.value = music.name
        playState.value = STATE_PLAYING
    }

    init {
        musicName.value = "当前没有播放歌曲"
        playState.value = STATE_NULL
    }
}