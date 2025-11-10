// In ui/screens/detail/MealDetailState.kt
package com.vibol.recipe.ui.screens.detail

import com.vibol.recipe.domain.model.Meal

data class MealDetailState(
    val isLoading: Boolean = false,
    val meal: Meal? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)