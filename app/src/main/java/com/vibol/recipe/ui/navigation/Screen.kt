// In ui/navigation/Screen.kt
package com.vibol.recipe.ui.navigation

// A sealed class to define the navigation routes in the app
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Favorite : Screen("favorite")
    // We add a placeholder for the mealId that we will pass to the detail screen
    object MealDetail : Screen("meal_detail/{mealId}") {
        // Helper function to create the route with a specific meal ID
        fun createRoute(mealId: String) = "meal_detail/$mealId"
    }
}