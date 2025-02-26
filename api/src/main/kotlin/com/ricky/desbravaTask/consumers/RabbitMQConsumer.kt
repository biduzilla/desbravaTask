package com.ricky.desbravaTask.consumers

import com.ricky.desbravaTask.service.EmailService
import com.ricky.desbravaTask.utils.RabbitMQConstants
import org.springframework.amqp.rabbit.annotation.RabbitListener
import com.google.gson.Gson;

class RabbitMQConsumer(private val emailService: EmailService) {
    @RabbitListener(queues = [RabbitMQConstants.EMAIL_FILA])
    fun receiveMessage(message: String) {
        val data = Gson
    }
}