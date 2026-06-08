package com.example

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class AgeViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AgeDatabase.getDatabase(application).ageDao()
    private val prefs = application.getSharedPreferences("age_prefs", Context.MODE_PRIVATE)

    var savedDob: LocalDate
        get() {
            val epochDay = prefs.getLong("saved_dob", LocalDate.of(1990, 1, 1).toEpochDay())
            return LocalDate.ofEpochDay(epochDay)
        }
        set(value) {
            prefs.edit().putLong("saved_dob", value.toEpochDay()).apply()
        }

    var savedBackgroundImageUri: String?
        get() = prefs.getString("saved_background_image_uri", null)
        set(value) {
            prefs.edit().putString("saved_background_image_uri", value).apply()
        }

    var savedColorHex: Int
        get() = prefs.getInt("saved_color_hex", 0xFF6366F1.toInt())
        set(value) {
            prefs.edit().putInt("saved_color_hex", value).apply()
        }

    var themeMode: Int
        get() = prefs.getInt("theme_mode", 2) // 0 = Light, 1 = Dark, 2 = System
        set(value) {
            prefs.edit().putInt("theme_mode", value).apply()
        }

    var isAmoledEnabled: Boolean
        get() = prefs.getBoolean("amoled_enabled", false)
        set(value) {
            prefs.edit().putBoolean("amoled_enabled", value).apply()
        }

    var isSystemFontEnabled: Boolean
        get() = prefs.getBoolean("system_font_enabled", true)
        set(value) {
            prefs.edit().putBoolean("system_font_enabled", value).apply()
        }

    var isWallpaperBlurEnabled: Boolean
        get() = prefs.getBoolean("wallpaper_blur_enabled", true)
        set(value) {
            prefs.edit().putBoolean("wallpaper_blur_enabled", value).apply()
        }

    var wallpaperBlurRadius: Float
        get() = prefs.getFloat("wallpaper_blur_radius", 15f)
        set(value) {
            prefs.edit().putFloat("wallpaper_blur_radius", value).apply()
        }

    val history: StateFlow<List<AgeCalculationHistory>> = dao.getRecentCalculations().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun saveCalculation(dob: LocalDate, today: LocalDate) {
        viewModelScope.launch {
            dao.insertCalculation(
                AgeCalculationHistory(
                    dob = dob.toEpochDay(),
                    today = today.toEpochDay()
                )
            )
        }
    }
}
