package fh.salzburg.cookbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fh.salzburg.cookbook.ui.theme.CookbookTheme

@Composable
fun CookBookScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "CookBook",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        RecipeItem("Kürbissuppe", "Hauptgang · 25 Min")
        RecipeItem("Salat", "Vorspeise · 10 Min")
        RecipeItem("Apfelkuchen", "Nachspeise · 45 Min")
    }
}

@Preview(showBackground = true)
@Composable
fun CookBookScreenPreview() {
    CookbookTheme {
        CookBookScreen()
    }
}
