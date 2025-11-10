// In data/repository/RecipeRepositoryImpl.kt
package com.vibol.recipe.data.repository

import com.vibol.recipe.data.local.dao.FavoriteMealDao
import com.vibol.recipe.data.local.model.FavoriteMealEntity
import com.vibol.recipe.data.remote.api.RecipeApiService
import com.vibol.recipe.data.remote.dto.MealDto
import com.vibol.recipe.domain.model.Category
import com.vibol.recipe.domain.model.Meal
import com.vibol.recipe.domain.repository.RecipeRepository
import com.vibol.recipe.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApiService,
    private val dao: FavoriteMealDao
) : RecipeRepository {

    override suspend fun getMeals(): List<Meal> {
        return api.getMeals(Constants.DB_NAME_HEADER)
            .filter { !it.id.isNullOrBlank() } // Keep this filter for safety
            .map { it.toDomainMeal() }
    }

    override suspend fun getCategories(): List<Category> {
        // This is based on the older API structure, assuming categories are still the same.
        // If categories also fail, we will need to check that DTO as well.
        return api.getCategories(Constants.DB_NAME_HEADER)
            .filter { !it.id.isNullOrBlank() }
            .map {
                Category(id = it.id!!, name = it.name ?: "Unknown", imageUrl = it.imageUrl ?: "")
            }
    }

    override fun getFavorites(): Flow<List<Meal>> {
        return dao.getFavorites().map { entities ->
            entities.map {
                Meal(id = it.mealId, name = it.name, imageUrl = it.imageUrl, "", "", "", emptyList())
            }
        }
    }

    override suspend fun saveFavorite(meal: Meal) {
        val entity = FavoriteMealEntity(mealId = meal.id, name = meal.name, imageUrl = meal.imageUrl)
        dao.saveFavorite(entity)
    }

    override suspend fun removeFavorite(mealId: String) {
        dao.removeFavorite(mealId)
    }

    override fun isFavorite(mealId: String): Flow<Boolean> {
        return dao.isFavorite(mealId)
    }

    override suspend fun getMealById(mealId: String): Meal? {
        // Fetch all meals and then find the specific one by its ID.
        return api.getMeals(Constants.DB_NAME_HEADER)
            .map { it.toDomainMeal() }
            .find { it.id == mealId }
    }
}

// THIS MAPPING LOGIC IS NOW MUCH SIMPLER AND CORRECT
private fun MealDto.toDomainMeal(): Meal {
    // Safely map the new ingredients list
    val ingredientList = this.ingredients?.mapNotNull { ingredientDto ->
        // Combine measure and ingredient, only if ingredient is not blank
        if (!ingredientDto.ingredient.isNullOrBlank()) {
            "${ingredientDto.measure ?: ""} ${ingredientDto.ingredient}".trim()
        } else {
            null
        }
    } ?: emptyList()

    return Meal(
        id = this.id!!, // We can use !! because we filtered nulls
        name = this.name ?: "Unknown Meal",
        imageUrl = this.imageUrl ?: "",
        category = this.category ?: "Unknown",
        area = this.area ?: "Unknown",
        instructions = this.instructions ?: "",
        ingredients = ingredientList
    )
}