package com.waleska404.shrek.domain.use_cases.save_onboarding

import com.waleska404.shrek.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed = completed)
    }
}