// In ui/screens/explore/ExploreViewModel.kt
package com.vibol.recipe.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibol.recipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreState())
    val state = _state.asStateFlow()

    init {
        loadExploreData()
    }

    // Function to be called when a filter chip is clicked
    fun onCategorySelected(category: String) {
        _state.update { it.copy(selectedCategory = category) }
        filterMeals()
    }

    private fun loadExploreData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val meals = recipeRepository.getMeals()
                val categories = recipeRepository.getCategories()
                _state.update {
                    it.copy(
                        isLoading = false,
                        allMeals = meals,
                        categories = categories
                    )
                }
                // Apply the initial "All" filter
                filterMeals()
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Failed to load data")
                }
            }
        }
    }

    // The core filtering logic
    private fun filterMeals() {
        val currentState = _state.value
        val filteredList = if (currentState.selectedCategory == "All") {
            currentState.allMeals
        } else {
            currentState.allMeals.filter { it.category == currentState.selectedCategory }
        }
        _state.update { it.copy(filteredMeals = filteredList) }
    }
}