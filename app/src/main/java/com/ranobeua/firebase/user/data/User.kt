package com.ranobeua.firebase.user.data

data class User(
    var email: String = "",
    var name : String = "",
    var readChapters: Int = 0,
    var level: String = "",
    var commentsID: List<String> = emptyList(),
    var teamId: List<String> = emptyList()
){}
