package com.waleska404.shrek.domain.use_cases.search_characters

import androidx.paging.PagingData
import com.waleska404.shrek.data.repository.Repository
import com.waleska404.shrek.domain.model.ShrekCharacter
import kotlinx.coroutines.flow.Flow

class SearchCharactersUseCase(
    val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<ShrekCharacter>> {
        return repository.searchCharacters(query = query)
    }
}