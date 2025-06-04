package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.UsuarioDTO
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "DEPARTAMENTO")
@SQLDelete(sql = "UPDATE Usuario SET flagExcluido = true WHERE idUsuario=?")
@SQLRestriction("flagExcluido <> true")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "IDUSUARIO")
    val idUsuario: String = "",

    @ManyToOne
    @JoinColumn(name = "IDCLUBE")
    var clube: Clube? = null,

    @Column(name = "NOME", length = 50)
    var nome: String = "",

    @Column(name = "EMAIL", length = 20)
    var email: String = "",

    @Column(name = "SENHA", length = 100)
    var senha: String = "",

    @Column(name = "CODVERIFICACAO")
    var codVerificacao: Int = 0,
) : BaseModel() {
    fun toDTO(): UsuarioDTO {
        return UsuarioDTO(
            idUsuario = idUsuario,
            clube = clube?.toDTO(),
            nome = nome,
            email = email
        )
    }
}
