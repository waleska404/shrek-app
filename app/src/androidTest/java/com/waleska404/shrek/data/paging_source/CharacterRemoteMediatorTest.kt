package com.waleska404.shrek.data.paging_source

import androidx.paging.*
import androidx.paging.RemoteMediator.*
import androidx.test.core.app.ApplicationProvider
import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.data.remote.FakeShrekApi2
import com.waleska404.shrek.domain.model.ShrekCharacter
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRemoteMediatorTest {

    private lateinit var shrekApi: FakeShrekApi2
    private lateinit var shrekDatabase: ShrekDatabase

    @Before
    fun setup() {
        shrekApi = FakeShrekApi2()
        shrekDatabase = ShrekDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @After
    fun cleanup() {
        shrekDatabase.clearAllTables()
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() =
        runBlocking {
            val remoteMediator = CharacterRemoteMediator(
                shrekApi = shrekApi,
                shrekDatabase = shrekDatabase
            )
            val pagingState = PagingState<Int, ShrekCharacter>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Success)
            assertFalse((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() =
        runBlocking {
            shrekApi.clearData()
            val remoteMediator = CharacterRemoteMediator(
                shrekApi = shrekApi,
                shrekDatabase = shrekDatabase
            )
            val pagingState = PagingState<Int, ShrekCharacter>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Success)
            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() =
        runBlocking {
            shrekApi.addException()
            val remoteMediator = CharacterRemoteMediator(
                shrekApi = shrekApi,
                shrekDatabase = shrekDatabase
            )
            val pagingState = PagingState<Int, ShrekCharacter>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Error)
        }

}
