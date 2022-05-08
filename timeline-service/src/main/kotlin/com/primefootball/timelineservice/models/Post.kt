package com.primefootball.timelineservice.models

open class Post(
    open var id: String,
    open var text: String,
    open var file: String,
    open var user: User,
    open var timeStamp: String
)
