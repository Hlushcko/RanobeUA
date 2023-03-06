package com.ranobeua.firebase.user.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

data class User(
    var email: String = "",
    var name : String = "",
    var readChapters: Int = 0,
    var level: String = "",
    var commentsId: Map<String, Any> = emptyMap(),
    var teamId: Map<String, Any> = emptyMap()
){}
