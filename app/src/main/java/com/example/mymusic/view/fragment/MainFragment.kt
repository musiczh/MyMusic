package com.example.mymusic.view.fragment

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.mymusic.MainActivity

import com.example.mymusic.R

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var mainActivity:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mainActivity = activity as MainActivity
        val localMusicImage = view.findViewById<ImageView>(R.id.image_local_music)
        val playImage = view.findViewById<ImageView>(R.id.image_plat_bar_singer)
        localMusicImage.setOnClickListener{
            Log.d("huan:MainFragment","点击了图标")
            mainActivity.navigate(R.id.localMusicFragment)
        }
        playImage.setOnClickListener {
            mainActivity.navigate(R.id.playFragment)
        }

        return view
    }
}
