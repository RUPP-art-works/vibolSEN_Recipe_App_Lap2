// In domain/model/Meal.kt
package com.vibol.recipe.domain.model

data class Meal(
    val id: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val area: String,
    val instructions: String,
    val ingredients: List<String>,

    // --- ADD THESE NEW FIELDS ---
    val tags: List<String>,
    val youtubeUrl: String?,
    val sourceUrl: String?
)