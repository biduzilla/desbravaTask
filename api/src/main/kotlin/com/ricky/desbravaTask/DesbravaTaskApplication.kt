package com.ricky.desbravaTask

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableRabbit
@OpenAPIDefinition(
	info = Info(
		title = " REST API DesbravaTask",
		description = "Documentação da REST API DesbravaTask",
		version = "v1",
		contact = Contact(
			name = "Luiz Henrique",
			email = "luiz.devs@gmail.com",
//            url = "https://www.eazybytes.com"
		),
		license = License(
			name = "Apache 2.0",
//            url = "https://www.eazybytes.com"
		)
	),
	externalDocs = ExternalDocumentation(
		description = "Documentação da REST API DesbravaTask",
//        url = "https://www.eazybytes.com/swagger-ui.html"
	)
)
class DesbravaTaskApplication

fun main(args: Array<String>) {
	runApplication<DesbravaTaskApplication>(*args)
}
