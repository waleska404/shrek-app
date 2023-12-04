package com.waleska404.shrek.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.data.remote.ShrekApi
import com.waleska404.shrek.domain.model.CharacterRemoteKeys
import com.waleska404.shrek.domain.model.ShrekCharacter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val shrekApi: ShrekApi,
    private val shrekDatabase: ShrekDatabase,
) : RemoteMediator<Int, ShrekCharacter>() {

    private val characterDao = shrekDatabase.characterDao()
    private val characterRemoteKeysDao = shrekDatabase.characterRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = characterRemoteKeysDao.getRemoteKeys(id = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 5 //minutes
        Log.d("Remote Mediator", "Current Time: ${parseMillis(currentTime)}")
        Log.d("Remote Mediator", "Last Updated Time: ${parseMillis(lastUpdated)}")

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            Log.d("Remote Mediator", "UP TO DATE! Using cached data")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("Remote Mediator", "REFRESH! Using fresh data")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ShrekCharacter>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = shrekApi.getAllCharacters(page = page)
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
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ShrekCharacter>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ShrekCharacter>
    ): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(id = character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ShrekCharacter>
    ): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(id = character.id)
            }
    }

    private fun parseMillis(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat(
            "yyyy.MM.dd HH:mm",
            Locale.ROOT
        )
        return format.format(date)
    }

}