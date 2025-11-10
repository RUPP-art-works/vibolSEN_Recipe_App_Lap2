// In data/remote/api/RecipeApiService.kt
package com.vibol.recipe.data.remote.api

import com.vibol.recipe.data.remote.dto.CategoryDto
import com.vibol.recipe.data.remote.dto.MealDto
import retrofit2.http.GET
import retrofit2.http.Header

interface RecipeApiService {

    @GET("meals")
    suspend fun getMeals(@Header("X-DB-NAME") dbName: String): List<MealDto>

    @GET("categories")
    suspend fun getCategories(@Header("X-DB-NAME") dbName: String): List<CategoryDto>

}