package com.waleska404.shrek.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.waleska404.shrek.data.remote.ShrekApi
import com.waleska404.shrek.domain.model.ShrekCharacter
import java.lang.Exception
import javax.inject.Inject

class SearchCharactersSource @Inject constructor(
    private val shrekApi: ShrekApi,
    private val query: String
) : PagingSource<Int, ShrekCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShrekCharacter> {
        return try {
            val apiResponse = shrekApi.searchCharacters(name = query)
            val characters = apiResponse.characters
            if(characters.isNotEmpty()) {
                LoadResult.Page(
                    data = characters,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShrekCharacter>): Int? {
        return state.anchorPosition
    }
}