// In data/local/db/RecipeDatabase.kt
package com.vibol.recipe.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vibol.recipe.data.local.dao.FavoriteMealDao
import com.vibol.recipe.data.local.model.FavoriteMealEntity

@Database(
    entities = [FavoriteMealEntity::class],
    version = 1
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun favoriteMealDao(): FavoriteMealDao
}