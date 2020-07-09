package com.example.mymusic.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.MainActivity

import com.example.mymusic.R
import com.example.mymusic.model.entity.SearchMusic
import com.example.mymusic.view.adapter.RecVItemOnclickInter
import com.example.mymusic.view.adapter.SMRcyViewAdapter
import com.example.mymusic.viewModel.FraSearchViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {
    private lateinit var adapter : SMRcyViewAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: FraSearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_search, container, false)
        mainActivity = activity as MainActivity
        initViewModel()
        initView(view)
        return view
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(FraSearchViewModel::class.java)
        viewModel.searchResultList.observe(viewLifecycleOwner,Observer {
            adapter.changeData(it)
        })

    }

    private fun initView(view:View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_search_music)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        val list = ArrayList<SearchMusic>()
        adapter = SMRcyViewAdapter(list,object : RecVItemOnclickInter<SearchMusic>{
            override fun onClick(list: ArrayList<SearchMusic>, position: Int) {
                GlobalScope.launch {
                    val url =  viewModel.getPlayUrl(list[position].songmid)
                    mainActivity.mediaService.playMusicWithUrl(url)
                }
            }

            override fun doubleClick() {
            }

            override fun onLongClick() {
            }

        })
        recyclerView.adapter = adapter

        val buttonBack = view.findViewById<Button>(R.id.button_back_top_bar)
        buttonBack.setOnClickListener { mainActivity.navBackStack() }

        val editText = view.findViewById<EditText>(R.id.editText_search)
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId==EditorInfo.IME_ACTION_SEARCH){
                viewModel.getSearchResult(v.text.toString())
            }
            true
        }
    }

}
