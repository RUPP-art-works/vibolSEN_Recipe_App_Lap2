// In ui/components/AppBottomNavigationBar.kt
package com.vibol.recipe.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vibol.recipe.ui.navigation.Screen

@Composable
fun AppBottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Explore,
        Screen.Favorite
    )

    val icons = mapOf(
        Screen.Home to Icons.Default.Home,
        Screen.Explore to Icons.Default.Search,
        Screen.Favorite to Icons.Default.Favorite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            val baseRoute = screen.route.substringBefore("?")
            NavigationBarItem(
                icon = { Icon(icons[screen]!!, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute?.startsWith(baseRoute) == true,
                onClick = {
                    // THIS IS THE FIX: Navigate to the base route without arguments
                    navController.navigate(baseRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}