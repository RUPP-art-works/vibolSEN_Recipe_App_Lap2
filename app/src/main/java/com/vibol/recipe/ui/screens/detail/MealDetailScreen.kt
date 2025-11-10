// In ui/screens/detail/MealDetailScreen.kt
package com.vibol.recipe.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // A Scaffold is used to easily place a FloatingActionButton on the screen.
    Scaffold(
        floatingActionButton = {
            // The FAB is only shown when the meal has loaded successfully.
            state.meal?.let {
                FloatingActionButton(onClick = { viewModel.toggleFavorite() }) {
                    Icon(
                        // The icon changes based on whether the meal is a favorite.
                        imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite"
                    )
                }
            }
        }
    ) { innerPadding ->
        // The main content area of the screen.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding from the Scaffold to avoid overlap.
        ) {
            when {
                state.isLoading -> {
                    // Show a loading indicator while fetching data.
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    // Show an error message if something went wrong.
                    Text(
                        text = state.error!!,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                state.meal != null -> {
                    // If the meal is loaded, display the details.
                    val meal = state.meal!!
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        // 1. Meal Image Header
                        item {
                            AsyncImage(
                                model = meal.imageUrl,
                                contentDescription = meal.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            )
                        }

                        // 2. Meal Title and Info
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = meal.name,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Cuisine: ${meal.area} | Category: ${meal.category}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        // 3. Ingredients Section
                        item {
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Text(
                                    text = "Ingredients",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                meal.ingredients.forEach { ingredient ->
                                    Text(
                                        text = "• $ingredient",
                                        modifier = Modifier.padding(bottom = 4.dp),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }

                        // 4. Instructions Section
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Instructions",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = meal.instructions,
                                    style = MaterialTheme.typography.bodyLarge,
                                    lineHeight = 24.sp // Improves readability
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}