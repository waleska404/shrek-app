package com.waleska404.shrek.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.data.remote.ShrekApi
import com.waleska404.shrek.domain.model.CharacterRemoteKeys
import com.waleska404.shrek.domain.model.ShrekCharacter
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val shrekApi: ShrekApi,
    private val shrekDatabase: ShrekDatabase,
) : RemoteMediator<Int, ShrekCharacter>() {

    private val characterDao = shrekDatabase.characterDao()
    private val characterRemoteKeysDao = shrekDatabase.characterRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ShrekCharacter>): MediatorResult {
        return try {
            val response = shrekApi.getAllCharacters(page = 1)
            if(response.characters.isNotEmpty()) {
                shrekDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        characterDao.deleteAllCharacters()
                        characterRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.characters.map { character ->
                        CharacterRemoteKeys(
                            id = character.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    characterRemoteKeysDao.addAllRemoteKeys(characterRemoteKeys = keys)
                    characterDao.addCharacters(characters = response.characters)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}