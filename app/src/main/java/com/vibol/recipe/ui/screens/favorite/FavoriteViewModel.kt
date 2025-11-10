// In ui/screens/favorite/FavoriteViewModel.kt
package com.vibol.recipe.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibol.recipe.domain.model.Meal
import com.vibol.recipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    recipeRepository: RecipeRepository
) : ViewModel() {
    // Directly expose the Flow from the repository as StateFlow
    val favoriteMeals: StateFlow<List<Meal>> = recipeRepository.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}