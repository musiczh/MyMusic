package com.example.mymusic.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.MainActivity
import com.example.mymusic.R
import com.example.mymusic.model.entity.Music
import com.example.mymusic.view.adapter.LMRcyViewAdapter
import com.example.mymusic.view.adapter.RecVItemOnclickInter
import com.example.mymusic.viewModel.FraLocalViewModel
import kotlin.collections.ArrayList

class LocalMusicFragment : Fragment() {
    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter:LMRcyViewAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel:FraLocalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_local_music, container, false)
        mainActivity = activity as MainActivity
        initView(view)
        dataBind()
        initRecyclerView()
        if (viewModel.mMusicList.value == null) viewModel.refreshList()
        return view
    }

    private fun initView(view:View){
        recyclerView = view.findViewById(R.id.recycler_view_local_music)
        val buttonBack = view.findViewById<Button>(R.id.button_back_top_bar)
        val textViewTittle = view.findViewById<TextView>(R.id.text_view_top_bar_tittle)
        buttonBack.setOnClickListener{
            mainActivity.navBackStack()
        }
        textViewTittle.text = "本地音乐"
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        val list = ArrayList<Music>()
        viewModel.mMusicList .value?.let { list.addAll(it) }
        adapter = LMRcyViewAdapter(list,object :RecVItemOnclickInter<Music>{
            override fun onClick(list: ArrayList<Music>, position: Int) {
                val arrayList = ArrayList<Music>(list)
                mainActivity.mediaService.playMusic(arrayList,position)
            }
            override fun doubleClick() {
            }
            override fun onLongClick() {
            }
        })
        recyclerView.adapter = adapter
    }

    private fun dataBind(){
        viewModel = ViewModelProvider(this).get(FraLocalViewModel::class.java)
        viewModel.mMusicList.observe(viewLifecycleOwner, Observer {
            adapter.changeData(it)
        })
    }
}
