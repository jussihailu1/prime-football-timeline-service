package com.primefootball.timelineservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate


@SpringBootApplication
class TimelineServiceApplication

fun main(args: Array<String>) {
    runApplication<TimelineServiceApplication>(*args)

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        return JedisConnectionFactory()
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.setConnectionFactory(jedisConnectionFactory())
        return template
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory?): RabbitTemplate {
        return RabbitTemplate(connectionFactory!!)
    }
}

