package com.ricky.desbrava_task

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = Info(
        title = " REST API DesbravaTask",
        description = "Documentação da REST API DesbravaTask",
        version = "v1",
        contact = Contact(
            name = "Luiz Henrique",
            email = "luiz.devs@gmail.com",
//            url = "https://www.luizhenrique.com"
        ),
        license = License(
            name = "Apache 2.0",
//            url = "https://www.luizhenrique.com"
        )
    ),
    externalDocs = ExternalDocumentation(
        description = "Documentação da REST API DesbravaTask",
//        url = "https://www.luizhenrique.com/swagger-ui.html"
    )
)
class DesbravaTaskApplication

fun main(args: Array<String>) {
    runApplication<DesbravaTaskApplication>(*args)
}
