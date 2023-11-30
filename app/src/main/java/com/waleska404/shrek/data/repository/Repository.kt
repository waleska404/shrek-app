package com.waleska404.shrek.data.repository

import androidx.paging.PagingData
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.domain.repository.DataStoreOperations
import com.waleska404.shrek.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    fun getAllCharacters(): Flow<PagingData<ShrekCharacter>> {
        return remote.getAllCharacters()
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }
}