package com.ricky.desbravatask.domain.usercase.comentario

import com.google.gson.Gson
import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.domain.models.ErrorRequest
import com.ricky.desbravatask.domain.repository.ComentarioRepository
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UseCaseGetById @Inject constructor(private val repository: ComentarioRepository) {
    operator fun invoke(id: String):Flow<Resource<Comentario>> = flow {
        try {
            emit(Resource.Loading())
            repository.getById(id).let { result ->
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