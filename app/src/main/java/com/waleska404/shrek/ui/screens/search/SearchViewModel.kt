package com.waleska404.shrek.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedCharacters = MutableStateFlow<PagingData<ShrekCharacter>>(PagingData.empty())
    val searchedCharacters = _searchedCharacters

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchCharacters(query: String) {
        /*
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchHeroesUseCase(query = query).cachedIn(viewModelScope).collect {
                _searchedHeroes.value = it
            }
        }*/
    }

}