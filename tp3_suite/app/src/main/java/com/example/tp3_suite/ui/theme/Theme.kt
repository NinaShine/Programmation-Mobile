package com.example.tp3_suite.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlin.text.Typography as Typography1

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF8BBD0),              // Rose pastel très doux
    onPrimary = Color.Black,                  // Texte sur boutons roses
    primaryContainer = Color(0xFFFFEBEE),     // Conteneur rose très pâle
    onPrimaryContainer = Color(0xFF4A148C),   // Texte accentué si besoin

    secondary = Color(0xFFE1BEE7),
    onSecondary = Color.Black,

    background = Color(0xFFFFF7FA),           // Fond presque blanc rosé
    onBackground = Color(0xFF1C1B1F),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F),

    outline = Color(0xFFBDBDBD),
    outlineVariant = Color(0xFFEDEDED),

    error = Color(0xFFB00020),
    onError = Color.White
)


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFB39DDB),             // Lilas
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE7F6),    // Lilas clair
    onPrimaryContainer = Color(0xFF311B92),  // Texte violet foncé

    secondary = Color(0xFF80CBC4),           // Menthe douce
    onSecondary = Color(0xFF00332F),

    background = Color(0xFFFDFCFB),          // Blanc chaleureux
    onBackground = Color(0xFF1C1B1F),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F),

    outline = Color(0xFFD1C4E9),             // Lilas très pâle
    outlineVariant = Color(0xFFE0E0E0),

    error = Color(0xFFD32F2F),
    onError = Color.White
)


@Composable
fun Tp3_suiteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}


