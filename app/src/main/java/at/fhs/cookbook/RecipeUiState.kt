package at.fhs.cookbook

import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe

// E13: der komplette Bildschirm-Inhalt als EIN Wert.

data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),   // alle Rezepte — die eine Quelle
    val input: String = "",                    // Text im Eingabefeld
    val showInput: Boolean = false,            // Eingabe sichtbar?
    val category: Category? = null,            // aktiver Filter — null heißt: alle
    val onlyFavorites: Boolean = false,        // nur ♥ zeigen?
) {
    // abgeleiteter State: bei jedem Zugriff frisch berechnet — nie gespeichert (E12)
    val visibleRecipes: List<Recipe>
        get() {
            var result = recipes
            if (category != null) result = result.filter { it.category == category }
            if (onlyFavorites) result = result.filter { it.favorite }
            return result
        }

    val canAdd get() = input.isNotBlank() && recipes.none { it.title == input }
}
