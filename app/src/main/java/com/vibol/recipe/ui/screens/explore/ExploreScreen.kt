// In ui/screens/explore/ExploreScreen.kt
package com.vibol.recipe.ui.screens.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vibol.recipe.ui.screens.favorite.FavoriteMealListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onMealClick: (String) -> Unit // <-- ADDED THIS PARAMETER
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Explore Recipes") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // --- Filter Chips Section ---
                LazyRow(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // "All" chip
                    item {
                        FilterChip(
                            selected = state.selectedCategory == "All",
                            onClick = { viewModel.onCategorySelected("All") },
                            label = { Text("All") }
                        )
                    }
                    // Category chips
                    items(state.categories) { category ->
                        FilterChip(
                            selected = state.selectedCategory == category.name,
                            onClick = { viewModel.onCategorySelected(category.name) },
                            label = { Text(category.name) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // --- Filtered Meals List ---
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.filteredMeals) { meal ->
                        // UPDATED: Pass the click event
                        FavoriteMealListItem(meal = meal, onClick = { onMealClick(meal.id) })
                    }
                }
            }
        }
    }
}