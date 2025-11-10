// In ui/screens/favorite/FavoriteScreen.kt
package com.vibol.recipe.ui.screens.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.vibol.recipe.domain.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onMealClick: (String) -> Unit // <-- ADDED THIS PARAMETER
) {
    val favoriteMeals by viewModel.favoriteMeals.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Recipes") })
        }
    ) { innerPadding ->
        if (favoriteMeals.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorite meals saved yet.", fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteMeals) { meal ->
                    // UPDATED: Pass the click event
                    FavoriteMealListItem(meal = meal, onClick = { onMealClick(meal.id) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMealListItem(meal: Meal, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick // <-- UPDATED: Card is now clickable
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // UPDATED: Using a real image instead of a placeholder
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 100.dp, height = 80.dp)
            )
            Text(
                text = meal.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}