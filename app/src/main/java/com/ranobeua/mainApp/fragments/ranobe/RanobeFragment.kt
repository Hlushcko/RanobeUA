package com.ranobeua.mainApp.fragments.ranobe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ranobeua.R
import com.ranobeua.base.firebase.other.data.Team
import com.ranobeua.base.firebase.ranobe.data.Chapter
import com.ranobeua.base.firebase.ranobe.data.RanobeTeamInfo
import com.ranobeua.base.firebase.viewModel.ViewModelChapterBase
import com.ranobeua.base.firebase.viewModel.ViewModelRanobeBase
import com.ranobeua.mainApp.fragments.ranobe.recycler.RecyclerChapters
import com.ranobeua.mainApp.fragments.ranobe.recycler.RecyclerTeam
import com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector.ModelConnect


class RanobeFragment : Fragment() {

    private var connector: ModelConnect? = null
    private var chapterBase: ViewModelChapterBase? = null
    private var ranobeBase: ViewModelRanobeBase? = null

    private var recyclerT: RecyclerView? = null
    private var recyclerC: RecyclerView? = null
    private var recyclerTeam: RecyclerTeam? = null
    private var recyclerChapters: RecyclerChapters? = null
    private var teamList: List<RanobeTeamInfo>? = null

    private lateinit var originalName: TextView
    private lateinit var author: TextView
    private lateinit var year: TextView
    private lateinit var name: TextView
    private lateinit var statusTranslate: TextView
    private lateinit var teamTranslate: TextView
    private lateinit var resourceTeam: TextView
    private lateinit var description: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        initView(view)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        initRecycler()
    }


    private fun initViewModels(){
        connector = ViewModelProvider(requireActivity())[ModelConnect::class.java]
        chapterBase = ViewModelProvider(requireActivity())[ViewModelChapterBase::class.java]
        ranobeBase = ViewModelProvider(requireActivity())[ViewModelRanobeBase::class.java]
    }


    private fun initRecycler(){
        recyclerChapters = RecyclerChapters(requireActivity())
        recyclerC = view?.findViewById(R.id.recyclerChapters)
        recyclerC?.layoutManager = LinearLayoutManager(context?.applicationContext)
        recyclerC?.adapter = recyclerChapters

        recyclerTeam = RecyclerTeam(requireActivity())
        recyclerT = view?.findViewById(R.id.recyclerTeam)
        recyclerT?.layoutManager = LinearLayoutManager(context?.applicationContext)
        recyclerT?.adapter = recyclerChapters

        getRanobeInfo()
        chapterIdObserver()
        teamIdObserver()
    }


    private fun initView(view: View){
        originalName = view.findViewById(R.id.originalName)
        author = view.findViewById(R.id.author)
        year = view.findViewById(R.id.year)
        name = view.findViewById(R.id.name)
        statusTranslate = view.findViewById(R.id.statusTranslate)
        teamTranslate = view.findViewById(R.id.teamTranslate)
        resourceTeam = view.findViewById(R.id.resourceTeam)
        description = view.findViewById(R.id.description)
    }


    private fun getRanobeInfo(){
        ranobeBase?.getRanobeInfo(connector?.getName().toString()){
            if(it != null) {
                originalName.text = it.originalName
                author.text = it.author
                year.text = it.year
                teamList = ArrayList(it.ranobeTeamInfo.values)
                connector?.setTeamId(0)
            }
        }
    }

    private fun teamIdObserver(){
        connector?.getTeamId()?.observe(this, Observer {
            if(it >= 0 && teamList != null){
                val chapters: List<String> = ArrayList(teamList!![it.toInt()].chaptersId.values)
                val result = chapterBase?.getAllChaptersTeam(chapters)
                recyclerChapters?.submitList(result)
            }
        })
    }

    private fun getChaptersTeam(){

    }


    private fun chapterIdObserver(){
        connector?.getChapterId()?.observe(this, Observer {
            if(it.isNotEmpty()){

            }
        })
    }

}