package com.example.ui.theme

import android.app.Activity
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = CoolPrimary,
    secondary = CoolSecondary,
    tertiary = CoolTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = LightText,
    onSecondary = LightText,
    onTertiary = LightText,
    onBackground = LightText,
    onSurface = LightText,
    onSurfaceVariant = GrayText,
    outlineVariant = DarkLineColor
)

private val LightColorScheme = lightColorScheme(
    primary = CoolPrimary,
    secondary = CoolSecondary,
    tertiary = CoolTertiary,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onPrimary = LightBackground,
    onSecondary = LightBackground,
    onTertiary = LightBackground,
    onBackground = DarkText,
    onSurface = DarkText,
    onSurfaceVariant = DarkGrayText,
    outlineVariant = LightLineColor
)

@Composable
fun animateColorSchemeAsState(
    targetColorScheme: ColorScheme,
    animationSpec: androidx.compose.animation.core.AnimationSpec<androidx.compose.ui.graphics.Color> = tween(500)
): ColorScheme {
    val primary by animateColorAsState(targetColorScheme.primary, animationSpec)
    val secondary by animateColorAsState(targetColorScheme.secondary, animationSpec)
    val tertiary by animateColorAsState(targetColorScheme.tertiary, animationSpec)
    val background by animateColorAsState(targetColorScheme.background, animationSpec)
    val surface by animateColorAsState(targetColorScheme.surface, animationSpec)
    val surfaceVariant by animateColorAsState(targetColorScheme.surfaceVariant, animationSpec)
    val onPrimary by animateColorAsState(targetColorScheme.onPrimary, animationSpec)
    val onSecondary by animateColorAsState(targetColorScheme.onSecondary, animationSpec)
    val onTertiary by animateColorAsState(targetColorScheme.onTertiary, animationSpec)
    val onBackground by animateColorAsState(targetColorScheme.onBackground, animationSpec)
    val onSurface by animateColorAsState(targetColorScheme.onSurface, animationSpec)
    val onSurfaceVariant by animateColorAsState(targetColorScheme.onSurfaceVariant, animationSpec)
    val outlineVariant by animateColorAsState(targetColorScheme.outlineVariant, animationSpec)

    return targetColorScheme.copy(
        primary = primary,
        secondary = secondary,
        tertiary = tertiary,
        background = background,
        surface = surface,
        surfaceVariant = surfaceVariant,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onTertiary = onTertiary,
        onBackground = onBackground,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
        outlineVariant = outlineVariant
    )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  primaryColor: androidx.compose.ui.graphics.Color = CoolPrimary,
  dynamicColor: Boolean = false,
  amoled: Boolean = false,
  content: @Composable () -> Unit,
) {
  val baseColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  val targetColorScheme = if (darkTheme && amoled) {
      baseColorScheme.copy(
          primary = primaryColor,
          background = Color.Black,
          surface = Color(0xFF0C0C0C),
          surfaceVariant = Color(0xFF141414),
          onBackground = Color(0xFFF1F5F9),
          onSurface = Color(0xFFF1F5F9),
          onSurfaceVariant = Color(0xFF94A3B8)
      )
  } else {
      baseColorScheme.copy(
          primary = primaryColor
      )
  }
  val colorScheme = animateColorSchemeAsState(targetColorScheme)
  
  val view = LocalView.current
  if (!view.isInEditMode) {
      SideEffect {
          val window = (view.context as Activity).window
          window.statusBarColor = colorScheme.background.toArgb()
          WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
      }
  }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
