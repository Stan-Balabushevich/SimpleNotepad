package id.slava.nt.simplenotepad.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = GreenGrey80,
    primaryVariant = GreenGrey30,
    secondary = GreenGrey100,
    background = GreenGrey30
)

private val LightColorPalette = lightColors(
    primary = GreenGrey50,
    primaryVariant = GreenGrey30,
    secondary = GreenGrey100,
    background = GreenGrey90

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
fun SimpleNotepadTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }


    val systemUiController = rememberSystemUiController()
    SideEffect {
        // to change system bar color
        systemUiController.setStatusBarColor(
            color = GreenGrey30,
            darkIcons = false
        )
        // to change navigation bar color
        systemUiController.setNavigationBarColor(
            color = GreenGrey30,
            darkIcons = false
        )
    }




    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}