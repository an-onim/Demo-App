package ru.social.core.ui.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val rippleConfiguration = RippleConfiguration(
        color = Color(0xFFF37D56),
        rippleAlpha = RippleAlpha(
            0.1f,
            0.1f,
            0.1f,
            0.1f
        )
    )

    val typography = Typography
    val shape = Shape


    CompositionLocalProvider(
        LocalSDColors provides colorScheme,
        LocalSDTypography provides typography,
        LocalSDShape provides shape,
//        LocalRippleConfiguration provides rippleConfiguration,
        content = content
    )
}