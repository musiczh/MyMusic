package com.example.mymusic.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.MainActivity

import com.example.mymusic.R
import com.example.mymusic.model.entity.Music
import com.example.mymusic.viewModel.FraMainViewModel

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var mainActivity:MainActivity
    private lateinit var localMusicImage:ImageView
    private lateinit var musicImage:ImageView
    private lateinit var tvMusicName:TextView
    private lateinit var viewModel:FraMainViewModel
    private lateinit var playMusicButton:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mainActivity = activity as MainActivity
        viewBinding(view)
        initViewModel()
        initViewModelData()
        return view
    }

    //初始化viewModel的数据
    private fun initViewModelData(){
        val music: Music? = mainActivity.getCurrentMusic()
        val isPlaying = mainActivity.isPlaying()
        music?.let {
            viewModel.musicName.value = music.name
        }
        isPlaying?.let {
            if (it) viewModel.playState.value = FraMainViewModel.STATE_PLAYING
            else  viewModel.playState.value = FraMainViewModel.STATE_PAUSE
        }

    }


    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(FraMainViewModel::class.java)
        viewModel.musicName.observe(viewLifecycleOwner, Observer{ tvMusicName.text = it })
        viewModel.playState.observe (viewLifecycleOwner, Observer{
            when(it){
                FraMainViewModel.STATE_PLAYING->{
                    mainActivity.continueMusic()
                    playMusicButton.background = mainActivity.getDrawable(R.drawable.pause_black)
                }
                FraMainViewModel.STATE_PAUSE->{
                    mainActivity.pauseMusic()
                    playMusicButton.background = mainActivity.getDrawable(R.drawable.play_black)
                }
            }
        })
        mainActivity.subscribeOnCreate("MainFragment"){
            viewModel.observer(it)
        }
    }

    private fun viewBinding(view:View){
        localMusicImage = view.findViewById(R.id.image_local_music)
        musicImage = view.findViewById(R.id.image_plat_bar_singer)
        tvMusicName = view.findViewById(R.id.text_view_play_bar_song)
        playMusicButton = view.findViewById(R.id.button_play_bar_play)
        tvMusicName.setOnClickListener {
            mainActivity.navigate(R.id.playFragment)
        }
        localMusicImage.setOnClickListener{
            mainActivity.navigate(R.id.localMusicFragment)
        }
        musicImage.setOnClickListener {
            mainActivity.navigate(R.id.playFragment)
        }
        playMusicButton.setOnClickListener() {
            if (viewModel.playState.value==FraMainViewModel.STATE_PLAYING)
                viewModel.playState.value = FraMainViewModel.STATE_PAUSE
            else if (viewModel.playState.value==FraMainViewModel.STATE_PAUSE)
                viewModel.playState.value = FraMainViewModel.STATE_PLAYING

        }
        val imageViewHis = view.findViewById<ImageView>(R.id.image_recent)
        imageViewHis.setOnClickListener { mainActivity.navigate(R.id.historyFragment) }
    }


}
