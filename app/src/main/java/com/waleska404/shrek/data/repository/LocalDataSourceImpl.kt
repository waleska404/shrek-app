package com.waleska404.shrek.data.repository

import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.domain.repository.LocalDataSource

class LocalDataSourceImpl(shrekDatabase: ShrekDatabase): LocalDataSource {

    private val characterDao = shrekDatabase.characterDao()

    override suspend fun getSelectedCharacter(characterId: Int): ShrekCharacter {
        return characterDao.getSelectedCharacter(characterId = characterId)
    }
}