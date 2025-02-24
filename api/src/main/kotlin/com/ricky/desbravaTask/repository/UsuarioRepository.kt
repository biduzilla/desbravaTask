package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository:JpaRepository<Usuario,String> {
}