// In ui/screens/detail/MealDetailViewModel.kt
package com.vibol.recipe.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibol.recipe.domain.model.Meal
import com.vibol.recipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MealDetailState())
    val state = _state.asStateFlow()

    private val mealId: String? = savedStateHandle.get<String>("mealId")

    init {
        if (mealId != null) {
            loadMealDetails(mealId)
            checkIfFavorite(mealId)
        } else {
            _state.update { it.copy(error = "Meal ID not found") }
        }
    }

    // NEW: Function to handle the favorite button click
    fun toggleFavorite() {
        viewModelScope.launch {
            val meal = _state.value.meal
            if (meal != null) {
                if (_state.value.isFavorite) {
                    recipeRepository.removeFavorite(meal.id)
                } else {
                    recipeRepository.saveFavorite(meal)
                }
            }
        }
    }

    // NEW: Observe the database to see if the favorite status changes
    private fun checkIfFavorite(mealId: String) {
        recipeRepository.isFavorite(mealId)
            .onEach { isFav ->
                _state.update { it.copy(isFavorite = isFav) }
            }.launchIn(viewModelScope)
    }

    private fun loadMealDetails(mealId: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val meal = recipeRepository.getMealById(mealId)
                _state.update {
                    it.copy(isLoading = false, meal = meal)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Failed to load meal details.")
                }
            }
        }
    }
}