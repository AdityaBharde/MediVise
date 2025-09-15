package com.example.medivise.ui.theme

import androidx.compose.foundation.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

// ...

// Assuming AppGreenPrimary is your main branding color
private val LightAppColorScheme = lightColorScheme(
    primary = AppGreenPrimary,
    onPrimary = OnAppGreenPrimary,
    primaryContainer = AppGreenPrimaryContainer,
    onPrimaryContainer = OnAppGreenPrimaryContainer,

    secondary = AppBlueSecondary, // Assign your secondary color
    onSecondary = OnAppBlueSecondary,
    // secondaryContainer = ...,
    // onSecondaryContainer = ...,

    tertiary = AppOrangeTertiary,   // Assign your tertiary color
    onTertiary = OnAppOrangeTertiary,
    // tertiaryContainer = ...,
    // onTertiaryContainer = ...,

    background = Color(0xFFFFFBFE), // Example background
    surface = Color(0xFFFFFBFE),    // Example surface
    // ... other colors (error, outline, surfaceVariant, etc.)
)

private val DarkAppColorScheme = darkColorScheme(
    primary = AppGreenPrimary, // Or a slightly adjusted green for dark theme if needed
    onPrimary = OnAppGreenPrimary,
    primaryContainer = Color(0xFF005330), // Darker container for dark theme
    onPrimaryContainer = AppGreenPrimaryContainer,

    secondary = AppBlueSecondary, // Adjust for dark theme if needed
    onSecondary = OnAppBlueSecondary,

    tertiary = AppOrangeTertiary, // Adjust for dark theme if needed
    onTertiary = OnAppOrangeTertiary,

    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    // ... other colors for dark theme
)

@Composable
fun MediviseTheme( // Or whatever your AppTheme is named
    darkTheme: Boolean = isSystemInDarkTheme(),
    // ... dynamicColor parameters if you use them ...
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkAppColorScheme else LightAppColorScheme
    // ... rest of your theme setup (SideEffect for status bar, etc.) ...

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}