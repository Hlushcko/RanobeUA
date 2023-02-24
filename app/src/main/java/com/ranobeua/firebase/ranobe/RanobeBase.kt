package com.ranobeua.firebase.ranobe

import com.google.firebase.database.FirebaseDatabase
import com.ranobeua.firebase.ranobe.data.Ranobe
import com.ranobeua.firebase.ranobe.data.RanobeTeamInfo

class RanobeBase {


    val ranobeBase = FirebaseDatabase.getInstance().getReference("ranobe")


    fun createRanobe(ranobe: Ranobe){
        val ranobePath = ranobeBase.child(ranobe.originalName)
        ranobePath.child("originalName").setValue(ranobe.originalName)
        ranobePath.child("author").setValue(ranobe.author)
        ranobePath.child("year").setValue(ranobe.year)

        addTranslateInfo(ranobe.originalName, ranobe.ranobeTeamInfo[0])
    }

    fun addTranslateInfo(originalName: String, ranobe: RanobeTeamInfo){
        val ranobePath = ranobeBase.child(originalName)

        val teamInfo = ranobePath.child(ranobe.idTeam)
        teamInfo.child("name").setValue(ranobe.name)
        teamInfo.child("description").setValue(ranobe.description)
        teamInfo.child("status").setValue(ranobe.status)
        teamInfo.child("team").setValue(ranobe.team)
        teamInfo.child("idTeam").setValue(ranobe.idTeam)
        teamInfo.child("urlTeam").setValue(ranobe.urlTeam)
        teamInfo.child("chapters")
    }

}