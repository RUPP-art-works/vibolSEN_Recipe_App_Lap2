// In ui/navigation/Screen.kt
package com.vibol.recipe.ui.navigation

// UPDATED: Added a 'title' property for user-friendly names
sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")

    object Explore : Screen("explore?category={category}", "Explore") {
        fun createRoute(category: String) = "explore?category=$category"
    }

    object Favorite : Screen("favorite", "Favorite")

    // The detail screen doesn't need a title for the bottom bar
    object MealDetail : Screen("meal_detail/{mealId}", "Meal Detail") {
        fun createRoute(mealId: String) = "meal_detail/$mealId"
    }
}