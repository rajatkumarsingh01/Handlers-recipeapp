package com.example.recipeapp.network

import com.example.recipeapp.dto.ComplexSearchResponse
import com.example.recipeapp.dto.RandomRecipesResponse
import com.example.recipeapp.dto.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SpoonacularApi{
    @GET("recipes/random")
    suspend fun getRandom(
        @Query("number") number: Int = 8,
        @Query("addRecipeInformation") addInfo: Boolean = true
    ): RandomRecipesResponse

    @GET("recipes/complexSearch")
    suspend fun search(
        @Query("query") query: String,
        @Query("number") number: Int = 20,
        @Query("addRecipeInformation") addInfo: Boolean = true
    ): ComplexSearchResponse

    @GET("recipes/{id}/information")
    suspend fun details(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean = false
    ): RecipeDto
}
