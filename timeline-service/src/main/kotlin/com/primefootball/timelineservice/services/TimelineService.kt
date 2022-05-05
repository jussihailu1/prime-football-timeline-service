package com.primefootball.timelineservice.services

import com.primefootball.timelineservice.models.Post
import com.primefootball.timelineservice.models.Timeline
import com.primefootball.timelineservice.repositories.TimelineRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit


@Service
class TimelineService(
    private val timelineRepository: TimelineRepository,
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun getTimeline(requesterId: String): Timeline {
        // Refresh inactivity period or add to active users list
        setActivity(requesterId)

        return if(redisTemplate.hasKey(requesterId)){
            // return the timeline that belong to this user
            timelineRepository.findById(requesterId).get()
        } else {
            // fetch timeline from post-service and return it
            messageBrokerStuff()
            Timeline("", emptyList())
        }
    }

    private fun setActivity(userId: String){
        redisTemplate.opsForValue().set(userId, "", Duration.of(1, ChronoUnit.MINUTES));
    }

    private fun refreshCache() {
        // Every X seconds, do
        messageBrokerStuff()
    }

    private fun messageBrokerStuff(){
        // HTTP call to post-service through message broker to find all the post from the users in that list
        // Replace current timeline (if it exists) with new list
    }
//
}
