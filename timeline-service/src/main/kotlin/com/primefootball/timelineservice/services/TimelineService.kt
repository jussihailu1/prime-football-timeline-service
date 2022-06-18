package com.primefootball.timelineservice.services

import com.google.gson.Gson
import com.primefootball.timelineservice.messaging.MessagingConfig
import com.primefootball.timelineservice.models.Post
import com.primefootball.timelineservice.models.Timeline
import com.primefootball.timelineservice.models.User
import com.primefootball.timelineservice.repositories.TimelineRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*


@Service
class TimelineService(
    private val timelineRepository: TimelineRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val rabbitTemplate: RabbitTemplate
) {
    private var gson = Gson()

    fun getTimeline(requesterId: UUID): Timeline {
        val userIsActive = redisTemplate.hasKey(requesterId.toString())

        return if (userIsActive) {
            setActivity(requesterId)
            println("cached timeline")
            timelineRepository.findById(requesterId).orElse(Timeline(requesterId, emptyList()));
        } else requestTimelineFromPostService(requesterId)
    }

    fun getLatestTimeline(requesterId: UUID): Timeline {
        setActivity(requesterId)
        return requestTimelineFromPostService(requesterId)
    }

    private fun setActivity(userId: UUID) {
        redisTemplate.opsForValue().set(userId.toString(), userId.toString(), Duration.of(1, ChronoUnit.HOURS))
    }

    private fun requestTimelineFromPostService(requesterId: UUID): Timeline {
        println("latest timeline")
        val response = rabbitTemplate.convertSendAndReceive(
            MessagingConfig.EXCHANGE,
            MessagingConfig.ROUTING_KEY,
            requesterId
        )

        val json = gson.fromJson(response.toString(), Array<Post>::class.java)
        val listOfPosts = emptyList<Post>().toMutableList()
        json.map { post -> listOfPosts += setUserAndReturnPost(post) }

        return timelineRepository.save(Timeline(requesterId, listOfPosts))
    }

    private fun setUserAndReturnPost(post: Post): Post{
        post.user.username = "some username"
        post.user.profileImage = "some profile image"
        return post
    }

//    private fun refreshCache(requesterId: UUID) {
//        // Every X minutes, refresh timeline
//         requestTimelineFromPostService(requesterId)
//    }
}

