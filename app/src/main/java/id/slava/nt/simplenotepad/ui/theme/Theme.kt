package id.slava.nt.simplenotepad.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import id.slava.nt.simplenotepad.R
import java.util.Locale

private val DarkColorPalette = darkColors(
    primary = GreenGrey80,
    primaryVariant = GreenGrey30,
    secondary = Green100,
    secondaryVariant = LightGreen100,
    background = GreenGrey30
)

private val LightColorPalette = lightColors(
    primary = GreenGrey50,
    primaryVariant = GreenGrey30,
    secondary = Green100,
    secondaryVariant = LightGreen100,
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

private val DarkColorPaletteUk = darkColors(
    primary = BlueGrey80,
    primaryVariant = BlueGrey30,
    secondary = Blue100,
    secondaryVariant = DarkYellow100,
    background = BlueGrey30
)

private val LightColorPaletteUk = lightColors(
    primary = BlueGrey50,
    primaryVariant = BlueGrey30,
    secondary = Blue100,
    secondaryVariant = LightYellow100,
    background = BlueGrey90

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
fun SimpleNotepadTheme(darkTheme: Boolean = isSystemInDarkTheme(),
                       content: @Composable () -> Unit) {

    val colors =
        when (Locale.getDefault().language){

            "uk"->  if (darkTheme) {
                            DarkColorPaletteUk
                        } else {
                            LightColorPaletteUk }
            else -> { if (darkTheme) {
                            DarkColorPalette
                        } else {
                            LightColorPalette }
            }
        }


//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//       LightColorPalette
//    }

    val statusNavigationBarColor = colorResource(id = R.color.statNavbarColor)
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusNavigationBarColor.toArgb() // change color status bar here
            window.navigationBarColor = statusNavigationBarColor.toArgb()  // to change navigation bar color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}