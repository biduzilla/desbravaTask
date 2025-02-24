package com.ricky.desbravaTask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
class DesbravaTaskApplication

fun main(args: Array<String>) {
	runApplication<DesbravaTaskApplication>(*args)
}
