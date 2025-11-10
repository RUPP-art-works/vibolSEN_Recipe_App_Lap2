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

    // This single line replaces all 40 of the old ingredient/measure fields
    @SerializedName("ingredients")
    val ingredients: List<IngredientDto>?
)