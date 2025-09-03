package com.example.recipeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.dto.RecipeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RecipeRepository) : ViewModel() {
    private val _popular = MutableStateFlow<List<RecipeDto>>(emptyList())
    val popular: StateFlow<List<RecipeDto>> = _popular.asStateFlow()

    private val _all = MutableStateFlow<List<RecipeDto>>(emptyList())
    val all: StateFlow<List<RecipeDto>> = _all.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun isFavourite(id: Int): Flow<Boolean> = repo.isFavourite(id)

    fun toggleFavourite(recipe: RecipeDto) {
        viewModelScope.launch {
            repo.addFavourite(recipe)
        }
    }


    init {
        refresh()
        onSearch("Biryani")
    }


    fun refresh() = viewModelScope.launch {
        runCatching { repo.getPopular(6) }
            .onSuccess { _popular.value = it }
            .onFailure { e ->
                android.util.Log.e("HomeViewModel", "Popular recipes fetch failed", e)
            }
    }

    fun onSearch(q: String) {
        _query.value = q
        viewModelScope.launch {
            if (q.isBlank()) return@launch
            runCatching { repo.search(q) }
                .onSuccess { _all.value = it }
                .onFailure { e ->
                    android.util.Log.e("HomeViewModel", "Search failed for query: $q", e)
                }
        }
    }

}
