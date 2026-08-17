package at.fhs.cookbook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.fhs.cookbook.model.Recipe

// E18: die rote Fläche hinter der Karte — E8-Handwerk.

@Composable
fun DeleteBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd,           // Icon dort, wo die Karte hinwischt
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,                    // dekorativ — die Geste trägt die Bedeutung
            tint = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}

// E18: Geste um die Karte — remember-State (E9), Slot (E7), Veto-Lambda.

@Composable
fun SwipeableRecipeItem(
    recipe: Recipe,
    onDelete: (Recipe) -> Unit,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->                   // unser Veto-Recht
            if (value == SwipeToDismissBoxValue.EndToStart) {   // rechts → links gewischt
                onDelete(recipe)                          // Event nach oben melden (UDF)
                true                                      // Swipe vollenden lassen
            } else {
                false                                     // andere Richtung: zurückschnappen
            }
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DeleteBackground() },       // Slot: liegt UNTER der Karte
        enableDismissFromStartToEnd = false,              // nur eine Wisch-Richtung — bewusst
        modifier = modifier,
    ) {
        RecipeItem(recipe = recipe, onClick = onClick, onFavoriteClick = onFavoriteClick)
    }
}
