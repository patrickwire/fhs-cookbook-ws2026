package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import at.fhs.cookbook.model.Recipe

// E14: Detail-Screen — nur Column + Texte, E7/E8-Handwerk.
// Stretch (Zurück-Pfeil): der App-Bar ein navigationIcon geben — IconButton mit
// Icons.AutoMirrored.Filled.ArrowBack und onClick = { navController.navigateUp() }.

@Composable
fun RecipeDetailScreen(recipe: Recipe) {
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),   // macht die Column scrollbar
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(                                    // Stretch aus der Übung: das große Bild
            model = recipe.imageUrl,
            contentDescription = recipe.title,
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        Text(recipe.title, style = MaterialTheme.typography.headlineMedium)
        Text("${recipe.minutes} Min · ${recipe.category.name}")

        Text("Zutaten", style = MaterialTheme.typography.titleMedium)
        recipe.ingredients.forEach { Text("• $it") }

        Text("Zubereitung", style = MaterialTheme.typography.titleMedium)
        // forEachIndexed = forEach mit mitlaufender Nummer (ab 0) — die Übungs-Lücke:
        recipe.steps.forEachIndexed { i, step -> Text("${i + 1}. $step") }
    }
}
