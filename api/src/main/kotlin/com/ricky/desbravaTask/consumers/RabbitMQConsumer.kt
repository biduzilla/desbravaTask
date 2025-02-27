package com.ricky.desbravaTask.consumers

import com.google.gson.Gson
import com.ricky.desbravaTask.dto.EmailVerificacaoDTO
import com.ricky.desbravaTask.service.EmailService
import com.ricky.desbravaTask.utils.RabbitMQConstants
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMQConsumer(private val emailService: EmailService) {
    @RabbitListener(queues = [RabbitMQConstants.EMAIL_FILA])
    fun receiveMessage(message: String) {
        val data = Gson().fromJson(message, EmailVerificacaoDTO::class.java)

        emailService.sendEmail(
            to = data.email,
            cod = data.cod
        )
    }
}