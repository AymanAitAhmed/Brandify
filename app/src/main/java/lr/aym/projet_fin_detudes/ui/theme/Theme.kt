package lr.aym.projet_fin_detudes.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary = Main200,
    primaryVariant = Main500,
    secondary = secondary200,
    background = DarkGray900,
    surface = DarkGray700,
    onBackground = DarkGray200,
    onSurface = DarkGray400,
    onSecondary = DarkGray500,
)

private val LightColorPalette = lightColors(
    primary = Main500,
    primaryVariant = Main700,
    secondary = secondary200,
    onSecondary = DarkGray400,
    background = Color.White,
    surface = DarkGray100,
    onBackground = DarkGray900,
    onSurface = DarkGray700,

    /* Other default colors to override

    onPrimary = Color.White,
    onSecondary = Color.Black,

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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}