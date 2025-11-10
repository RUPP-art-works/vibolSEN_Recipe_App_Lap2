// In di/AppModule.kt
package com.vibol.recipe.di

import android.app.Application
import androidx.room.Room
import com.vibol.recipe.data.local.db.RecipeDatabase
import com.vibol.recipe.data.remote.api.RecipeApiService
import com.vibol.recipe.data.repository.RecipeRepositoryImpl
import com.vibol.recipe.domain.repository.RecipeRepository
import com.vibol.recipe.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// @Module tells Hilt that this is a module that provides dependencies.
@Module
// @InstallIn(SingletonComponent::class) means the dependencies provided here will live
// as long as the application is alive (they will be singletons).
@InstallIn(SingletonComponent::class)
object AppModule {

    // --- NETWORK RELATED DEPENDENCIES ---

    @Provides
    @Singleton // @Singleton ensures there's only one instance of Retrofit throughout the app.
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeApiService(retrofit: Retrofit): RecipeApiService {
        // Hilt sees that this function needs a Retrofit instance. It looks at the function above,
        // sees it can provide Retrofit, and automatically passes it in.
        return retrofit.create(RecipeApiService::class.java)
    }


    // --- DATABASE RELATED DEPENDENCIES ---

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        // Hilt can provide the Application context by default.
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe_database.db"
        ).build()
    }


    // --- REPOSITORY DEPENDENCY ---

    @Provides
    @Singleton
    fun provideRecipeRepository(api: RecipeApiService, db: RecipeDatabase): RecipeRepository {
        // This function tells Hilt how to build our repository.
        // It needs RecipeApiService and RecipeDatabase. Hilt knows how to create both
        // from the functions we defined above, so it provides them automatically.
        // We return the implementation (RecipeRepositoryImpl), but the return type is the
        // interface (RecipeRepository), which is a good practice for abstraction.
        return RecipeRepositoryImpl(api, db.favoriteMealDao())
    }
}