package com.example.recipeapp.data

import com.example.recipeapp.data.FavoriteRecipe
import com.example.recipeapp.dto.RecipeDto
import com.example.recipeapp.network.SpoonacularApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class RecipeRepository @Inject constructor(
    private val api: SpoonacularApi,
    private val dao: FavoriteDao
) {
    // network
    suspend fun getPopular(number: Int = 8) = api.getRandom(number = number).recipes
    suspend fun search(query: String) = api.search(query = query).results
    suspend fun getDetails(id: Int) = api.details(id)

    // local
    fun favourites(): Flow<List<FavoriteRecipe>> = dao.getAll()
    fun isFavourite(id: Int): Flow<Boolean> = dao.isFavorite(id)
    suspend fun addFavourite(dto: RecipeDto) {
        dao.insert(FavoriteRecipe(dto.id, dto.title, dto.image, dto.readyInMinutes))
    }
    suspend fun removeFavourite(id: Int) = dao.deleteById(id)
}
