package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import at.fhs.cookbook.model.Recipe

// E16: die Karte bekommt jetzt das ganze Recipe — und ein Bild vom Server.
// E18: das Löschen-Icon ist raus — die Swipe-Geste ersetzt es (zwei Löschwege verwirren).

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.imageUrl,               // fertige URL aus der Antwort (E15)
                contentDescription = recipe.title,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)),
            )
            Column(Modifier.weight(1f).padding(start = 12.dp)) { Text(recipe.title) }
            FavoriteButton(favorite = recipe.favorite, onFavoriteClick = onFavoriteClick)   // E21: das Herz reist
        }
    }
}
