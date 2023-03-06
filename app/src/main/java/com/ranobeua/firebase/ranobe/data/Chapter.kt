package com.ranobeua.firebase.ranobe.data

data class Chapter(
    var number: Int = 0,
    var name: String = "",
    var date: Long = 0L,
    var chapterId: String = "",
    var emailCreator: String = "",
    var textId: String = "",
    var commentsId: Map<String, String> = emptyMap()
)