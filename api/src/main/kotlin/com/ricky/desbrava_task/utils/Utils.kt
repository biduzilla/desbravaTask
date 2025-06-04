package com.ricky.desbrava_task.utils

import com.ricky.desbrava_task.models.Usuario
import org.springframework.boot.actuate.endpoint.SecurityContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder

fun getPageable(page: Int, size: Int): Pageable {
    val sort: Sort = Sort.by(Sort.Direction.DESC, "createdAt")
    return PageRequest.of(page, size, sort)
}

fun getUsuarioLogado(): Usuario {
    val authentication = SecurityContextHolder.getContext().authentication
    return (authentication?.principal as? Usuario) ?: Usuario()
}