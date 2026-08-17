package at.fhs.cookbook

import androidx.lifecycle.ViewModel
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import at.fhs.cookbook.model.startRecipes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// E13: der State-Holder aus E12 mit Superkräften — überlebt das Drehen.

class RecipeViewModel : ViewModel() {   // ": ViewModel()" = erbt — überlebt das Drehen

    private val _uiState = MutableStateFlow(RecipeUiState(recipes = startRecipes))
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()   // außen: nur lesbar

    private var nextId = startRecipes.size + 1   // wie in E10/E12 — außen geht das niemanden an

    fun setInput(value: String) = _uiState.update { it.copy(input = value) }
    fun setShowInput(value: Boolean) = _uiState.update { it.copy(showInput = value) }
    fun setCategory(category: Category?) = _uiState.update { it.copy(category = category) }
    fun toggleOnlyFavorites() = _uiState.update { it.copy(onlyFavorites = !it.onlyFavorites) }

    fun addRecipe() = _uiState.update { state ->
        state.copy(
            recipes = state.recipes + Recipe(nextId++, state.input),   // + = neue Liste mit Anhang
            input = "",
            showInput = false,
        )
    }

    fun removeRecipe(recipe: Recipe) = _uiState.update { state ->
        state.copy(recipes = state.recipes.filter { it.id != recipe.id })   // neue Liste ohne das eine
    }

    fun toggleFavorite(recipe: Recipe) = _uiState.update { state ->
        state.copy(recipes = state.recipes.map {
            if (it.id == recipe.id) it.copy(favorite = !it.favorite) else it
        })
    }
}
