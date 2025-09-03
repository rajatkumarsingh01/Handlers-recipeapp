package com.example.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.recipeapp.FavoriteRecipe
import com.example.recipeapp.viewmodels.FavouritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    vm: FavouritesViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val favourites by vm.favourites.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("Favourite recipes", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            items(favourites) { recipe ->
                FavouriteCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
            }
        }
    }
}

@Composable
fun FavouriteCard(recipe: FavoriteRecipe, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp)) // light grey border
            .background(Color.White) // white background like screenshot
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Text(
                text = "Ready in ${recipe.readyInMinutes ?: "-"} min",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF546E7A) // muted dark grey like screenshot
            )
        }
    }
}
