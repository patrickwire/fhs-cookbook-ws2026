package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.ui.theme.CookBookTheme

// E7-Endstand: die ersten eigenen Composables — Text, Button, Icon.
// Regeln der Einheit: PascalCase, kein Rückgabewert, Modifier-Parameter, Theme-Rollen statt Hex.

@Composable
fun RecipeHeader(title: String, category: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,          // Rolle aus E6, nie hartcodiert
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = category,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Button(onClick = { /* kommt in E9 — erst State, dann Verhalten */ }) {
            Text("Speichern")
        }
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Als Favorit markiert",          // Pflicht: echter Satz oder null
        )
    }
}

// Preview braucht eine parameterlose Wrapper-Funktion — und das Theme drumherum!
@Preview(showBackground = true)
@Composable
fun RecipeHeaderPreview() {
    CookBookTheme { RecipeHeader(title = "Kürbissuppe", category = "Vorspeise") }
}
