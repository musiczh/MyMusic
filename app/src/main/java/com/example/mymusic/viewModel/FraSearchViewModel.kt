package com.example.mymusic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusic.model.Repository
import com.example.mymusic.model.entity.SearchMusic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FraSearchViewModel:ViewModel() {
    val searchResultList = MutableLiveData<List<SearchMusic>>()

    suspend fun getPlayUrl(songmid:String):String{
        return Repository.getPlayAddress(songmid)
    }

    fun getSearchResult(keyword:String){
        GlobalScope.launch {
            val list = Repository.getSearchList(1,10,keyword)
            searchResultList.postValue(list)
        }
    }

}