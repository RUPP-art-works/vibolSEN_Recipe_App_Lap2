// In data/remote/dto/MealDto.kt
package com.vibol.recipe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealDto(
    @SerializedName("id")
    val id: String?,

    @SerializedName("meal")
    val name: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("area")
    val area: String?,

    @SerializedName("instructions")
    val instructions: String?,

    @SerializedName("mealThumb")
    val imageUrl: String?,

    @SerializedName("ingredients")
    val ingredients: List<IngredientDto>?,

    // --- ADD THESE NEW FIELDS ---
    @SerializedName("tags")
    val tags: String?,

    @SerializedName("youtube")
    val youtube: String?,

    @SerializedName("source")
    val source: String?
)