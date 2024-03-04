package com.innovativetools.ai.myaiassistant.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = teal_200,
    primaryVariant = GreenShadow,
    secondary = StrokeColorDark,
    secondaryVariant = CardBackground,
    background = Black,
    surface = TextColorDark,
    error = TomatoRed,
    onPrimary = CardBorderDark,
    onSecondary = DarkBackground,
    onSurface = InactiveInputDark,
    onBackground = StrokeColorDark,
)

private val LightColorPalette = lightColors(
    primary = teal_200,
    primaryVariant = GreenShadow,
    secondary = StrokeColorDark,
    secondaryVariant = CardBackground,
    background = Black,
    surface = TextColorDark,
    error = TomatoRed,
    onPrimary = CardBorderDark,
    onSecondary = DarkBackground,
    onSurface = InactiveInputDark,
    onBackground = StrokeColorDark,
//    primary = teal_200,
//    primaryVariant = GreenShadow,
//    secondary = StrokeColor,
//    secondaryVariant = CardBackground,
//    background = Background,
//    surface = TextColor,
//    error = TomatoRed,
//    onPrimary = CardBorder,
//    onSecondary = CapabilitiesBackground,
//    onSurface = InactiveInput,
//    onBackground = MessageBackground
)

@Composable
fun MyAiAssistantTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = if (darkTheme) {
        TypographyDark
    } else {
        Typography
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}