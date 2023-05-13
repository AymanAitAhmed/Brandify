package lr.aym.projet_fin_detudes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Main200,
    primaryVariant = Main500,
    secondary = secondary200,
    background = DarkGray900,
    surface = DarkGray700,
    onBackground = DarkGray200,
    onSecondary = DarkGray500,
)

private val LightColorPalette = lightColors(
    primary = Main500,
    primaryVariant = Main700,
    secondary = secondary200,
    onSecondary = DarkGray400

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Projet_fin_detudesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}