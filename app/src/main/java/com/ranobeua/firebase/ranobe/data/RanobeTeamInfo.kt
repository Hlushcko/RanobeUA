package com.ranobeua.firebase.ranobe.data

data class RanobeTeamInfo(
    val name: String,
    val description: String,
    val status: String,
    val team: String,
    val idTeam: String,
    val urlTeam: String,
    val chaptersId: List<String> = emptyList()
)
