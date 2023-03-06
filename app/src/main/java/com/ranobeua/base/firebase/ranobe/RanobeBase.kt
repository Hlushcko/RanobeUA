package com.ranobeua.base.firebase.ranobe

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ranobeua.base.firebase.ranobe.data.Ranobe
import com.ranobeua.base.firebase.ranobe.data.RanobeTeamInfo

class RanobeBase {

    companion object {

        private val ranobeBase = FirebaseDatabase.getInstance().getReference("ranobe")
        private var currentPosition: String? = null


        fun addChapterToRanobe(originalName: String, teamId: String, chapterId: String, callable: (Boolean?) -> Unit){
            val chapters = ranobeBase.child(originalName).child(teamId).child("chaptersId").push()
            chapters.setValue(chapterId).addOnCompleteListener {
                callable(it.isSuccessful)
            }
        }


    }


    fun createRanobe(ranobe: Ranobe, callable: (Boolean?) -> Unit){
        val ranobePath = ranobeBase.child(ranobe.originalName)
        ranobePath.child("originalName").setValue(ranobe.originalName)
        ranobePath.child("author").setValue(ranobe.author)
        ranobePath.child("year").setValue(ranobe.year)

        addTranslateTeamInfo(ranobe.originalName, ranobe.ranobeTeamInfo.values.first()){
            callable(it)
        }
    }


    fun addTranslateTeamInfo(originalName: String, ranobe: RanobeTeamInfo, callable: (Boolean?) -> Unit){
        val ranobePath = ranobeBase.child(originalName)

        val teamInfo = ranobePath.child(ranobe.idTeam)
        teamInfo.child("name").setValue(ranobe.name)
        teamInfo.child("description").setValue(ranobe.description)
        teamInfo.child("status").setValue(ranobe.status)
        teamInfo.child("team").setValue(ranobe.team)
        teamInfo.child("idTeam").setValue(ranobe.idTeam)
        teamInfo.child("urlTeam").setValue(ranobe.urlTeam).addOnCompleteListener {
            callable(it.isSuccessful)
        }
    }

    fun updateInfoRanobeTeam(originalName: String, idTeam: String, newInfo: HashMap<String, Any>, callable: (Boolean?) -> Unit){
        ranobeBase.child(originalName).child(idTeam).updateChildren(newInfo).addOnCompleteListener {
            callable(it.isSuccessful)
        }
    }


    fun getRanobeInfo(originalName: String, callable: (Ranobe?) -> Unit){
        val base = ranobeBase.orderByChild("originalName").equalTo(originalName)

        base.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val element = snapshot.children.firstOrNull()?.getValue(Ranobe::class.java)
                callable(element)
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })
    }


    fun getRanobeList(callable: (List<Ranobe>?) -> Unit){
        ranobeBase.startAt(currentPosition).limitToFirst(20)
            .addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Ranobe>()
                for(element in snapshot.children){
                    list.add(element.getValue(Ranobe::class.java)!!)
                    currentPosition = element.key
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })

    }

}