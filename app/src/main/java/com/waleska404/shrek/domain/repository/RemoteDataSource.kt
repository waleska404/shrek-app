package com.waleska404.shrek.domain.repository

import androidx.paging.PagingData
import com.waleska404.shrek.domain.model.ShrekCharacter
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllCharacters(): Flow<PagingData<ShrekCharacter>>
    fun searchCharacters(query: String): Flow<PagingData<ShrekCharacter>>
}