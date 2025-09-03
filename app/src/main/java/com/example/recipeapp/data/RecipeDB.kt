package com.example.recipeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.FavoriteRecipe

@Database(entities = [FavoriteRecipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
