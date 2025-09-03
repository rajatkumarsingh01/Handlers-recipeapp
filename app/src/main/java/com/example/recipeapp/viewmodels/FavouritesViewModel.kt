package com.example.recipeapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.FavoriteRecipe
import com.example.recipeapp.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repo: RecipeRepository) : ViewModel() {
    val favourites: Flow<List<FavoriteRecipe>> = repo.favourites()
}
