package com.example.mymusic.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.R
import com.example.mymusic.model.entity.SearchMusic

class SMRcyViewAdapter(private val mMusicList:ArrayList<SearchMusic>,
                       private val clickListener: RecVItemOnclickInter<SearchMusic>
):
    RecyclerView.Adapter<SMRcyViewAdapter.MusicHolder>() {

    inner class MusicHolder(view: View): RecyclerView.ViewHolder(view){
        val nameTextView : TextView = view.findViewById(R.id.text_view_lm_music_name)
        val artistTextView: TextView = view.findViewById(R.id.text_view_lm_artist)
        val durationTextView: TextView = view.findViewById(R.id.text_view_lm_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_local_music,parent,
            false)
        val holder = MusicHolder(view)
        view.setOnClickListener {
            clickListener.onClick(mMusicList,holder.adapterPosition)
        }
        return holder
    }

    override fun getItemCount(): Int = mMusicList.size


    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        val music = mMusicList[position]
        holder.artistTextView.text = music.singer
       // holder.durationTextView.text = getTimeString(0)
        holder.nameTextView.text = music.name
    }

    fun changeData(list: List<SearchMusic>){
        mMusicList.clear()
        mMusicList.addAll(list)
        this.notifyDataSetChanged()
    }

}