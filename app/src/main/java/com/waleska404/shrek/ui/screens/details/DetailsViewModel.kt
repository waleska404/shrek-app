package com.waleska404.shrek.ui.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.domain.use_cases.UseCases
import com.waleska404.shrek.util.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedCharacter: MutableStateFlow<ShrekCharacter?> = MutableStateFlow(null)
    val selectedCharacter: StateFlow<ShrekCharacter?> = _selectedCharacter

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val characterId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedCharacter.value = characterId?.let { useCases.getSelectedCharacterUseCase(characterId = characterId) }
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }

}

sealed class UiEvent {
    object GenerateColorPalette : UiEvent()
}