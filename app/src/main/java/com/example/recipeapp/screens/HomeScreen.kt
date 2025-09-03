package com.example.recipeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeapp.dto.RecipeDto
import com.example.recipeapp.viewmodels.HomeViewModel

@Composable
fun HomeScreen(vm: HomeViewModel, onRecipeClick: (Int) -> Unit) {
    val popular by vm.popular.collectAsState()
    val all by vm.all.collectAsState()
    val query by vm.query.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Greeting
        Text(
            "👋 Hey Rajat",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            "Discover tasty and healthy recipe",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        // Search bar
        TextField(
            value = query,
            onValueChange = { vm.onSearch(it) },
            placeholder = { Text("Search any recipe", color = Color.Gray) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF3F6FB)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF3F6FB),
                unfocusedContainerColor = Color(0xFFF3F6FB),
                disabledContainerColor = Color(0xFFF3F6FB),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )


        Spacer(Modifier.height(20.dp))

        // Popular recipes
        Text(
            "Popular Recipes",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(popular) { r ->
                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(160.dp)
                        .clickable { onRecipeClick(r.id) },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = r.image,
                            contentDescription = r.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Column(
                            Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                        ) {
                            Text(r.title, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Ready in ${r.readyInMinutes ?: "-"} min", color = Color.White)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // All recipes
        Text(
            "All recipes",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(all) { r ->
                val isFav by vm.isFavourite(r.id).collectAsState(initial = false)
                RecipeRow(
                    r = r,
                    isFav = isFav,
                    onClick = { onRecipeClick(r.id) },
                    onFav = { vm.toggleFavourite(r) }
                )
            }
        }
    }
}

@Composable
fun RecipeRow(
    r: RecipeDto,
    isFav: Boolean,
    onClick: () -> Unit,
    onFav: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = r.image,
                contentDescription = r.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(r.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Ready in ${r.readyInMinutes ?: "-"} min",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            // Heart button styled inside circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onFav) {
                    Icon(
                        imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "fav",
                        tint = if (isFav) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}
