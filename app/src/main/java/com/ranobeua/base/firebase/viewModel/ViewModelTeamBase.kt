package com.ranobeua.base.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.ranobeua.base.firebase.other.TeamBase
import com.ranobeua.base.firebase.other.data.Team

class ViewModelTeamBase : ViewModel() {

    private val base = TeamBase()


    fun createTeam(team: Team, callable: (Boolean?) -> Unit){
        base.createTeam(team, callable)
    }


    fun getTeam(idTeam: String, callable: (Team?) -> Unit){
        base.getTeam(idTeam, callable)
    }


    fun getTeamByEmailCreator(email: String, callable: (Team?) -> Unit){
        base.getTeamByEmailCreator(email, callable)
    }


    fun setTeamDescription(idTeam: String, description: String, callable: (Boolean?) -> Unit){
        base.setTeamDescription(idTeam, description, callable)
    }

}