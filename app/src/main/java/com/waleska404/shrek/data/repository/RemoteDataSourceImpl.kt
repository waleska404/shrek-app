package com.waleska404.shrek.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.data.paging_source.CharacterRemoteMediator
import com.waleska404.shrek.data.remote.ShrekApi
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.domain.repository.RemoteDataSource
import com.waleska404.shrek.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class RemoteDataSourceImpl(
    private val shrekApi: ShrekApi,
    private val shrekDatabase: ShrekDatabase
) : RemoteDataSource {

    private val heroDao = shrekDatabase.characterDao()

    override fun getAllCharacters(): Flow<PagingData<ShrekCharacter>> {
        val pagingSourceFactory = { heroDao.getAllCharacters() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CharacterRemoteMediator(
                shrekApi = shrekApi,
                shrekDatabase = shrekDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchCharacters(query: String): Flow<PagingData<ShrekCharacter>> {
        TODO("Not yet implemented")
    }
    /*
    override fun searchCharacters(query: String): Flow<PagingData<ShrekCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchHeroesSource(borutoApi = shrekApi, query = query)
            }
        ).flow
    }*/
}