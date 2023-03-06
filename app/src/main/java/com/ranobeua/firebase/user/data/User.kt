package com.ranobeua.firebase.user.data

data class User(
    var email: String = "",
    var name : String = "",
    var readChapters: Int = 0,
    var level: String = "",
    var commentsId: Map<String, String> = emptyMap(),
    var teamId: Map<String, String> = emptyMap()
){}
