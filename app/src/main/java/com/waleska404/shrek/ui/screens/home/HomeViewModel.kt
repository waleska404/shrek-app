package com.waleska404.shrek.ui.screens.home

import androidx.lifecycle.ViewModel
import com.waleska404.shrek.domain.use_cases.UseCases
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    useCases: UseCases
): ViewModel() {

    val getAllCharacters = useCases.getAllCharactersUseCase()
}