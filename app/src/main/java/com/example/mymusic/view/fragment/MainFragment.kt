package com.example.mymusic.view.fragment

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val localMusicImage = view.findViewById<ImageView>(R.id.image_local_music)
        localMusicImage.setOnClickListener{
            Log.d("huan:MainFragment","点击了图标")
            val activity = activity as MainActivity
            activity.navigate(R.id.localMusicFragment)
        }

        return view
    }
}
