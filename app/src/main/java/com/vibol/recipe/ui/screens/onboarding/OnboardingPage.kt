// In ui/screens/onboarding/OnboardingPage.kt
package com.vibol.recipe.ui.screens.onboarding

import androidx.annotation.DrawableRes // <-- ADD THIS IMPORT STATEMENT

data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)