package at.mirjam.yumnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black

// Define custom color schemes
private val DarkColorScheme = darkColorScheme(
    primary = PrettyGreen,
    onPrimary = White,
    secondary = AccentPeach,
    onSecondary = DarkGray,
    background = Black,
    onBackground = LightGray,
    surface = DarkGray, // color of selected tab in NavBar
    surfaceContainer = DarkGray,
    onSurface = LightGray, //
    onSurfaceVariant = LightGray,
    tertiary = DarkBrown,
    onTertiary = White,
    error = AccentOrange
)

private val LightColorScheme = lightColorScheme(
    primary = PrettyGreen,
    onPrimary = White,
    secondary = DarkBrown,
    onSecondary = White,
    background = LightGray,
    onBackground = Black,
    surface = White,
    surfaceContainer = LightGray,
    onSurface = DarkGray,
    onSurfaceVariant = DarkGray,
    tertiary = AccentBeige,
    onTertiary = Black,
    error = AccentRed
)

@Composable
fun YumNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}