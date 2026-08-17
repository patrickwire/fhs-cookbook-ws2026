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

    // E17: Anlegen — der Server vergibt die id, seine Antwort ist die Wahrheit.
    fun addRecipe(title: String, category: Category) {
        val draft = Recipe(id = 0, title = title, category = category)  // id 0 = „noch keine"
        _uiState.update { it.copy(input = "", showInput = false) }
        viewModelScope.launch {
            try {
                val created = repository.addRecipe(draft)               // Antwort MIT echter id
                _uiState.update { it.copy(recipes = it.recipes + created) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Speichern fehlgeschlagen") }
            }
        }
    }

    // E18: optimistisch — 1. sofort anzeigen · 2. Server-Wahrheit · 3. Rollback
    fun toggleFavorite(recipe: Recipe) {
        val optimistic = recipe.copy(favorite = !recipe.favorite)
        _uiState.update { st ->                                       // 1. VOR dem launch:
            st.copy(recipes = st.recipes.map {                        //    Herz füllt sich sofort
                if (it.id == recipe.id) optimistic else it
            })
        }
        viewModelScope.launch {
            try {
                val updated = repository.setFavorite(recipe.id, optimistic.favorite)
                _uiState.update { st ->                               // 2. Server-Antwort
                    st.copy(recipes = st.recipes.map {                //    übernehmen (E17-Regel)
                        if (it.id == updated.id) updated else it
                    })
                }
            } catch (e: Exception) {
                _uiState.update { st ->                               // 3. ROLLBACK: das
                    st.copy(                                          //    unveränderte Original
                        recipes = st.recipes.map {                    //    aus dem Parameter
                            if (it.id == recipe.id) recipe else it
                        },
                        error = "Ändern fehlgeschlagen",              //    … + Meldung.
                    )                                                 //    Leise ja — stumm nie!
                }
            }
        }
    }

    // E18: die drei Teilschritte des Undo-Tanzes — keiner kennt die Snackbar;
    // das WANN dirigiert die UI (rememberCoroutineScope).

    fun removeLocally(recipe: Recipe) = _uiState.update {             // Karte raus — NUR UiState,
        it.copy(recipes = it.recipes.filter { r -> r.id != recipe.id })   // der Server weiß nichts
    }

    fun restore(recipe: Recipe) = _uiState.update {                   // Reue: einfach zurücklegen —
        it.copy(recipes = it.recipes + recipe)                        // es wurde ja nie gelöscht
    }

    fun confirmDelete(recipe: Recipe) {                               // Reue-Zeit um: JETZT löschen
        viewModelScope.launch {
            try {
                repository.deleteRecipe(recipe.id)
            } catch (e: Exception) {
                restore(recipe)                                       // Rollback …
                _uiState.update { it.copy(error = "Löschen fehlgeschlagen") }   // … + Meldung
            }
        }
    }

    // Stretch: Bearbeiten-Screen → PUT (Muster identisch zu toggleFavorite)
    fun saveRecipe(edited: Recipe) {
        viewModelScope.launch {
            try {
                val updated = repository.updateRecipe(edited)
                _uiState.update { state ->
                    state.copy(recipes = state.recipes.map {
                        if (it.id == updated.id) updated else it
                    })
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Speichern fehlgeschlagen") }
            }
        }
    }
}
