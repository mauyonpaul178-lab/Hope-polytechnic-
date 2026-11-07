package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
  primary = NavyPrimary,
  onPrimary = Color.White,
  primaryContainer = NavyLight,
  onPrimaryContainer = Color.White,
  secondary = CrimsonRed,
  onSecondary = Color.White,
  secondaryContainer = CrimsonLight.copy(alpha = 0.2f),
  onSecondaryContainer = CrimsonDark,
  tertiary = GoldAccent,
  onTertiary = NavyDark,
  tertiaryContainer = GoldLight,
  onTertiaryContainer = NavyDark,
  background = BackgroundLight,
  onBackground = TextPrimary,
  surface = SurfaceWhite,
  onSurface = TextPrimary,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = TextSecondary,
  outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFF8FB1ED),
  onPrimary = NavyDark,
  primaryContainer = NavyLight,
  onPrimaryContainer = Color.White,
  secondary = CrimsonLight,
  onSecondary = Color.White,
  secondaryContainer = CrimsonDark,
  onSecondaryContainer = Color.White,
  tertiary = GoldAccent,
  onTertiary = NavyDark,
  background = NavyDark,
  onBackground = Color.White,
  surface = NavySurface,
  onSurface = Color.White,
  surfaceVariant = Color(0xFF1E293B),
  onSurfaceVariant = Color(0xFFCBD5E1),
  outline = Color(0xFF334155)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Keep brand fidelity: use official Navy & Red palette by default
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = NavyDark.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
