package at.fhs.cookbook.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import at.fhs.cookbook.RecipeViewModel
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.ui.theme.CookBookTheme

// E13-Endstand: die UI abonniert den UiState und wird zum reinen Anzeiger.
// Drehtest: Rezept anlegen, Favorit setzen, Gerät drehen — alles bleibt. Das ist E13.

@Composable
fun CookBookApp(viewModel: RecipeViewModel = viewModel()) {   // viewModel() = immer dieselbe Instanz
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { CookBookTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.setShowInput(true) }) {
                Icon(Icons.Default.Add, contentDescription = "Rezept hinzufügen")
            }
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {

            // Filter-Chips: "Alle" + eine je Kategorie + nur-Favoriten (Stretch der Übung)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = state.category == null,
                    onClick = { viewModel.setCategory(null) },
                    label = { Text("Alle") },
                )
                Category.entries.forEach { cat ->   // entries = alle enum-Werte als Liste
                    FilterChip(
                        selected = state.category == cat,
                        onClick = { viewModel.setCategory(cat) },
                        label = { Text(cat.name) },   // deutsch machen: Feinschliff/zuhause
                    )
                }
                FilterChip(
                    selected = state.onlyFavorites,
                    onClick = { viewModel.toggleOnlyFavorites() },
                    label = { Text("♥") },
                )
            }

            if (state.showInput) {
                OutlinedTextField(
                    value = state.input,
                    onValueChange = { viewModel.setInput(it) },
                    label = { Text("Neues Rezept") },
                )
                Button(onClick = { viewModel.addRecipe() }, enabled = state.canAdd) {
                    Text("Hinzufügen")
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.visibleRecipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        title = recipe.title,
                        favorite = recipe.favorite,
                        onFavoriteClick = { viewModel.toggleFavorite(recipe) },
                        onDelete = { viewModel.removeRecipe(recipe) },
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
