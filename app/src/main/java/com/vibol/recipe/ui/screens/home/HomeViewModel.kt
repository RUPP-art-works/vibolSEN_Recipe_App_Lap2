// In ui/screens/home/HomeViewModel.kt
package com.vibol.recipe.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibol.recipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel tells Hilt this is a ViewModel and its dependencies should be injected.
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // _state is the private, mutable version of our state. Only the ViewModel can change it.
    private val _state = MutableStateFlow(HomeState())
    // state is the public, read-only version that the UI will observe.
    val state = _state.asStateFlow()

    // The init block is called when the ViewModel is first created.
    init {
        loadHomepageData()
    }

    private fun loadHomepageData() {
        // Set loading to true before starting the network call.
        _state.update { it.copy(isLoading = true) }

        // Launch a coroutine to fetch data without blocking the main thread.
        viewModelScope.launch {
            try {
                val meals = recipeRepository.getMeals()
                val categories = recipeRepository.getCategories()

                // Update the state with the fetched data and set loading to false.
                _state.update {
                    it.copy(
                        isLoading = false,
                        popularMeals = meals.take(10), // Let's just show 10 popular meals
                        randomMeal = meals.randomOrNull(),
                        categories = categories
                    )
                }
            } catch (e: Exception) {
                // If an error occurs, update the state with the error message.
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load data: ${e.message}"
                    )
                }
            }
        }
    }
}