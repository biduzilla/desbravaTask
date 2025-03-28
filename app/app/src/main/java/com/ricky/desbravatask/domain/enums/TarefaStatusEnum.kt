package com.ricky.desbravatask.domain.enums

enum class TarefaStatusEnum(override val value: String):EnumWithValue {
    A_FAZER("A fazer"),
    EM_ANDAMENTO("Em andamento"),
    EM_REVISAO("Em revisão"),
    CONCLUIDA("Concluída"),
}