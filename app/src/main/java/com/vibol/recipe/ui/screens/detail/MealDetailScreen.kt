// In ui/screens/detail/MealDetailScreen.kt
package com.vibol.recipe.ui.screens.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current // Get the context for opening URLs

    Scaffold(
        floatingActionButton = {
            state.meal?.let {
                FloatingActionButton(onClick = { viewModel.toggleFavorite() }) {
                    Icon(
                        imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(
                    text = state.error!!,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                state.meal?.let { meal ->
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        // Meal Image Header
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

                        // Meal Title, Info, and Tags
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

                                // --- TAGS SECTION ---
                                if (meal.tags.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        meal.tags.forEach { tag ->
                                            SuggestionChip(onClick = {}, label = { Text(tag) })
                                        }
                                    }
                                }
                            }
                        }

                        // Ingredients Section
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
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                            }
                        }

                        // Instructions Section
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
                                    lineHeight = 24.sp
                                )
                            }
                        }

                        // --- ACTION BUTTONS SECTION ---
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // YouTube Button
                                if (!meal.youtubeUrl.isNullOrBlank()) {
                                    Button(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.youtubeUrl))
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = "YouTube")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Watch Video")
                                    }
                                }
                                // Source Button
                                if (!meal.sourceUrl.isNullOrBlank()) {
                                    OutlinedButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.sourceUrl))
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.Launch, contentDescription = "Source")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("View Source")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}