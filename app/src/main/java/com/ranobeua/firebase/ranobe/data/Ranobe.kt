package com.ranobeua.firebase.ranobe.data

data class Ranobe(
    var originalName: String = "",
    var author: String = "",
    var year: String = "",
    var ranobeTeamInfo: Map<String, RanobeTeamInfo> = emptyMap()
)
