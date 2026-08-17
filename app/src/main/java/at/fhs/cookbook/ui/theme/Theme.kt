package at.fhs.cookbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// E11: Platzhalter-Schemata — euer Theme-Builder-Export (Color.kt/Theme.kt aus E6) ersetzt das.
private val lightScheme = lightColorScheme()   // im Export: eure Rollen aus der Seed Color
private val darkScheme = darkColorScheme()

@Composable
fun CookBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),   // liest die Systemeinstellung
    content: @Composable () -> Unit,
) {
    val scheme = if (darkTheme) darkScheme else lightScheme
    MaterialTheme(colorScheme = scheme, content = content)
}
