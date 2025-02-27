package com.ricky.desbravaTask.service

import com.google.gson.Gson
import com.ricky.desbravaTask.utils.RabbitMQConstants
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitMQProducer(
    private val rabbitTemplate: RabbitTemplate
) {
    fun sendMessage(message: Any) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, RabbitMQConstants.ROUTING_KEY, Gson().toJson(message))
    }

}