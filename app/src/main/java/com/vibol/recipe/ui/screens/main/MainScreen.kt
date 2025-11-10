// In ui/screens/main/MainScreen.kt
package com.vibol.recipe.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.vibol.recipe.ui.navigation.AppNavigationHost // We will create this next
import com.vibol.recipe.ui.screens.onboarding.OnboardingScreen

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val hasCompletedOnboarding by viewModel.hasCompletedOnboarding.collectAsState()

    when (hasCompletedOnboarding) {
        true -> AppNavigationHost()
        false -> OnboardingScreen(onFinished = { viewModel.onOnboardingFinished() })
        null -> {} // Show a loading screen or splash screen here if you want
    }
}