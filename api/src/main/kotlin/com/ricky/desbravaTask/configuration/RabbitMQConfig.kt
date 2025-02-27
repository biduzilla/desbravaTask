package com.ricky.desbravaTask.configuration

import com.ricky.desbravaTask.utils.RabbitMQConstants
import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(private val amqpAdmin: AmqpAdmin) {

    @Bean
    fun queue(): Queue {
        return Queue(RabbitMQConstants.EMAIL_FILA, false)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(RabbitMQConstants.EXCHANGE)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConstants.ROUTING_KEY)
    }
//    @Bean
//    fun queue(): Queue = Queue(RabbitMQConstants.EMAIL_FILA, true)
//
//    @Bean
//    fun exchange(): DirectExchange = DirectExchange(RabbitMQConstants.EXCHANGE)
//
//    @Bean
//    fun binding(queue: Queue, exchange: DirectExchange): Binding {
//        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConstants.ROUTING_KEY)
//    }
//
//    @Bean
//    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
//        return RabbitTemplate(connectionFactory)
//    }
//
//    @PostConstruct
//    fun init() {
//        amqpAdmin.declareExchange(DirectExchange(RabbitMQConstants.EXCHANGE))
//        amqpAdmin.declareQueue(Queue(RabbitMQConstants.EMAIL_FILA, true))
//    }
}