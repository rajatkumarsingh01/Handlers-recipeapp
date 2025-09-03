package com.example.recipeapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteRecipe(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: Int?
)
