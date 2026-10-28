package fh.salzburg.cookbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fh.salzburg.cookbook.ui.theme.CookbookTheme

@Composable
fun RecipeItem(titel: String, untertitel: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bis E8 steht diese Fläche als Platzhalter für das spätere Rezeptbild.
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(text = titel, style = MaterialTheme.typography.titleMedium)
                Text(text = untertitel, style = MaterialTheme.typography.bodyMedium)
            }
            // Ein Icon zeigt etwas an. Die Interaktion ergänzen wir erst ab E9.
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Kein Favorit",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun RecipeItemPreview() {
    CookbookTheme {
        RecipeItem("Kürbissuppe", "Hauptgang · 25 Min")
    }
}

@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f)
@Composable
fun RecipeItemGrosseSchriftPreview() {
    CookbookTheme {
        RecipeItem("Kürbissuppe mit Ingwer und Kokosmilch", "Hauptgang · 25 Min")
    }
}
