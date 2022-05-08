package com.primefootball.timelineservice.models

import lombok.NoArgsConstructor
import org.springframework.data.redis.core.RedisHash

@RedisHash("timeline")
open class Timeline(
    open var id: String, // This is also the user's id to which this timeline belongs
    open var posts: List<Post>,
)
