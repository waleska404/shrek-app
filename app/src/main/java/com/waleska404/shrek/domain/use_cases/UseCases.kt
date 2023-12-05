package com.waleska404.shrek.domain.use_cases

import com.waleska404.shrek.domain.use_cases.get_all_characters.GetAllCharactersUseCase
import com.waleska404.shrek.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.waleska404.shrek.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.waleska404.shrek.domain.use_cases.search_characters.SearchCharactersUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllCharactersUseCase: GetAllCharactersUseCase,
    val searchCharactersUseCase: SearchCharactersUseCase
)