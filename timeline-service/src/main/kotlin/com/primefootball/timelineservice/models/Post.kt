package com.primefootball.timelineservice.models

import org.springframework.data.redis.core.RedisHash

open class Post(
    open var id: String,
    open var text: String,
    open var file: String,
    open var user: User,
    open var timeStamp: String
)
