package com.ranobeua.base.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.ranobeua.base.firebase.other.CommentBase
import com.ranobeua.base.firebase.other.data.Comment

class ViewModelCommentBase : ViewModel() {

    private val base = CommentBase()


    fun addComment(originalName: String, username: String, comment: String, idChapter: String){
        base.addComment(originalName, username, comment, idChapter)
    }


    fun getComment(idComment: String, callable: (Comment?) -> Unit){
        base.getComment(idComment, callable)
    }


    fun setComment(idComment: String, newText: String){
        base.setComment(idComment, newText)
    }


}