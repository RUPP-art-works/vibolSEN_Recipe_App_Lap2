// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // The kotlin.compose plugin is often not needed here anymore,
    // but the most important thing is adding Hilt.

    alias(libs.plugins.hilt.android) apply false // <-- ADD THIS LINE
}