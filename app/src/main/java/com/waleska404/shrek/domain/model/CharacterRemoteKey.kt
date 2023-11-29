package com.waleska404.shrek.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.waleska404.shrek.util.Constants.CHARACTER_REMOTE_KEY_DATABASE_TABLE


@Entity(tableName = CHARACTER_REMOTE_KEY_DATABASE_TABLE)
data class CharacterRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)