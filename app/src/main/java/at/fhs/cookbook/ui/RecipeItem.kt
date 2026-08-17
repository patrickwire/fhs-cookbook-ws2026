package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.ui.theme.CookBookTheme

// E8-Endstand: die Rezeptkarte vom E5-Wireframe — noch statisch (interaktiv wird sie in E9).
// Übungs-Bausteine: Row + CenterVertically, weight(1f) am Textbereich, titleMedium.

@Composable
fun RecipeItem(title: String, category: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {                 // weight schiebt das Herz an den Rand
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(category, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    CookBookTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {   // Stretch: drei Karten mit spacedBy
            RecipeItem("Kürbissuppe", "Vorspeise · 25 Min")
            RecipeItem("Salat", "Vorspeise · 10 Min")
            RecipeItem("Curry", "Hauptgang · 40 Min")
        }
    }
}
