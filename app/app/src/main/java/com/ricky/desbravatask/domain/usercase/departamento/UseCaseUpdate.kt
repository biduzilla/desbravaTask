package com.ricky.desbravatask.domain.usercase.departamento

import com.google.gson.Gson
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.ErrorRequest
import com.ricky.desbravatask.domain.repository.DepartamentoRepository
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UseCaseUpdate @Inject constructor(private val repository: DepartamentoRepository) {
    operator fun invoke(departamento: Departamento):Flow<Resource<Departamento>> = flow {
        try {
            emit(Resource.Loading())
            repository.update(departamento).let { result ->
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