package com.ricky.desbravatask.domain.usercase


import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.domain.usercase.comentario.UseCaseDelete
import com.ricky.desbravatask.domain.usercase.comentario.UseCaseGetById
import com.ricky.desbravatask.domain.usercase.comentario.UseCaseGetByIdTarefa
import com.ricky.desbravatask.domain.usercase.comentario.UseCaseSave
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComentarioManager @Inject constructor(
    private val useCaseDelete: UseCaseDelete,
    private val useCaseGetById: UseCaseGetById,
    private val useCaseGetByIdTarefa: UseCaseGetByIdTarefa,
    private val usercaseSave: UseCaseSave,
) {
    fun delete(id: String): Flow<Resource<Boolean>> = useCaseDelete(id)
    fun getById(id: String): Flow<Resource<Comentario>> = useCaseGetById(id)
    fun getByIdTarefa(idTarefa: String): Flow<Resource<List<Comentario>>> =
        useCaseGetByIdTarefa(idTarefa)
    fun save(comentario: Comentario): Flow<Resource<Comentario>> = usercaseSave(comentario)

}