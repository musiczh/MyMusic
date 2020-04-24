package com.example.mymusic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusic.model.Repository
import com.example.mymusic.model.entity.Music

class FraLocalViewModel:ViewModel() {
    val mMusicList = MutableLiveData<List<Music>>()

    fun refreshList(){
        mMusicList.value = Repository.getLocalMusicList().toList()
    }
}