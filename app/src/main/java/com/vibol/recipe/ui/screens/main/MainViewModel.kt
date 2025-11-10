// In ui/screens/main/MainViewModel.kt
package com.vibol.recipe.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibol.recipe.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsRepository: UserPreferencesRepository
) : ViewModel() {
    val hasCompletedOnboarding: StateFlow<Boolean?> = prefsRepository.hasCompletedOnboarding
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun onOnboardingFinished() {
        viewModelScope.launch {
            prefsRepository.setOnboardingCompleted()
        }
    }
}