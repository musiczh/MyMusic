package com.example.mymusic.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.R
import com.example.mymusic.model.entity.Music
import com.example.mymusic.util.getTimeString

/**
 * 本地音乐播放列表recyclerView的适配器
 * 2020.4.15
 */
class LMRcyViewAdapter(private val mMusicList:ArrayList<Music>,
                       private val clickListener:RecVItemOnclickInter):
    RecyclerView.Adapter<LMRcyViewAdapter.LocalMusicHolder>() {

    inner class LocalMusicHolder(view: View):RecyclerView.ViewHolder(view){
        val nameTextView :TextView = view.findViewById(R.id.text_view_lm_music_name)
        val artistTextView:TextView = view.findViewById(R.id.text_view_lm_artist)
        val durationTextView:TextView = view.findViewById(R.id.text_view_lm_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalMusicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_local_music,parent,
            false)
        val holder = LocalMusicHolder(view)
        view.setOnClickListener {
            clickListener.onClick(mMusicList,holder.adapterPosition)
        }
        return holder
    }

    override fun getItemCount(): Int = mMusicList.size


    override fun onBindViewHolder(holder: LocalMusicHolder, position: Int) {
        val music = mMusicList[position]
        holder.artistTextView.text = music.artist
        holder.durationTextView.text = getTimeString(music.duration)
        holder.nameTextView.text = music.name
    }


}