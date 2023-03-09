package com.ranobeua.base.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.ranobeua.base.firebase.ranobe.RanobeBase
import com.ranobeua.base.firebase.ranobe.data.Ranobe
import com.ranobeua.base.firebase.ranobe.data.RanobeTeamInfo

class ViewModelRanobeBase : ViewModel() {

    private val base = RanobeBase()


    fun createRanobe(ranobe: Ranobe, callable: (Boolean?) -> Unit){
        base.createRanobe(ranobe, callable)
    }


    fun addTranslateTeamInfo(originalName: String, ranobe: RanobeTeamInfo, callable: (Boolean?) -> Unit){
        base.addTranslateTeamInfo(originalName, ranobe, callable)
    }


    fun updateInfoRanobeTeam(originalName: String, idTeam: String, newInfo: HashMap<String, Any>, callable: (Boolean?) -> Unit){
        base.updateInfoRanobeTeam(originalName, idTeam, newInfo, callable)
    }


    fun getRanobeInfo(originalName: String, callable: (Ranobe?) -> Unit){
        base.getRanobeInfo(originalName, callable)
    }


    fun getRanobeList(callable: (List<Ranobe>?) -> Unit){
        base.getRanobeList(callable)
    }

}