package com.primefootball.timelineservice.models

import java.io.Serializable
import java.util.*

data class User(
    val id: UUID,
    val userName: String = "some username",
    val profileImage: String = "some profile image"
) : Serializable
