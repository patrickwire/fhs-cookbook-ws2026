package at.fhs.cookbook

import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe

// E13 + E16: der komplette Bildschirm-Inhalt als EIN Wert — jetzt mit den zwei Netz-Feldern.

data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),   // startet LEER — startRecipes ist in Rente
    val input: String = "",
    val showInput: Boolean = false,
    val category: Category? = null,
    val onlyFavorites: Boolean = false,
    val isLoading: Boolean = false,            // NEU in E16 (Scrandle-Muster)
    val error: String? = null,                 // NEU in E16
) {
    val visibleRecipes: List<Recipe>
        get() {
            var result = recipes
            if (category != null) result = result.filter { it.category == category }
            if (onlyFavorites) result = result.filter { it.favorite }
            return result
        }

    val canAdd get() = input.isNotBlank() && recipes.none { it.title == input }
}
