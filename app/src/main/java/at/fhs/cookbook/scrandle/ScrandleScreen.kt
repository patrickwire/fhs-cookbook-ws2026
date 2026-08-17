package at.fhs.cookbook.scrandle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import at.fhs.cookbook.data.Dish

// E15: die vier Gesichter + zwei Karten nebeneinander.

@Serializable
object ScrandleRoute   // für den NavHost (E14): composable<ScrandleRoute> { ScrandleScreen() }

@Composable
fun ScrandleScreen(viewModel: ScrandleViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Welches Gericht ist besser bewertet?", style = MaterialTheme.typography.titleMedium)
        Text("Punkte: ${state.score}")

        val pair = state.pair
        when {
            state.isLoading -> CircularProgressIndicator()   // der Ladekreis (Material 3)
            state.error != null -> {
                Text(state.error ?: "")
                Button(onClick = { viewModel.loadPair() }) { Text("Nochmal versuchen") }
            }
            pair != null -> Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DishCard(pair.left, onClick = { viewModel.guess(pair.left) }, Modifier.weight(1f))
                DishCard(pair.right, onClick = { viewModel.guess(pair.right) }, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun DishCard(dish: Dish, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = modifier) {          // Card(onClick) aus E14
        Column {
            AsyncImage(
                model = dish.imageUrl,                      // fertige URL aus der Antwort
                contentDescription = dish.name,
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentScale = ContentScale.Crop,           // Fläche füllen statt verzerren
            )
            Text(dish.name, Modifier.padding(12.dp))
            // Das Rating wird bewusst NICHT angezeigt — sonst wäre es kein Rätsel.
        }
    }
}

// Stretch (Siegesserie): val streak: Int = 0 in den UiState; im Erfolgszweig von guess()
// streak + 1, sonst streak = 0 — und ein Text("Serie: …") im Screen.
