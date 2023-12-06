package com.waleska404.shrek.domain.repository

import com.waleska404.shrek.domain.model.ShrekCharacter

interface LocalDataSource {
    suspend fun getSelectedCharacter(characterId: Int): ShrekCharacter
}