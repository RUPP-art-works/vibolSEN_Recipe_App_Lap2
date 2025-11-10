// In domain/repository/RecipeRepository.kt
package com.vibol.recipe.domain.repository

import com.vibol.recipe.domain.model.Category
import com.vibol.recipe.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    // Functions for fetching data from the API
    suspend fun getMeals(): List<Meal>
    suspend fun getCategories(): List<Category>

    // Functions for local database
    fun getFavorites(): Flow<List<Meal>>
    suspend fun saveFavorite(meal: Meal)
    suspend fun removeFavorite(mealId: String)
    fun isFavorite(mealId: String): Flow<Boolean>
    suspend fun getMealById(mealId: String): Meal?
}