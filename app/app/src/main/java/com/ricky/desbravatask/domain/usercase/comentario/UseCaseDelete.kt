package com.ricky.desbravatask.domain.usercase.comentario

import com.google.gson.Gson
import com.ricky.desbravatask.domain.models.ErrorRequest
import com.ricky.desbravatask.domain.repository.ComentarioRepository
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UseCaseDelete @Inject constructor(
    private val repository: ComentarioRepository
) {
    operator fun invoke(id: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            repository.delete(id).let { result ->
                if (result.isSuccessful) {
                    emit(Resource.Success(true))
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