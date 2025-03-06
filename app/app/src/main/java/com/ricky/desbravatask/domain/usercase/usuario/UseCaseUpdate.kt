package com.ricky.desbravatask.domain.usercase.usuario

import com.google.gson.Gson
import com.ricky.desbravatask.domain.models.ErrorRequest
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.domain.repository.UsuarioRepository
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UseCaseUpdate @Inject constructor(private val repository: UsuarioRepository) {
    operator fun invoke(model: UsuarioUpdate):Flow<Resource<UsuarioUpdate>> = flow {
        try {
            emit(Resource.Loading())
            repository.update(model).let { result ->
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