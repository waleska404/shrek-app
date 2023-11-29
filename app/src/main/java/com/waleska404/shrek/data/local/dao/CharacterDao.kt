package com.waleska404.shrek.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.waleska404.shrek.domain.model.ShrekCharacter

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun getAllCharacters(): PagingSource<Int, ShrekCharacter>

    @Query("SELECT * FROM character_table WHERE id = :characterId")
    fun getSelectedCharacter(characterId: Int): ShrekCharacter

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacters(characters: List<ShrekCharacter>)

    @Query("DELETE FROM character_table")
    suspend fun deleteAllCharacters()
}