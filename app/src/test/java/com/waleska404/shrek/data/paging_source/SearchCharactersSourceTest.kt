package com.waleska404.shrek.data.paging_source

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.waleska404.shrek.data.remote.FakeShrekApi
import com.waleska404.shrek.data.remote.ShrekApi
import com.waleska404.shrek.domain.model.ShrekCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchHeroesSourceTest {

    private lateinit var shrekApi: ShrekApi
    private lateinit var characters: List<ShrekCharacter>

    @Before
    fun setup() {
        shrekApi = FakeShrekApi()
        characters = listOf(
            ShrekCharacter(
                id = 1,
                name = "Sasuke",
                image = "",
                about = "",
                rating = 5.0,
                power = 0,
                month = "",
                day = "",
                family = listOf(),
                abilities = listOf(),
                natureTypes = listOf()
            ),
            ShrekCharacter(
                id = 2,
                name = "Naruto",
                image = "",
                about = "",
                rating = 5.0,
                power = 0,
                month = "",
                day = "",
                family = listOf(),
                abilities = listOf(),
                natureTypes = listOf()
            ),
            ShrekCharacter(
                id = 3,
                name = "Sakura",
                image = "",
                about = "",
                rating = 5.0,
                power = 0,
                month = "",
                day = "",
                family = listOf(),
                abilities = listOf(),
                natureTypes = listOf()
            )
        )
    }

    @Test
    fun `Search api with existing hero name, expect single hero result, assert LoadResult_Page`() =
        runTest {
            val heroSource = SearchCharactersSource(shrekApi = shrekApi, query = "Sasuke")
            assertEquals<LoadResult<Int, ShrekCharacter>>(
                expected = LoadResult.Page(
                    data = listOf(characters.first()),
                    prevKey = null,
                    nextKey = null
                ),
                actual = heroSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with existing hero name, expect multiple hero result, assert LoadResult_Page`() =
        runTest {
            val heroSource = SearchCharactersSource(shrekApi = shrekApi, query = "Sa")
            assertEquals<LoadResult<Int, ShrekCharacter>>(
                expected = LoadResult.Page(
                    data = listOf(characters.first(), characters[2]),
                    prevKey = null,
                    nextKey = null
                ),
                actual = heroSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with empty hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val heroSource = SearchCharactersSource(shrekApi = shrekApi, query = "")
            val loadResult = heroSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = shrekApi.searchCharacters("").characters

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

    @Test
    fun `Search api with non_existing hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val heroSource = SearchCharactersSource(shrekApi = shrekApi, query = "Unknown")
            val loadResult = heroSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = shrekApi.searchCharacters("Unknown").characters

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

}












