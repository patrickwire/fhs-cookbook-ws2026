package at.fhs.cookbook.ui

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import at.fhs.cookbook.RecipeViewModel
import at.fhs.cookbook.navigation.RecipeDetailRoute
import at.fhs.cookbook.navigation.RecipeListRoute
import at.fhs.cookbook.scrandle.ScrandleRoute
import at.fhs.cookbook.scrandle.ScrandleScreen
import at.fhs.cookbook.model.Recipe
import at.fhs.cookbook.ui.theme.CookBookTheme
import kotlinx.coroutines.launch

// E14-Endstand: CookBookApp besitzt ViewModel + NavController und verteilt an die Szenen.

@Composable
fun CookBookApp(viewModel: RecipeViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()   // der Regisseur — per remember gemerkt

    // E18: der Undo-Tanz — die UI dirigiert das WANN, das ViewModel kennt nur das WAS.
    // rememberCoroutineScope = der launch-Knopf der UI (≙ viewModelScope, E15);
    // showSnackbar ist suspend — sie wartet auf den MENSCHEN, nicht den Server.
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val onDelete: (Recipe) -> Unit = { recipe ->
        viewModel.removeLocally(recipe)                   // 1. sofort weg (nur UiState)
        scope.launch {
            val result = snackbarHostState.showSnackbar(  // 2. wartet, bis sie weg ist
                message = "„${recipe.title}“ gelöscht",
                actionLabel = "Rückgängig",
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.restore(recipe)                 // 3a. Reue: zurück, kein Netzaufruf
            } else {
                viewModel.confirmDelete(recipe)           // 3b. abgelaufen: das echte DELETE
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },   // der dritte Scaffold-Slot (E11)
        topBar = { CookBookTopBar(onScrandleClick = { navController.navigate(ScrandleRoute) }) },
        floatingActionButton = {   // FAB bleibt vorerst auf jedem Screen — Feinschliff später
            FloatingActionButton(onClick = { viewModel.setShowInput(true) }) {
                Icon(Icons.Default.Add, contentDescription = "Rezept hinzufügen")
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RecipeListRoute,          // die Startszene
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<RecipeListRoute> {
                RecipeListScreen(
                    state = state,
                    viewModel = viewModel,
                    onRecipeClick = { navController.navigate(RecipeDetailRoute(it.id)) },
                    onDelete = onDelete,
                )
            }
            composable<RecipeDetailRoute> { entry ->
                val route = entry.toRoute<RecipeDetailRoute>()   // Gepäck auspacken
                val recipe = state.recipes.firstOrNull { it.id == route.id }
                if (recipe == null) {
                    Text("Rezept nicht gefunden")   // Null-Fall sichtbar behandeln (E2)
                } else {
                    RecipeDetailScreen(recipe = recipe)
                }
            }
            composable<ScrandleRoute> { ScrandleScreen() }   // E15 zahlt direkt ein
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CookBookAppPreview() = CookBookTheme { CookBookApp() }
