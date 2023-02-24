package com.ranobeua.firebase.ranobe.data

data class Ranobe(
    val originalName: String,
    val author: String,
    val year: String,
    val ranobeTeamInfo: List<RanobeTeamInfo>
)
