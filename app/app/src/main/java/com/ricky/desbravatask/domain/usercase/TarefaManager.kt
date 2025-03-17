package com.ricky.desbravatask.domain.usercase


import com.ricky.desbravatask.domain.models.PageModel
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseDelete
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseGetAll
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseGetByDepartamento
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseGetById
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseSave
import com.ricky.desbravatask.domain.usercase.tarefa.UseCaseUpdate
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TarefaManager @Inject constructor(
    private val useCaseDelete: UseCaseDelete,
    private val useCaseGetById: UseCaseGetById,
    private val useCaseGetByDepartamento: UseCaseGetByDepartamento,
    private val usercaseSave: UseCaseSave,
    private val useCaseUpdate: UseCaseUpdate,
    private val useCaseGetAll: UseCaseGetAll
) {
    fun delete(id: String): Flow<Resource<Boolean>> = useCaseDelete(id)
    fun getById(id: String): Flow<Resource<Tarefa>> = useCaseGetById(id)
    fun getByDepartamento(id: String): Flow<Resource<List<Tarefa>>> = useCaseGetByDepartamento(id)
    fun getAll(
        search: String,
        page: Int,
        size: Int
    ): Flow<Resource<PageModel<Tarefa>>> = useCaseGetAll(search, page, size)

    fun save(tarefa: Tarefa): Flow<Resource<Tarefa>> = usercaseSave(tarefa)
    fun update(tarefa: Tarefa): Flow<Resource<Tarefa>> =
        useCaseUpdate(tarefa)
}