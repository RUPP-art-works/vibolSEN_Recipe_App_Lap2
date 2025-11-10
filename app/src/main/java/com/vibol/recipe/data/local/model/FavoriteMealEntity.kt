// In data/local/model/FavoriteMealEntity.kt
package com.vibol.recipe.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// This class represents the "favorite_meals" table in your database
@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey
    val mealId: String,
    val name: String,
    val imageUrl: String
)