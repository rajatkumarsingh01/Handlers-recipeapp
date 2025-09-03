package com.example.recipeapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeapp.viewmodels.DetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(id: Int, vm: DetailsViewModel, onBack: () -> Unit) {
    LaunchedEffect(id) { vm.load(id) }
    val recipe by vm.recipe.collectAsState()
    val isFav by vm.isFavourite(id).collectAsState(initial = false)

    Scaffold(topBar = { TopAppBar(title = { Text(recipe?.title ?: "Detail") }, navigationIcon = { IconButton(onClick = onBack) { Icon(
        Icons.Default.ArrowBack, "") } }) }) {
        recipe?.let { r ->
            Column(modifier = Modifier.padding(16.dp)) {
                AsyncImage(model = r.image, contentDescription = r.title, modifier = Modifier.fillMaxWidth().height(220.dp))
                Spacer(Modifier.height(8.dp))
                Text(r.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(6.dp))
                Text("Ready in ${r.readyInMinutes ?: "-"} min")
                Spacer(Modifier.height(10.dp))
                Text("Instructions", style = MaterialTheme.typography.titleMedium)
                Text(r.instructions ?: r.summary ?: "No instructions")
                Spacer(Modifier.height(12.dp))
                Button(onClick = { vm.toggleFavourite(r) }) {
                    Text(if (isFav) "Remove from favourites" else "Add to favourites")
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    }
}
