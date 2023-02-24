package com.ranobeua.firebase.user.data

data class User(
    val email: String,
    val name : String,
    val readChapters: Int,
    val level: String,
    val commentsID: List<String>,
    val teamId: List<String>
)
