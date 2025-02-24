package com.ricky.desbravaTask.entity

import com.ricky.desbravaTask.dto.UsuarioDTO
import jakarta.persistence.*

@Entity
@Table(name = "USUARIO")
data class Usuario(
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    @Column(name = "NAME", length = 20)
    var name: String = "",
    @Column(name = "EMAIL", length = 50)
    var email: String = "",
    @Column(name = "SENHA", length = 250)
    var senha: String = "",
    @Column(name = "CODVERIFICACAO")
    var codVerificacao: Int = 0,
) : BaseEntity() {
    fun toDTO(): UsuarioDTO {
        return UsuarioDTO(
            id = id,
            name = name,
            email = email,
            codVerificacao = codVerificacao
        )
    }
}
