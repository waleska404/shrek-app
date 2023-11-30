package com.waleska404.shrek.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waleska404.shrek.domain.model.CharacterRemoteKeys

@Dao
interface CharacterRemoteKeysDao {

    @Query("SELECT * FROM character_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): CharacterRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(characterRemoteKeys: List<CharacterRemoteKeys>)

    @Query("DELETE FROM character_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}