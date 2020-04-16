package com.example.mymusic.view.fragment


import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.mymusic.MainActivity
import com.example.mymusic.R
import com.example.mymusic.model.entity.Music
import com.example.mymusic.util.getTimeString
import com.example.mymusic.util.setTimeTask
import com.example.mymusic.viewModel.FragmentPlayViewModel
import java.util.*
import java.util.concurrent.ScheduledFuture

/**
 * A simple [Fragment] subclass.
 */
class PlayFragment : Fragment() {
    lateinit var viewModel: FragmentPlayViewModel
    lateinit var mainActivity: MainActivity
    private var scheduledFuture: ScheduledFuture<*>? = null
    private var ifRefreshSeekBar = true

    lateinit var buttonBack :Button
    lateinit var textViewTittle :TextView
    lateinit var seekBar :SeekBar
    lateinit var tvCurrentTime :TextView
    lateinit var tvDuration :TextView
    lateinit var buttonPrevious:Button
    lateinit var buttonNext: Button
    lateinit var buttonPlay:Button

    private val handle = Handler(){
        val bundle = it.data
        val progress = bundle.getInt("progress")
        val current = bundle.getString("current")
        if (viewModel.state.value==FragmentPlayViewModel.STATE_PLAYING&&ifRefreshSeekBar){
            seekBar.progress = progress
            tvCurrentTime.text = current
        }
        true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        init(view)
        return view
    }

    private fun init(view:View){
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)
            .get(FragmentPlayViewModel::class.java)
        initControls(view)
        initLiveBinding()
        initViewModelData()
        startSeekTask()

        mainActivity.subscribe("play"){
            viewModel.let {self->
                self.musicName.value = it.name
                self.duration.value = it.duration
                self.progress.value = 0
                self.state.value = FragmentPlayViewModel.STATE_PLAYING
                self.currentTime.value = 0
            }
        }
    }

    //初始化数据绑定
    private fun initLiveBinding(){
        viewModel.let{ self ->
            self.currentTime.observe(this){ tvCurrentTime.text = getTimeString(it) }
            self.currentTime.observe(this){ tvCurrentTime.text = getTimeString(it) }
            self.duration.observe(this){ tvDuration.text = getTimeString(it)}
            self.musicName.observe(this){ textViewTittle.text = it}
            self.progress.observe(this){ seekBar.progress = it}
            self.state.observe(this){
                if (it==FragmentPlayViewModel.STATE_PAUSE||it==FragmentPlayViewModel.STATE_NULL)
                    buttonPlay.background = mainActivity.getDrawable(R.drawable.play_black)
                else buttonPlay.background = mainActivity.getDrawable(R.drawable.pause_black)
            }
        }

    }

    //初始化viewModel的数据
    private fun initViewModelData(){
        val music: Music? = mainActivity.getCurrentMusic()
        val cTime = mainActivity.getCurrentTime()
        val isPlaying = mainActivity.isPlaying()
        music?.let {
            viewModel.duration.value = music.duration
            viewModel.musicName.value = music.name
            viewModel.progress.value = cTime*100/music.duration
        }
        viewModel.apply {
            currentTime.value = cTime
            state.value = when{
                isPlaying -> FragmentPlayViewModel.STATE_PLAYING
                !isPlaying&&cTime>0 -> FragmentPlayViewModel.STATE_PAUSE
                else -> FragmentPlayViewModel.STATE_NULL
            }
        }
    }

    //初始化控件
    private fun initControls(view:View){
        buttonBack = view.findViewById(R.id.button_back_fragment_play_top_bar)
        textViewTittle = view.findViewById(R.id.text_view_fragment_play_top_bar_tittle)
        seekBar = view.findViewById(R.id.seek_bar_fragment_play)
        tvCurrentTime = view.findViewById(R.id.text_view_fragment_play_current_time)
        tvDuration = view.findViewById(R.id.text_view_fragment_play_duration)
        buttonNext = view.findViewById(R.id.button_fragment_play_next)
        buttonPrevious = view.findViewById(R.id.button_fragment_play_previous)
        buttonPlay = view.findViewById(R.id.button_fragment_play_play)
        buttonBack.setOnClickListener {mainActivity.navBackStack()}
        buttonNext.setOnClickListener { mainActivity.playNextMusic() }
        buttonPrevious.setOnClickListener { mainActivity.playPreviousMusic() }
        buttonPlay.setOnClickListener {
            when (viewModel.state.value) {
                FragmentPlayViewModel.STATE_PLAYING -> {
                    mainActivity.pauseMusic()
                    viewModel.state.value = FragmentPlayViewModel.STATE_PAUSE
                }
                FragmentPlayViewModel.STATE_PAUSE -> {
                    mainActivity.continueMusic()
                    viewModel.state.value  = FragmentPlayViewModel.STATE_PLAYING
                }
            }
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.currentTime.value = viewModel.duration.value!!*progress/100
                viewModel.progress.value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                ifRefreshSeekBar = false

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                ifRefreshSeekBar = true
                if(seekBar!=null)
                     mainActivity.seekTo(seekBar.progress)
            }

        })

    }


    //进度条更新
    private fun startSeekTask(){
        scheduledFuture = setTimeTask(1){
            val current = mainActivity.getCurrentTime()
            val progress = current*100/ viewModel.duration.value!!
            val currentString = getTimeString(current)
            val msg = Message.obtain()
            val bundle = Bundle()
            bundle.putInt("progress",progress)
            bundle.putString("current",currentString)
            msg.data=bundle
            handle.sendMessage(msg)
            Log.d("huan_seekBar","循环一次currentTime=$current")
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("huan_PlayFragment","执行了onDestroy")
        scheduledFuture?.cancel(true)
    }
}
