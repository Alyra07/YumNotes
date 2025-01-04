package at.mirjam.yumnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Define custom color schemes
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBrown,
    onPrimary = White,
    secondary = AccentRed,
    onSecondary = AccentPeach,
    background = Black,
    onBackground = LightGray,
    surface = DarkGray,
    onSurface = LightGray,
    tertiary = SecondaryGreen,
    onTertiary = LightGray,
    onError = AccentOrange
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBrown,
    onPrimary = White,
    secondary = AccentBeige,
    onSecondary = Black,
    background = LightGray,
    onBackground = Black,
    surface = White,
    onSurface = DarkGray,
    tertiary = AccentOrange,
    onTertiary = AccentPeach,
    onError = AccentRed
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