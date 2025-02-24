package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Usuario
import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, String> {
    fun existsByEmail(email: String): Boolean
}