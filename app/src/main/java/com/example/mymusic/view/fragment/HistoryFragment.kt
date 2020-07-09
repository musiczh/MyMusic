package com.example.mymusic.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.MainActivity

import com.example.mymusic.R
import com.example.mymusic.model.entity.Music
import com.example.mymusic.view.adapter.LMRcyViewAdapter
import com.example.mymusic.view.adapter.RecVItemOnclickInter
import com.example.mymusic.viewModel.FraHisViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LMRcyViewAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: FraHisViewModel
    private lateinit var liveData: LiveData<List<Music>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        mainActivity = activity as MainActivity
        initView(view)
        dataBind()
        initRecyclerView()
        return view
    }

    private fun initView(view:View){
        recyclerView = view.findViewById(R.id.recycler_view_music_list)
        val tvTittle =  view.findViewById<TextView>(R.id.text_view_top_bar_tittle)
        tvTittle.text = "历史播放记录"
        val btnBack = view.findViewById<Button>(R.id.button_back_top_bar)
        btnBack.setOnClickListener { mainActivity.navBackStack() }
    }

    private fun dataBind(){
        viewModel = ViewModelProvider(this).get(FraHisViewModel::class.java)
        viewModel.musicList.observe(viewLifecycleOwner, Observer {
            adapter.changeData(it)
    })
        liveData = viewModel.getHisLiveData()
        liveData.observe(viewLifecycleOwner, Observer {
            viewModel.musicList.value = it.reversed()
        })
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        val list = ArrayList<Music>()
        viewModel.musicList.value?.let { list.addAll(it) }
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

}
