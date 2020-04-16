package com.example.mymusic.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.MainActivity

import com.example.mymusic.R
import com.example.mymusic.dao.getLocalMusicList
import com.example.mymusic.model.entity.Music
import com.example.mymusic.view.adapter.LMRcyViewAdapter
import com.example.mymusic.view.adapter.RecVItemOnclickInter
import java.util.*
import kotlin.collections.ArrayList

class LocalMusicFragment : Fragment() {
    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter:LMRcyViewAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_local_music, container, false)
        mainActivity = activity as MainActivity
        recyclerView = view.findViewById(R.id.recycler_view_local_music)
        val buttonBack = view.findViewById<Button>(R.id.button_back_top_bar)
        val textViewTittle = view.findViewById<TextView>(R.id.text_view_top_bar_tittle)
        buttonBack.setOnClickListener{
            val mainActivity = activity as MainActivity
            mainActivity.navBackStack()
        }
        textViewTittle.text = "本地音乐"
        setMusic()
        return view
    }

    private fun setMusic(){
        recyclerView.layoutManager = LinearLayoutManager(context)
        val list = getLocalMusicList()
        recyclerView.adapter = LMRcyViewAdapter(list,object :RecVItemOnclickInter{
            override fun onClick(list: ArrayList<Music>, position: Int) {
                Log.d("LocalMusicFragment","接收到点击$position")
                mainActivity.playMusic(list,position)
            }

            override fun doubleClick() {
            }

            override fun onLongClick() {
            }

        })
    }
}
