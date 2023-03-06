package com.ranobeua.firebase.other

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ranobeua.firebase.other.data.Team
import com.ranobeua.firebase.user.UserBase

class TeamBase {

    private val teamBase = FirebaseDatabase.getInstance().getReference("teams")


    fun createTeam(team: Team, callable: (Boolean?) -> Unit){
        val tm = teamBase.child(team.idTeam)
        tm.child("idTeam").setValue(team.idTeam)
        tm.child("name").setValue(team.name)
        tm.child("description").setValue(team.description)
        tm.child("emailCreator").setValue(team.emailCreator)
        tm.child("date").setValue(team.date)
        UserBase.addElementToUser(team.idTeam, "teamId"){
            callable(it)
        }
    }

    fun getTeam(idTeam: String, callable: (Team?) -> Unit){
        teamBase.child(idTeam).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                callable(snapshot.getValue(Team::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })
    }

    fun getTeamByEmailCreator(email: String, callable: (Team?) -> Unit){
        teamBase.orderByChild("emailCreator").equalTo(email)
            .addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val team = snapshot.children.firstOrNull()?.getValue(Team::class.java)
                callable(team)
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })
    }

    fun setTeamDescription(idTeam: String, description: String, callable: (Boolean?) -> Unit){
        teamBase.child(idTeam).child("description").setValue(description)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    callable(true)
                }else{
                    callable(false)
                }
            }
    }


}