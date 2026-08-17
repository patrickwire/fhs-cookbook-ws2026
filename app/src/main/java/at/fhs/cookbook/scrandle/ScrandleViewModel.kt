package at.fhs.cookbook.scrandle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhs.cookbook.data.Dish
import at.fhs.cookbook.data.ScrandlePair
import at.fhs.cookbook.data.api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// E15: UiState (Laden/Fehler/Daten + Punktestand) und die ganze Spiellogik (E12!).

data class ScrandleUiState(
    val pair: ScrandlePair? = null,     // null = noch nichts geladen
    val isLoading: Boolean = false,     // gerade unterwegs zum Server?
    val error: String? = null,          // null = alles gut
    val score: Int = 0,                 // dein Punktestand
)

class ScrandleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScrandleUiState())
    val uiState = _uiState.asStateFlow()

    init { loadPair() }                 // init läuft einmal beim Erzeugen des ViewModels

    fun loadPair() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {         // Nebenjob — endet automatisch mit dem ViewModel
            try {
                val pair = api.scrandle()
                _uiState.update { it.copy(pair = pair, isLoading = false) }
            } catch (e: Exception) {    // kein WLAN, Server down, Funkloch …
                _uiState.update { it.copy(error = "Laden fehlgeschlagen", isLoading = false) }
            }
        }
    }

    fun guess(chosen: Dish) {
        val pair = _uiState.value.pair ?: return    // .value = aktueller Stand, ohne Abo
        val better = if (pair.left.rating >= pair.right.rating) pair.left else pair.right
        if (chosen.id == better.id) _uiState.update { it.copy(score = it.score + 1) }
        loadPair()                                  // und die nächste Runde
    }
}
