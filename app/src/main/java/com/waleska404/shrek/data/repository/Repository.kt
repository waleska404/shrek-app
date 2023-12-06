package com.waleska404.shrek.data.repository

import androidx.paging.PagingData
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.domain.repository.DataStoreOperations
import com.waleska404.shrek.domain.repository.LocalDataSource
import com.waleska404.shrek.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    fun getAllCharacters(): Flow<PagingData<ShrekCharacter>> {
        return remote.getAllCharacters()
    }

    fun searchCharacters(query: String): Flow<PagingData<ShrekCharacter>> {
        return remote.searchCharacters(query = query)
    }

    suspend fun getSelectedCharacter(characterId: Int): ShrekCharacter {
        return local.getSelectedCharacter(characterId = characterId)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }
}