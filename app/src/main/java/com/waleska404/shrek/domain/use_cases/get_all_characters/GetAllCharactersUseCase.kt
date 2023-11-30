package com.waleska404.shrek.domain.use_cases.get_all_characters

import androidx.paging.PagingData
import com.waleska404.shrek.data.repository.Repository
import com.waleska404.shrek.domain.model.ShrekCharacter
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<ShrekCharacter>> {
        return repository.getAllCharacters()
    }
}