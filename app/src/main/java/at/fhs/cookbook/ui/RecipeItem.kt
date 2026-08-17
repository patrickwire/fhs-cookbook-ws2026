package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Die Karte aus E9, erweitert um onDelete.

@Composable
fun RecipeItem(
    title: String,
    favorite: Boolean,
    onFavoriteClick: () -> Unit,
    onDelete: () -> Unit,               // neu in E10: Löschen ist ein Event der Karte
    onClick: () -> Unit = {},           // NEU in E14: Karte tippbar — Default hält alte Aufrufer am Leben
) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) { Text(title) }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorit",
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Löschen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() =
    RecipeItem("Kürbissuppe", favorite = false, onFavoriteClick = {}, onDelete = {})
