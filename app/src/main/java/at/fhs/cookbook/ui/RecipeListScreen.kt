package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.RecipeUiState
import at.fhs.cookbook.RecipeViewModel
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe

// E14: der E13-Inhalt, jetzt als eigene Szene.

@Composable
fun RecipeListScreen(
    state: RecipeUiState,
    viewModel: RecipeViewModel,
    onRecipeClick: (Recipe) -> Unit,   // Navigation ist Sache von CookBookApp — der Screen meldet nur
) {
    Column(Modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = state.category == null,
                onClick = { viewModel.setCategory(null) },
                label = { Text("Alle") },
            )
            Category.entries.forEach { cat ->
                FilterChip(
                    selected = state.category == cat,
                    onClick = { viewModel.setCategory(cat) },
                    label = { Text(cat.name) },
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
                    onClick = { onRecipeClick(recipe) },   // NEU in E14: die Karte ist tippbar
                )
            }
        }
    }
}
