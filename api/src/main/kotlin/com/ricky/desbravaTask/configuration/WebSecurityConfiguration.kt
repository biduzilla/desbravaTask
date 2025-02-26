package com.ricky.desbravaTask.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.ricky.desbravaTask.security.JwtAuthFilter
import com.ricky.desbravaTask.security.JwtService
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.I18n
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    @Lazy private val usuarioService: UsuarioService,
    private val jwtService: JwtService,
    private val i18n: I18n,
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtFilter(): OncePerRequestFilter {
        return JwtAuthFilter(jwtService, usuarioService, i18n, objectMapper)
    }

    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        passwordEncoder: PasswordEncoder,
        userDetailsService: UsuarioService
    ): AuthenticationManager {
        val authManagerBuild: AuthenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder::class.java)

        authManagerBuild
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)

        return authManagerBuild.build()
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf {
            it.disable()
        }.headers {
            it.frameOptions { frame ->
                frame.disable()
            }
        }.authorizeHttpRequests { authorize ->
            authorize
                ?.requestMatchers("/h2-console/**")?.permitAll()
                ?.requestMatchers("/h2-console/")?.permitAll()
                ?.requestMatchers("/h2-console")?.permitAll()
                ?.anyRequest()
                ?.authenticated()
        }.sessionManagement {
            it?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.formLogin {
            it?.disable()
        }.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
