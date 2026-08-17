package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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

// E9: die Karte aus E8 wird interaktiv — per State Hoisting bleibt sie „dumm".

@Composable
fun RecipeItem(
    title: String,
    favorite: Boolean,             // Wert kommt rein …
    onFavoriteClick: () -> Unit,   // … Ereignis geht raus (State Hoisting)
) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) { Text(title) }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorit",
                )
            }
        }
    }
}

// Zwei Previews: die stateless Karte kann beide Varianten zeigen — ohne Klick.
@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() = RecipeItem("Kürbissuppe", favorite = false, onFavoriteClick = {})

@Preview(showBackground = true)
@Composable
fun RecipeItemFavoritePreview() = RecipeItem("Kürbissuppe", favorite = true, onFavoriteClick = {})
