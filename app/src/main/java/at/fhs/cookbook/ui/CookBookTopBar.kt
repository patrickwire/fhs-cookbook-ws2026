package at.fhs.cookbook.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

// E11: eigener Slot-Bewohner für die obere Leiste.

@OptIn(ExperimentalMaterial3Api::class)   // bewusste Nutzung einer noch änderbaren API
@Composable
fun CookBookTopBar(onScrandleClick: () -> Unit = {}) {
    TopAppBar(
        title = { Text("CookBook") },
        actions = { TextButton(onClick = onScrandleClick) { Text("Scrandle") } },   // E15: Zugang zum Spiel
    )
}
