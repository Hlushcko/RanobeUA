package com.ranobeua.firebase.other

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ranobeua.firebase.ranobe.RanobeBase
import com.ranobeua.firebase.ranobe.data.Chapter

class CommentBase {


    private val ranobeBase = FirebaseDatabase.getInstance().getReference("ranobe")


    public fun addChapter(originalName: String, chapter: Chapter){
        val ranobePath = RanobeBase.ranobeBase.child(originalName)
        val userUid = FirebaseAuth.getInstance().currentUser?.uid

        if(userUid != null){

        }else{
            throw Exception("current user aren`t has a uid")
        }
    }

}