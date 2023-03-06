package com.ranobeua.base.firebase.ranobe.data

data class RanobeTeamInfo(
    var name: String = "",
    var description: String = "",
    var status: String = "",
    var team: String = "",
    var idTeam: String = "",
    var urlTeam: String = "",
    var chaptersId: Map<String, String> = emptyMap()
)
