package com.primefootball.timelineservice.messaging

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class MessageConsumer {
    @RabbitListener(queues = [MessagingConfig.SENDER_QUEUE])
    fun consumeMessageFromQueue(message: String) {
    }
}
