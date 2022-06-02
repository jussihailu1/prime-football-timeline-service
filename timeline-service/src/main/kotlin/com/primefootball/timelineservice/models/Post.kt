package com.primefootball.timelineservice.models

import java.io.Serializable
import java.util.*

class Post(
    val id: UUID,
    val text: String,
    val file: String,
    val user: User,
    val timeStamp: String = "some timestamp"
) : Serializable
