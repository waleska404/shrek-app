package com.waleska404.shrek.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.waleska404.shrek.data.local.dao.CharacterDao
import com.waleska404.shrek.data.local.dao.CharacterRemoteKeyDao
import com.waleska404.shrek.domain.model.CharacterRemoteKey
import com.waleska404.shrek.domain.model.ShrekCharacter

@Database(entities = [ShrekCharacter::class, CharacterRemoteKey::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class ShrekDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeyDao(): CharacterRemoteKeyDao
}