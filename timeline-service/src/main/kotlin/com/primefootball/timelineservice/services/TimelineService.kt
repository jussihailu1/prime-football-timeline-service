package com.primefootball.timelineservice.services

import com.google.gson.Gson
import com.primefootball.timelineservice.messaging.MessagingConfig
import com.primefootball.timelineservice.models.Post
import com.primefootball.timelineservice.models.Timeline
import com.primefootball.timelineservice.repositories.TimelineRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit


@Service
class TimelineService(
    private val timelineRepository: TimelineRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val rabbitTemplate: RabbitTemplate
) {
    var gson = Gson()

    fun getTimeline(requesterId: String): Timeline {
        val userIsActive = redisTemplate.hasKey(requesterId)
        setActivity(requesterId)

        return if (userIsActive) timelineRepository.findById(requesterId).orElse(Timeline(requesterId, emptyList()))
        else requestTimelineFromPostService(requesterId)
    }

    fun getLatestTimeline(requesterId: String): Timeline {
        setActivity(requesterId)
        return requestTimelineFromPostService(requesterId)
    }

    private fun setActivity(userId: String) {
        redisTemplate.opsForValue().set(userId, userId, Duration.of(1, ChronoUnit.MINUTES))
    }

    private fun requestTimelineFromPostService(requesterId: String): Timeline {
        val response = rabbitTemplate.convertSendAndReceive(
            MessagingConfig.EXCHANGE,
            MessagingConfig.SENDER_ROUTING_KEY,
            requesterId
        )

        val json = gson.fromJson(response.toString(), Array<Post>::class.java)
        var listOfPosts = emptyList<Post>()
        json.map { post -> listOfPosts += post }

        return Timeline(requesterId, listOfPosts)
    }

    private fun refreshCache() {
        // Every X seconds, do
//        messageBrokerStuff()
    }
}

