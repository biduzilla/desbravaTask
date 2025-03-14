package com.ricky.desbravatask.domain.enums

import com.ricky.desbravatask.domain.enums.EnumWithValue

enum class TarefaPrioridadeEnum(override val value: String):EnumWithValue {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta");
}