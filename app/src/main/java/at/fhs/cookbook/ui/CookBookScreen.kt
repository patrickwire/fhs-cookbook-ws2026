package at.fhs.cookbook.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// E9-Übungs-Endstand: der Screen besitzt den State — die Karte trifft keine Entscheidungen.

@Composable
fun CookBookScreen() {
    var favorite by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        RecipeItem("Kürbissuppe", favorite, onFavoriteClick = { favorite = !favorite })
        OutlinedTextField(
            value = input,                    // was das Feld anzeigt
            onValueChange = { input = it },   // läuft bei jedem Tastendruck
            label = { Text("Rezepttitel") },
        )
        Text("Deine Eingabe: $input")
    }
}
