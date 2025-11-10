// In ui/screens/home/HomeState.kt
package com.vibol.recipe.ui.screens.home

import com.vibol.recipe.domain.model.Category
import com.vibol.recipe.domain.model.Meal

data class HomeState(
    val isLoading: Boolean = false,
    val popularMeals: List<Meal> = emptyList(),
    val categories: List<Category> = emptyList(),
    val randomMeal: Meal? = null,
    val error: String? = null
)