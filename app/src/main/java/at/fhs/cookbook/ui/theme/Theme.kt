package at.fhs.cookbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Assistenten-Stand: Material-3-Standardfarben. Das eigene Designsystem kommt in E11 (Theme Builder, E6).
private val lightScheme = lightColorScheme()
private val darkScheme = darkColorScheme()

@Composable
fun CookBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val scheme = if (darkTheme) darkScheme else lightScheme
    MaterialTheme(colorScheme = scheme, content = content)
}
