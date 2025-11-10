// In ui/navigation/AppNavigationHost.kt
package com.vibol.recipe.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vibol.recipe.ui.components.AppBottomNavigationBar
import com.vibol.recipe.ui.screens.detail.MealDetailScreen
import com.vibol.recipe.ui.screens.explore.ExploreScreen
import com.vibol.recipe.ui.screens.favorite.FavoriteScreen
import com.vibol.recipe.ui.screens.home.HomeScreen

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home Screen Destination
            composable(Screen.Home.route) {
                HomeScreen(
                    onMealClick = { mealId ->
                        navController.navigate(Screen.MealDetail.createRoute(mealId))
                    }
                )
            }

            // Explore Screen Destination
            composable(Screen.Explore.route) {
                ExploreScreen(
                    onMealClick = { mealId ->
                        navController.navigate(Screen.MealDetail.createRoute(mealId))
                    }
                )
            }

            // Favorite Screen Destination
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    onMealClick = { mealId ->
                        navController.navigate(Screen.MealDetail.createRoute(mealId))
                    }
                )
            }

            // Meal Detail Screen Destination
            composable(
                route = Screen.MealDetail.route,
                arguments = listOf(navArgument("mealId") { type = androidx.navigation.NavType.StringType })
            ) {
                MealDetailScreen()
            }
        }
    }
}