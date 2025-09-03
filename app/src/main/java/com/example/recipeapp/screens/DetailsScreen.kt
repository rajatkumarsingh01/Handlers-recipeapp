package com.example.recipeapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeapp.viewmodels.DetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(id: Int, vm: DetailsViewModel, onBack: () -> Unit) {
    // Load recipe when screen launches
    LaunchedEffect(id) { vm.load(id) }

    val recipe by vm.recipe.collectAsState()
    val isFav by vm.isFavourite(id).collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.title ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        recipe?.let { r ->
            // Scrollable column for long instructions
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Recipe Image
                AsyncImage(
                    model = r.image,
                    contentDescription = r.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                // Recipe Title & Ready time
                Text(r.title, style = MaterialTheme.typography.headlineSmall)
                Text("Ready in ${r.readyInMinutes ?: "-"} min")

                // Instructions / Summary
                Text("Instructions", style = MaterialTheme.typography.titleMedium)
                Text(r.instructions ?: r.summary ?: "No instructions available")

                // Favourite Button
                Button(
                    onClick = { vm.toggleFavourite(r) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isFav) "Remove from favourites" else "Add to favourites")
                }
            }
        } ?: run {
            // Show progress indicator while loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
