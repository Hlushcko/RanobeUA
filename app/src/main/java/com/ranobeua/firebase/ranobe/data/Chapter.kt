package com.ranobeua.firebase.ranobe.data

import java.util.*

data class Chapter(
    var number: Int = 0,
    var name: String = "",
    var date: Date = Date(),
    var chapterId: String = "",
    var emailCreator: String = "",
    var textId: String = "",
    var commentsId: List<String> = emptyList()
)