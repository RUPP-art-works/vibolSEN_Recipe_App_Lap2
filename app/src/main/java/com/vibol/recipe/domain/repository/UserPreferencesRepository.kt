// In domain/repository/UserPreferencesRepository.kt
package com.vibol.recipe.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore
    private val onboardingCompletedKey = booleanPreferencesKey("onboarding_completed")

    val hasCompletedOnboarding: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[onboardingCompletedKey] ?: false
    }

    suspend fun setOnboardingCompleted() {
        dataStore.edit { prefs ->
            prefs[onboardingCompletedKey] = true
        }
    }
}