package at.fhs.cookbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhs.cookbook.data.RecipeRepository
import at.fhs.cookbook.data.api
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// E16: lädt beim Start übers Repository — die Filter aus E13 laufen einfach weiter.

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository(api)   // die Tür — von Hand eingebaut

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init { loadRecipes() }                           // läuft einmal beim Erzeugen (E15)

    fun loadRecipes() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val loaded = repository.getRecipes()
                _uiState.update { it.copy(recipes = loaded, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Laden fehlgeschlagen", isLoading = false) }
            }
        }
    }

    fun setInput(value: String) = _uiState.update { it.copy(input = value) }
    fun setShowInput(value: Boolean) = _uiState.update { it.copy(showInput = value) }
    fun setCategory(category: Category?) = _uiState.update { it.copy(category = category) }
    fun toggleOnlyFavorites() = _uiState.update { it.copy(onlyFavorites = !it.onlyFavorites) }

    // Ab hier: heute noch LOKAL — ab E17 laufen diese drei über POST/PATCH/DELETE zum Server.
    fun addRecipe() = _uiState.update { state ->
        val nextId = (state.recipes.maxOfOrNull { it.id } ?: 0) + 1   // größte id + 1 — bis der Server sie vergibt (E17)
        state.copy(recipes = state.recipes + Recipe(nextId, state.input), input = "", showInput = false)
    }

    fun removeRecipe(recipe: Recipe) = _uiState.update { state ->
        state.copy(recipes = state.recipes.filter { it.id != recipe.id })
    }

    fun toggleFavorite(recipe: Recipe) = _uiState.update { state ->
        state.copy(recipes = state.recipes.map {
            if (it.id == recipe.id) it.copy(favorite = !it.favorite) else it
        })
    }
}
