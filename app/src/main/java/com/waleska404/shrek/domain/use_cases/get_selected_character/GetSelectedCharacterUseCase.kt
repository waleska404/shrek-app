package com.waleska404.shrek.domain.use_cases.get_selected_character

import com.waleska404.shrek.data.repository.Repository
import com.waleska404.shrek.domain.model.ShrekCharacter

class GetSelectedCharacterUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(characterId: Int): ShrekCharacter {
        return repository.getSelectedCharacter(characterId = characterId)
    }
}