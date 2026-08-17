package at.fhs.cookbook.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.CookBookState
import at.fhs.cookbook.ui.theme.CookBookTheme

// E12-Endstand: UI-Stockwerk — nur noch merken und anzeigen. Gleiches Verhalten, neue Ordnung.

@Composable
fun CookBookApp() {
    val state = remember { CookBookState() }   // der Screen merkt sich die Instanz

    Scaffold(
        topBar = { CookBookTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { state.showInput = true }) {
                Icon(Icons.Default.Add, contentDescription = "Rezept hinzufügen")
            }
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
            if (state.showInput) {
                OutlinedTextField(
                    value = state.input,
                    onValueChange = { state.input = it },
                    label = { Text("Neues Rezept") },
                )
                Button(onClick = { state.addRecipe() }, enabled = state.canAdd) {
                    Text("Hinzufügen")
                }
            }
            Text("${state.favoriteCount} Favoriten")   // berechnet, nie gespeichert
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        title = recipe.title,
                        favorite = recipe.favorite,
                        onFavoriteClick = { state.toggleFavorite(recipe) },
                        onDelete = { state.removeRecipe(recipe) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CookBookAppPreview() = CookBookTheme { CookBookApp() }
