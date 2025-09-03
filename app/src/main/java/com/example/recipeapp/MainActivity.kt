package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.screens.DetailsScreen
import com.example.recipeapp.screens.FavouritesScreen
import com.example.recipeapp.screens.HomeScreen
import com.example.recipeapp.ui.theme.RecipeAppTheme
import com.example.recipeapp.viewmodels.DetailsViewModel
import com.example.recipeapp.viewmodels.FavouritesViewModel
import com.example.recipeapp.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                val navController = rememberNavController()
                val items = listOf("home", "favourites")

                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

                        data class BottomItem(
                            val route: String,
                            val label: String,
                            val selectedIcon: ImageVector,
                            val unselectedIcon: ImageVector
                        )
                        val items = listOf(
                            BottomItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
                            BottomItem("favourites", "Favourite", Icons.Filled.Favorite, Icons.Default.FavoriteBorder)
                        )

                        NavigationBar {
                            items.forEach { item ->
                                val selected = currentRoute == item.route ||
                                        (currentRoute?.startsWith(item.route) == true)

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.label
                                        )
                                    },
                                    label = { Text(item.label) },
                                    selected = selected,
                                    onClick = {
                                        if (!selected) {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color.Red,
                                        selectedTextColor = Color.Red,
                                        unselectedIconColor = Color(0xFF9E9E9E),
                                        unselectedTextColor = Color(0xFF9E9E9E),
                                        indicatorColor = Color.Transparent   // no pill background
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            val vm: HomeViewModel = hiltViewModel()
                            HomeScreen(
                                vm = vm,
                                onRecipeClick = { id ->
                                    navController.navigate("details/$id")
                                }
                            )
                        }

                        composable("favourites") {
                            val vm: FavouritesViewModel = hiltViewModel()
                            FavouritesScreen(
                                vm = vm,
                                onRecipeClick = { id ->
                                    navController.navigate("details/$id")
                                }
                            )
                        }

                        composable("details/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments
                                ?.getString("id")
                                ?.toIntOrNull() ?: 0

                            val vm: DetailsViewModel = hiltViewModel()
                            DetailsScreen(
                                id = id,
                                vm = vm,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
