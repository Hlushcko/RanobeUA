package com.ranobeua.firebase.ranobe.data

import java.util.*

data class Chapter(
    val chapterId: String,
    val emailCreator: String,
    val number: Int,
    val name: String,
    val text: String,
    val date: Date,
    val translate: String,
)