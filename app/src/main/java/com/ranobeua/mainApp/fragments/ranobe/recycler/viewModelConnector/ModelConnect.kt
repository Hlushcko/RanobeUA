package com.ranobeua.mainApp.fragments.ranobe.recycler.viewModelConnector

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelConnect : ViewModel() {

    private val nextRanobe: MutableLiveData<Boolean> = MutableLiveData(true)
    private lateinit var nameRanobe: String


    fun setName(newName: String){
        nameRanobe = newName
    }

    fun getName() = nameRanobe

    fun getNextRanobeStatus() = nextRanobe

    fun setNextRanobe(bool: Boolean){
        nextRanobe.value = bool
    }


}