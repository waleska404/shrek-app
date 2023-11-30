package com.waleska404.shrek.di

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.data.remote.ShrekApi
import com.waleska404.shrek.data.repository.RemoteDataSourceImpl
import com.waleska404.shrek.domain.repository.RemoteDataSource
import com.waleska404.shrek.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideShrekApi(retrofit: Retrofit): ShrekApi {
        return retrofit.create(ShrekApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        shrekApi: ShrekApi,
        shrekDatabase: ShrekDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            shrekApi = shrekApi,
            shrekDatabase = shrekDatabase
        )
    }
}