package com.ranobeua.firebase.other.data

import java.util.*

data class Team(
    val idTeam: String,
    val name: String,
    val description: String,
    val emailCreator: String,
    val emailTeam: List<String>,
    val date: Date
)
