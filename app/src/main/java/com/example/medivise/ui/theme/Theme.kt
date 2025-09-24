package com.example.medivise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightAppColorScheme = lightColorScheme(
    primary = AppGreenPrimary,
    onPrimary = OnAppGreenPrimary,
    primaryContainer = AppGreenPrimaryContainer,
    onPrimaryContainer = OnAppGreenPrimaryContainer,
    secondary = AppBlueSecondary,
    onSecondary = OnAppBlueSecondary,
    tertiary = AppOrangeTertiary,
    onTertiary = OnAppOrangeTertiary,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE)
)

private val DarkAppColorScheme = darkColorScheme(
    primary = AppGreenPrimary,
    onPrimary = OnAppGreenPrimary,
    primaryContainer = Color(0xFF005330),
    onPrimaryContainer = AppGreenPrimaryContainer,
    secondary = AppBlueSecondary,
    onSecondary = OnAppBlueSecondary,
    tertiary = AppOrangeTertiary,
    onTertiary = OnAppOrangeTertiary,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F)
)

@Composable
fun MediViseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkAppColorScheme else LightAppColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}