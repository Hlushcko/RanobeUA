package com.ranobeua.firebase.ranobe

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ranobeua.firebase.ranobe.data.Chapter
import java.time.LocalDateTime

class ChapterBase {

    companion object {
        private val chaptersBase = FirebaseDatabase.getInstance().getReference("chapters")
        private val chapterTextBase = FirebaseDatabase.getInstance().getReference("texts")


        fun addCommentIdToChapter(idComment: String, idChapter: String){
            val com = chaptersBase.child(idChapter).child("commentsId").push()
            com.setValue(idComment)
        }
    }


    fun addChapter(originalName: String, idTeam: String, text: String, chapter: Chapter, callable: (Boolean?) -> Unit){
        val localTime = LocalDateTime.now()
        val id: String =  originalName.substring(0, 5) + System.currentTimeMillis() + localTime.year + localTime.dayOfMonth + localTime.dayOfYear
        val chose = chaptersBase.child(idTeam).child(id)

        val email = FirebaseAuth.getInstance().currentUser?.email
            ?: throw Exception("email user not found")

        chose.child("chapterId").setValue(id)
        chose.child("emailCreator").setValue(email)
        chose.child("number").setValue(chapter.number)
        chose.child("name").setValue(chapter.name)
        chose.child("textId").setValue(id)
        chose.child("date").setValue(chapter.date)

        saveText(text, id)
        RanobeBase.addChapterToRanobe(originalName, idTeam, id){
            callable(it)
        }
    }

    fun updateInfoChapter(idTeam: String, idChapter: String, newInfo: HashMap<String, Any>){
        chaptersBase.child(idTeam).child(idChapter).updateChildren(newInfo)
    }

    fun updateTextChapter(idText: String, newText: String){
        chapterTextBase.child(idText).setValue(newText)
    }

    private fun saveText(text: String, id: String){
        val push = chapterTextBase.push()
        push.child(id).setValue(text)
    }


    fun getTextChapter(idChapter: String, callable: (String?) -> Unit){
        chapterTextBase.child(idChapter).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                callable(snapshot.getValue(String::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })
    }


    fun getChapter(idChapter: String, callable: (Chapter?) -> Unit){
        chaptersBase.child(idChapter).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                callable(snapshot.getValue(Chapter::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                callable(null)
            }
        })
    }



    fun getAllChaptersTeam(chaptersId: List<String>) : List<Chapter>{
        val chapters: ArrayList<Chapter> = ArrayList()

        for(element in chaptersId){
            getChapter(element) { result ->
                if(result != null){
                    chapters.add(result)
                }
            }
        }

        return chapters
    }




}