package ru.social.core.ui.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle


object SDTheme {

    val colors: SDColors
        @Composable
        get() = LocalSDColors.current

    val typography: SDTypography
        @Composable
        get() = LocalSDTypography.current

    val shapes: SDShape
        @Composable
        get() = LocalSDShape.current
}


data class SDColors(
    val bgPrimary: Color,
    val bgActionPrimary: Color,
    val bgHighlight: Color,
    val bgSecondary: Color,
    val bgSecondaryLight: Color,
    val bgTertiary: Color,
    val bgFab: Color,

    val fgPrimary: Color,
    val fgActionPrimary: Color,
    val fgActionEmphasis: Color,
    val fgSecondary: Color,
    val fgTertiary: Color,
    val fgOnColor: Color,

    val borderColor: Color
)

data class SDTypography(
    val headingL: TextStyle,
    val headingM: TextStyle,
    val headingS: TextStyle,

    val bodyBoldL: TextStyle,
    val bodyMediumL: TextStyle,
    val bodyBoldM: TextStyle,
    val bodyMediumM: TextStyle,
    val bodyBoldS: TextStyle,
    val bodyMediumS: TextStyle,

    val caption: TextStyle,

    val bookL: TextStyle,
    val bookM: TextStyle
)

data class SDShape(
    val corners: Shape,
    val textCorners: Shape,
    val buttonCorners: Shape,
    val appBarCorners: Shape,
)

val LocalSDColors = staticCompositionLocalOf<SDColors> { error("No colors provided") }
val LocalSDTypography = staticCompositionLocalOf<SDTypography> { error("No types provided") }
val LocalSDShape = staticCompositionLocalOf<SDShape> { error("No shapes provided") }