package com.ranobeua.firebase.ranobe.data

import java.util.*

data class Chapter(
    val number: Int,
    val name: String,
    val date: Date,
    val chapterId: String = "",
    val emailCreator: String = "",
    val textId: String = "",
    val commentsId: List<String> = emptyList()
)