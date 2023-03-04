package com.ranobeua.firebase.ranobe.data

data class RanobeTeamInfo(
    var name: String = "",
    var description: String = "",
    var status: String = "",
    var team: String = "",
    var idTeam: String = "",
    var urlTeam: String = "",
    var chaptersId: List<String> = emptyList()
)
