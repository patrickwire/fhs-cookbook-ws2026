package at.fhs.cookbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import at.fhs.cookbook.model.Recipe
import at.fhs.cookbook.model.startRecipes

// E12: Logik-Stockwerk — der State-Holder trägt State und Regeln.
// (Ausblick E13: aus `class CookBookState` wird `class RecipeViewModel : ViewModel()` —
//  gleiche Struktur, aber sie überlebt das Drehen und ist ohne UI testbar.)

class CookBookState {
    val recipes = startRecipes.toMutableStateList()
    var input by mutableStateOf("")
    var showInput by mutableStateOf(false)

    // get() = bei jedem Zugriff neu berechnet — eine Quelle, keine Kopie (Single Source of Truth)
    val canAdd get() = input.isNotBlank() && recipes.none { it.title == input }   // Stretch-Regel inklusive

    private var nextId = startRecipes.size + 1   // private: außen hat niemand ein Geschäft damit

    fun addRecipe() {
        recipes.add(Recipe(nextId++, input))
        input = ""
        showInput = false
    }

    fun removeRecipe(recipe: Recipe) = recipes.remove(recipe)

    fun toggleFavorite(recipe: Recipe) {
        recipes[recipes.indexOf(recipe)] = recipe.copy(favorite = !recipe.favorite)
    }

    // abgeleiteter Wert statt zweitem State: kann nie auseinanderlaufen
    val favoriteCount get() = recipes.count { it.favorite }
}
