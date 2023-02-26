package com.ranobeua.firebase.other

import com.google.firebase.database.FirebaseDatabase
import com.ranobeua.firebase.other.data.Team

class TeamBase {

    private val teamBase = FirebaseDatabase.getInstance().getReference("teams")


    fun createTeam(team: Team){
        val tm = teamBase.child(team.idTeam)
        tm.child("idTeam").setValue(team.idTeam)
        tm.child("name").setValue(team.name)
        tm.child("description").setValue(team.description)
        tm.child("emailCreator").setValue(team.emailCreator)
        tm.child("date").setValue(team.date.time)
    }

    fun getTeam(idTeam: String){

    }


}