package com.waleska404.shrek.data.remote

import com.waleska404.shrek.domain.model.ApiResponse
import com.waleska404.shrek.domain.model.ShrekCharacter

class FakeShrekApi : ShrekApi {

    private val characters = listOf(
        ShrekCharacter(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        ShrekCharacter(
            id = 2,
            name = "Naruto",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        ShrekCharacter(
            id = 3,
            name = "Sakura",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        )
    )

    override suspend fun getAllCharacters(page: Int): ApiResponse {
        return ApiResponse(
            success = false
        )
    }

    override suspend fun searchCharacters(name: String): ApiResponse {
        val searchedHeroes = findHeroes(name = name)
        return ApiResponse(
            success = true,
            message = "ok",
            characters = searchedHeroes
        )
    }

    private fun findHeroes(name: String): List<ShrekCharacter> {
        val founded = mutableListOf<ShrekCharacter>()
        return if (name.isNotEmpty()) {
            characters.forEach { hero ->
                if (hero.name.lowercase().contains(name.lowercase())) {
                    founded.add(hero)
                }
            }
            founded
        } else {
            emptyList()
        }
    }
}