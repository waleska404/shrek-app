package com.waleska404.shrek.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.waleska404.shrek.data.local.dao.CharacterDao
import com.waleska404.shrek.data.local.dao.CharacterRemoteKeysDao
import com.waleska404.shrek.domain.model.CharacterRemoteKeys
import com.waleska404.shrek.domain.model.ShrekCharacter

@Database(entities = [ShrekCharacter::class, CharacterRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class ShrekDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
}