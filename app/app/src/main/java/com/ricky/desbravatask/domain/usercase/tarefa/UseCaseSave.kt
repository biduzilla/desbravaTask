package com.ricky.desbravatask.domain.usercase.tarefa

import com.google.gson.Gson
import com.ricky.desbravatask.domain.models.ErrorRequest
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.repository.TarefaRepository
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UseCaseSave @Inject constructor(private val repository: TarefaRepository) {
    operator fun invoke(tarefa: Tarefa): Flow<Resource<Tarefa>> = flow {
        try {
            emit(Resource.Loading())
            repository.save(tarefa).let { result ->
                if (result.isSuccessful) {
                    result.body()?.let { response ->
                        emit(Resource.Success(response))
                    } ?: run {
                        emit(Resource.Error("Error inesperado"))
                    }

                } else {
                    val error =
                        Gson().fromJson(result.errorBody()?.charStream(), ErrorRequest::class.java)
                    emit(Resource.Error(error?.message ?: "Error desconhecido"))
                }
            }

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error inesperado"))
        } catch (e: IOException) {
            emit(Resource.Error("Cheque sua conexão com a internet"))
        }
    }
}