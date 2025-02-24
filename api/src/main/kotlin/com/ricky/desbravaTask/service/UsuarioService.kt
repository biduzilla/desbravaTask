package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Usuario
import org.springframework.data.domain.Page

interface UsuarioService : BaseService<Usuario> {
    fun findAll(search: String?, qtd: Int, pagina: Int): Page<Usuario>
}