// In data/local/dao/FavoriteMealDao.kt
package com.vibol.recipe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vibol.recipe.data.local.model.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {

    // Insert a meal, replace it if it already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(meal: FavoriteMealEntity)

    // Delete a meal by its ID
    @Query("DELETE FROM favorite_meals WHERE mealId = :mealId")
    suspend fun removeFavorite(mealId: String)

    // Get all saved favorite meals as a Flow (automatically updates UI)
    @Query("SELECT * FROM favorite_meals")
    fun getFavorites(): Flow<List<FavoriteMealEntity>>

    // Check if a specific meal is already a favorite
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE mealId = :mealId)")
    fun isFavorite(mealId: String): Flow<Boolean>
}