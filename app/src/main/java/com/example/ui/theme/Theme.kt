package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val KrishiLightColorScheme = lightColorScheme(
    primary = ForestGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = SageContainer,
    onPrimaryContainer = OnSageContainer,
    secondary = ForestGreenLight,
    onSecondary = Color.White,
    secondaryContainer = SurfaceMistHigh,
    onSecondaryContainer = ForestGreenDark,
    tertiary = WarmAmberAccent,
    onTertiary = Color.White,
    tertiaryContainer = WarmAmberContainer,
    onTertiaryContainer = OnWarmAmberContainer,
    background = SoftOffWhiteBg,
    onBackground = DarkSlateText,
    surface = SurfaceWhite,
    onSurface = DarkSlateText,
    surfaceVariant = SurfaceMist,
    onSurfaceVariant = MutedSlateText,
    outline = OutlineSubtle,
    outlineVariant = SurfaceMistHigh,
    error = WrongCrimson,
    onError = Color.White,
    errorContainer = WrongCrimsonBg,
    onErrorContainer = WrongCrimson
)

private val KrishiDarkColorScheme = darkColorScheme(
    primary = DarkPrimarySage,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = WarmAmberBright,
    onSecondary = DarkOnPrimary,
    secondaryContainer = DarkPineSurfaceVariant,
    onSecondaryContainer = DarkTextPrimary,
    tertiary = DarkAmberAccent,
    onTertiary = DarkOnPrimary,
    tertiaryContainer = DarkAmberContainer,
    onTertiaryContainer = DarkOnAmberContainer,
    background = DarkPineBackground,
    onBackground = DarkTextPrimary,
    surface = DarkPineSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkPineSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkOutline,
    outlineVariant = DarkPineSurfaceVariant,
    error = Color(0xFFFCA5A5),
    onError = Color(0xFF450A0A),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA)
)

val KrishiShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun KrishiPrepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) KrishiDarkColorScheme else KrishiLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = KrishiTypography,
        shapes = KrishiShapes,
        content = content
    )
}
