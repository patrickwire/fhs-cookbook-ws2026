package at.fhs.cookbook

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import at.fhs.cookbook.ui.RecipeItem
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

// E20: Compose-UI-Tests (Emulator, Sekunden). setContent stellt GENAU EINE Composable
// auf die Bühne — getestet wird die Karte, nicht die Welt.

class RecipeItemTest {

    @get:Rule
    val rule = createComposeRule()                     // stellt die Compose-Bühne

    private val testRecipe = Recipe(
        id = 1, title = "Kürbissuppe", category = Category.STARTER, minutes = 30)

    @Test
    fun zeigtDenTitel() {
        rule.setContent {
            RecipeItem(recipe = testRecipe, onClick = {}, onFavoriteClick = {})
        }
        rule.onNodeWithText("Kürbissuppe").assertIsDisplayed()
        // Tipp: onNodeWithContentDescription findet über die E7-Beschriftungen —
        // Barrierefreiheit und Testbarkeit sind dieselbe Tugend.
    }

    @Test
    fun tippMeldetOnClick() {
        var clicked = false                            // der Event-Fänger
        rule.setContent {
            RecipeItem(recipe = testRecipe, onClick = { clicked = true }, onFavoriteClick = {})
        }
        rule.onNodeWithText("Kürbissuppe").performClick()
        assertTrue(clicked)                            // die Karte hat gemeldet — UDF rückwärts
    }
}
