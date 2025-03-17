package com.ricky.desbravatask.domain.usercase


import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseDelete
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseGetAll
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseGetById
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseSave
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseUpdate
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DepartamentoManager @Inject constructor(
    private val useCaseDelete: UseCaseDelete,
    private val useCaseGetById: UseCaseGetById,
    private val usercaseSave: UseCaseSave,
    private val useCaseUpdate: UseCaseUpdate,
    private val useCaseGetAll: UseCaseGetAll
) {
    fun delete(id: String): Flow<Resource<Boolean>> = useCaseDelete(id)
    fun getById(id: String): Flow<Resource<Departamento>> = useCaseGetById(id)
    fun getAll(userId: String): Flow<Resource<List<Departamento>>> = useCaseGetAll(userId)
    fun save(departamento: Departamento): Flow<Resource<Departamento>> = usercaseSave(departamento)
    fun update(departamento: Departamento): Flow<Resource<Departamento>> =
        useCaseUpdate(departamento)
}