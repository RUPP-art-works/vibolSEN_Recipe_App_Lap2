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
            .filter { !it.id.isNullOrBlank() }
            .map { it.toDomainMeal() }
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories(Constants.DB_NAME_HEADER)
            .filter { !it.id.isNullOrBlank() }
            .map {
                Category(id = it.id!!, name = it.name ?: "Unknown", imageUrl = it.imageUrl ?: "")
            }
    }

    override fun getFavorites(): Flow<List<Meal>> {
        return dao.getFavorites().map { entities ->
            entities.map {
                // UPDATED: Added default values for the new fields (tags, youtubeUrl, sourceUrl)
                Meal(
                    id = it.mealId,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    category = "", // Not stored in favorites table
                    area = "", // Not stored
                    instructions = "", // Not stored
                    ingredients = emptyList(), // Not stored
                    tags = emptyList(), // Not stored
                    youtubeUrl = null, // Not stored
                    sourceUrl = null // Not stored
                )
            }
        }
    }

    override suspend fun saveFavorite(meal: Meal) {
        // This remains the same as FavoriteMealEntity only stores basic info
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
        return api.getMeals(Constants.DB_NAME_HEADER)
            .map { it.toDomainMeal() }
            .find { it.id == mealId }
    }
}

// UPDATED: This function now maps the new fields
private fun MealDto.toDomainMeal(): Meal {
    val ingredientList = this.ingredients?.mapNotNull { ingredientDto ->
        if (!ingredientDto.ingredient.isNullOrBlank()) {
            "${ingredientDto.measure ?: ""} ${ingredientDto.ingredient}".trim()
        } else {
            null
        }
    } ?: emptyList()

    // NEW LOGIC: Split the tags string into a list of strings
    val tagsList = this.tags
        ?.split(",")
        ?.filter { it.isNotBlank() }
        ?: emptyList()

    return Meal(
        id = this.id!!,
        name = this.name ?: "Unknown Meal",
        imageUrl = this.imageUrl ?: "",
        category = this.category ?: "Unknown",
        area = this.area ?: "Unknown",
        instructions = this.instructions ?: "",
        ingredients = ingredientList,
        // --- MAP THE NEW FIELDS ---
        tags = tagsList,
        youtubeUrl = this.youtube,
        sourceUrl = this.source
    )
}