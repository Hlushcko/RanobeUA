package com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelConnect : ViewModel() {

    private val nextRanobe: MutableLiveData<Boolean> = MutableLiveData(true)
    private lateinit var nameRanobe: String
    private val chapterId: MutableLiveData<String> = MutableLiveData()
    private val teamId: MutableLiveData<Int> = MutableLiveData(-1)


    fun setTeamId(team: Int){
        teamId.value = team
    }

    fun getTeamId() = teamId

    fun setName(newName: String){
        nameRanobe = newName
    }

    fun getName() = nameRanobe

    fun setChapterId(id: String){
        chapterId.value = id
    }

    fun getChapterId() = chapterId

    fun setNextRanobe(bool: Boolean){
        nextRanobe.value = bool
    }

    fun getNextRanobeStatus() = nextRanobe

}