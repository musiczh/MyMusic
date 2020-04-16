package com.example.mymusic.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusic.model.entity.Music
import java.time.Duration

class FragmentPlayViewModel :ViewModel() {

    companion object{
        const val STATE_PLAYING = 1
        const val STATE_PAUSE = 2
        const val STATE_NULL = 0
    }

    var musicName = MutableLiveData<String>()
    var currentTime  = MutableLiveData<Int>()
    var duration  = MutableLiveData<Int>()
    var progress  = MutableLiveData<Int>()
    var state = MutableLiveData<Int>()

    init{
        musicName.value = "当前没有播放音乐"
        currentTime.value = 0
        duration.value = 1
        progress.value = 0
        state.value = STATE_NULL
    }


}