package com.ranobeua.mainApp.fragments.ranobe

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ranobeua.R
import com.ranobeua.base.firebase.viewModel.ViewModelRanobeBase
import com.ranobeua.mainApp.fragments.ranobe.recycler.RecyclerRanobeList
import com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector.ModelConnect


class RanobeListFragment : Fragment() {

    private var ranobeRecycler: RecyclerRanobeList? = null
    private var recycler: RecyclerView? = null

    private var ranobeBase: ViewModelRanobeBase? = null
    private var connector: ModelConnect? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        initRecycler()
    }

    private fun initViewModels(){
        ranobeBase = ViewModelProvider(this)[ViewModelRanobeBase::class.java]
        connector = ViewModelProvider(this)[ModelConnect::class.java]
    }

    private fun initRecycler(){
        ranobeRecycler = RecyclerRanobeList(requireActivity())
        recycler = view?.findViewById(R.id.ranobeList)
        recycler?.layoutManager = LinearLayoutManager(context?.applicationContext)
        recycler?.adapter = ranobeRecycler
        nextRanobeInfo()
    }

    private fun nextRanobeInfo(){
        connector?.getNextRanobeStatus()?.observe(this, Observer { it ->
            if(it == true){
                ranobeBase?.getRanobeList {result ->
                    ranobeRecycler?.submitList(result)
                    connector?.setNextRanobe(false)
                }
            }
        })
    }


}