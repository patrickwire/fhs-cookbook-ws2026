package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items          // items(liste) { … } — Extension für LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList     // vorhandene Liste → State-Liste
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.model.Recipe
import at.fhs.cookbook.model.startRecipes

// E10-Endstand: Liste + Eingabe mit Validierung, Löschen, Empty State.

@Composable
fun CookBookScreen() {
    // NICHT mutableStateListOf(startRecipes) — das wäre eine Liste MIT einer Liste drin!
    val recipes = remember { startRecipes.toMutableStateList() }
    var input by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(startRecipes.size + 1) }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Neues Rezept") },
        )
        Button(
            onClick = {
                recipes.add(Recipe(nextId++, input))   // Liste meldet die Änderung
                input = ""                             // Feld leeren — value ist die Wahrheit
            },
            enabled = input.isNotBlank(),              // Validierung: leer wird gar kein Rezept
        ) { Text("Hinzufügen") }

        if (recipes.isEmpty()) {
            Text("Noch keine Rezepte — leg das erste an!")   // Empty State (Stretch, E5)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        title = recipe.title,
                        favorite = recipe.favorite,
                        // Ein Element in der Liste ändern ist sperrig — genau die Motivation für E13:
                        onFavoriteClick = {
                            recipes[recipes.indexOf(recipe)] = recipe.copy(favorite = !recipe.favorite)
                        },
                        onDelete = { recipes.remove(recipe) },   // die Liste entscheidet, was Löschen heißt
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CookBookScreenPreview() = CookBookScreen()
