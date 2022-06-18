package com.primefootball.timelineservice.models

import java.io.Serializable
import java.util.*

data class User(
    val id: UUID,
    var username: String,
    var profileImage: String,
) : Serializable
