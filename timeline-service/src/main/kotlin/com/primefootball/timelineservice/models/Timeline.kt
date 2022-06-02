package com.primefootball.timelineservice.models

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.util.*

@RedisHash("timeline")
open class Timeline(
    open val id: UUID, // This is also the user's id to which this timeline belongs
    open val posts: List<Post>,
) : Serializable
