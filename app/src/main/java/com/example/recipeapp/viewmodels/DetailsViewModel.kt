package com.example.recipeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.dto.RecipeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: RecipeRepository) : ViewModel() {

    private val _recipe = MutableStateFlow<RecipeDto?>(null)
    val recipe = _recipe.asStateFlow()

    fun load(id: Int) = viewModelScope.launch {
        _recipe.value = repo.getDetails(id)
    }

    fun toggleFavourite(dto: RecipeDto) = viewModelScope.launch {
        val isFav = repo.isFavourite(dto.id).first() // read current
        if (isFav) repo.removeFavourite(dto.id) else repo.addFavourite(dto)
    }

    fun isFavourite(id: Int): Flow<Boolean> = repo.isFavourite(id)
}
