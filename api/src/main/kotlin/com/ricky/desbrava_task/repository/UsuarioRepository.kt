package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UsuarioRepository : JpaRepository<Usuario, String> {

    @Query(
        """
        Select u from Usuario u 
        where 
            (:search is null 
            or u.nome like :search% or u.email like :search%)
            and u.clube.idClube = :idClube
    """
    )
    fun findAll(@Param("search") search: String?,@Param("idClube") idClube: String?,pageable: Pageable): Page<Usuario>

    fun findByEmail(id: String): Optional<Usuario>

    fun existsByEmail(email: String): Boolean
    fun findByEmailAndCodVerificacao(email: String, cod: Int): Optional<Usuario>
    fun findByCodVerificacao(id: Int): Optional<Usuario>
    fun existsByCodVerificacaoAndEmail(id: Int, email: String): Boolean
    fun existsByCodVerificacao(id: Int): Boolean
}