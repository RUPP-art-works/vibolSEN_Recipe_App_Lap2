// In data/remote/dto/CategoryDto.kt
package com.vibol.recipe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id") // CHANGED: from "idCategory"
    val id: String?,

    @SerializedName("category") // CHANGED: from "strCategory"
    val name: String?,

    @SerializedName("categoryThumb") // CHANGED: from "strCategoryThumb"
    val imageUrl: String?,

    // This field exists in the API but we don't need it for our app,
    // so it's fine to not include it here. Gson will just ignore it.
    // val categoryDescription: String?
)