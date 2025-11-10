// In ui/screens/explore/ExploreState.kt
package com.vibol.recipe.ui.screens.explore

import com.vibol.recipe.domain.model.Category
import com.vibol.recipe.domain.model.Meal

data class ExploreState(
    val isLoading: Boolean = false,
    val allMeals: List<Meal> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategory: String = "All", // Default to show all meals
    val filteredMeals: List<Meal> = emptyList(),
    val error: String? = null
)