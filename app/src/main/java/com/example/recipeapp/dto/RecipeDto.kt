package com.example.recipeapp.dto

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: Int?,
    val summary: String?,
    val instructions: String?
)

data class RandomRecipesResponse(
    val recipes: List<RecipeDto>)
data class ComplexSearchResponse(
    val results: List<RecipeDto>,
    val totalResults: Int
)
