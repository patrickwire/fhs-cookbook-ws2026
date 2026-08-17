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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.model.Recipe
import at.fhs.cookbook.model.startRecipes
import at.fhs.cookbook.ui.theme.CookBookTheme

// E11-Endstand: der Umbau — Scaffold hält die Zonen, der FAB blendet die Eingabe ein.

@Composable
fun CookBookApp() {
    val recipes = remember { startRecipes.toMutableStateList() }
    var input by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(startRecipes.size + 1) }
    var showInput by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CookBookTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { showInput = true }) {
                Icon(Icons.Default.Add, contentDescription = "Rezept hinzufügen")
            }
        },
    ) { innerPadding ->
        // innerPadding NICHT wegwerfen — sonst beginnt die Liste unter der App-Bar
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
            if (showInput) {   // bedingtes Anzeigen: UI folgt dem State
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Neues Rezept") },
                )
                Button(
                    onClick = {
                        recipes.add(Recipe(nextId++, input))
                        input = ""
                        showInput = false
                    },
                    enabled = input.isNotBlank(),
                ) { Text("Hinzufügen") }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        title = recipe.title,
                        favorite = recipe.favorite,
                        onFavoriteClick = {
                            recipes[recipes.indexOf(recipe)] = recipe.copy(favorite = !recipe.favorite)
                        },
                        onDelete = { recipes.remove(recipe) },
                    )
                }
            }
        }
    }
}

// Zwei Previews zeigen hell und dunkel gleichzeitig — der billigste Dark-Mode-Test.
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CookBookAppPreview() = CookBookTheme { CookBookApp() }
