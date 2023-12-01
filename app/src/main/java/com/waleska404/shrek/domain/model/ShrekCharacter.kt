package com.waleska404.shrek.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.waleska404.shrek.util.Constants.CHARACTER_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = CHARACTER_DATABASE_TABLE)
data class ShrekCharacter(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>
)