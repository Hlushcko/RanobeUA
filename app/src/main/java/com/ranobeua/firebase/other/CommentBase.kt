package com.ranobeua.firebase.other

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ranobeua.firebase.other.data.Comment
import com.ranobeua.firebase.ranobe.ChapterBase
import com.ranobeua.firebase.user.UserBase
import java.time.LocalDateTime


class CommentBase {

    private val commentBase = FirebaseDatabase.getInstance().getReference("comments")

    fun addComment(originalName: String, username: String, comment: String, idChapter: String){
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val localTime = LocalDateTime.now()

        if(userEmail != null){
            val id: String =  originalName.substring(0, 5) + System.currentTimeMillis() + localTime.year + localTime.dayOfMonth + localTime.dayOfYear
            val com = commentBase.push()

            com.child("idComment").setValue(id)
            com.child("idChapter").setValue(idChapter)
            com.child("emailUser").setValue(userEmail)
            com.child("nameUser").setValue(username)
            com.child("titleName").setValue(originalName)
            com.child("comment").setValue(comment)

            ChapterBase.addCommentIdToChapter(id, idChapter)
            UserBase.addCommentToUser(id){}
        }else{
            throw Exception("current user aren`t has an email")
        }
    }

    // TODO: клас який буде це викликати має містити наступний код:
    //  getUserInfo("a"){ data ->
    //                if (data != null){
    //                   не нуль, обробляєм
    //                }else{
    //                    нуль, кажемо це користувачу
    //                }
    //            }
    fun getComment(idComment: String, callable: (Comment?) -> Unit){
        val com = commentBase.orderByChild("idComment").startAt(idComment)

        com.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (comment in snapshot.children){ //тут унікальний ID але всяке буває.
                    callable(comment.getValue(Comment::class.java))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })
    }

    fun setComment(idComment: String, newText: String){
        commentBase.child(idComment).child("comment").setValue(newText)
    }


}