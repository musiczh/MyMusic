package com.example.mymusic.viewModel

import androidx.lifecycle.*
import com.example.mymusic.MainActivity
import com.example.mymusic.model.Repository
import com.example.mymusic.model.entity.Music
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FraHisViewModel:ViewModel() {
    var musicList = MutableLiveData<List<Music>>()
    fun getHisLiveData():LiveData<List<Music>> = Repository.getHisList()
}