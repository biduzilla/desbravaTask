package com.ricky.desbravatask.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.data.network.api.DepartamentoAPI
import com.ricky.desbravatask.data.network.api.RefreshTokenAPI
import com.ricky.desbravatask.data.network.api.UsuarioAPI
import com.ricky.desbravatask.data.network.interceptor.AuthInterceptor
import com.ricky.desbravatask.data.repositoryImpl.DepartamentoRepositoryImpl
import com.ricky.desbravatask.data.repositoryImpl.TokenRepositoryImpl
import com.ricky.desbravatask.data.repositoryImpl.UsuarioRepositoryImpl
import com.ricky.desbravatask.domain.repository.DepartamentoRepository
import com.ricky.desbravatask.domain.repository.TokenRepository
import com.ricky.desbravatask.domain.repository.UsuarioRepository
import com.ricky.desbravatask.utils.LocalDateTimeAdapter
import com.ricky.desbravatask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
    }

    @Singleton
    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context): DataStoreUtil {
        return DataStoreUtil(context)
    }

    @Singleton
    @Provides
    fun provideRefreshToken(): RefreshTokenAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RefreshTokenAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDepartamentoApi(authInterceptor: AuthInterceptor,gson: Gson): DepartamentoAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DepartamentoAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApi(authInterceptor: AuthInterceptor): UsuarioAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        api: UsuarioAPI,
    ): UsuarioRepository = UsuarioRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideTokenRepository(
        api: RefreshTokenAPI,
    ): TokenRepository = TokenRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideDepartamentoRepository(
        api: DepartamentoAPI,
    ): DepartamentoRepository = DepartamentoRepositoryImpl(api)


}