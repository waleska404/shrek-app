package com.waleska404.shrek.data.remote

import com.waleska404.shrek.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ShrekApi {
    @GET("/shrek/characters")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/shrek/characters/search")
    suspend fun searchCharacters(
        @Query("name") name: String
    ): ApiResponse
}