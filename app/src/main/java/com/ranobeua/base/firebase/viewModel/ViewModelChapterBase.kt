package com.ranobeua.base.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.ranobeua.base.firebase.ranobe.ChapterBase
import com.ranobeua.base.firebase.ranobe.data.Chapter

class ViewModelChapterBase : ViewModel() {

    private val base = ChapterBase()


    fun addChapter(originalName: String, idTeam: String, text: String, chapter: Chapter, callable: (Boolean?) -> Unit){
        base.addChapter(originalName, idTeam, text, chapter, callable)
    }


    fun updateChapter(idTeam: String, idChapter: String, newInfo: HashMap<String, Any>){
        base.updateInfoChapter(idTeam, idChapter, newInfo)
    }


    fun updateTextChapter(idText: String, newText: String){
        base.updateTextChapter(idText, newText)
    }


    fun getTextChapter(idChapter: String, callable: (String?) -> Unit){
        base.getTextChapter(idChapter, callable)
    }


    fun getChapter(idChapter: String, callable: (Chapter?) -> Unit){
        base.getChapter(idChapter, callable)
    }


    fun getAllChaptersTeam(chaptersId: List<String>) : List<Chapter>{
        return base.getAllChaptersTeam(chaptersId)
    }


}