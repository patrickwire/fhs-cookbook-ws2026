package at.fhs.cookbook

import at.fhs.cookbook.data.RecipeDto
import at.fhs.cookbook.data.toDto
import at.fhs.cookbook.data.toRecipe
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import org.junit.Assert.assertEquals
import org.junit.Test

// E20: Unit-Tests (JVM, kein Android, Millisekunden). Muster: Arrange → Act → Assert.
// assertEquals(ERWARTET, tatsächlich)! GEGENPROBE nicht vergessen: sabotieren → MUSS rot werden.

class MappingTest {

    @Test
    fun `uebersetzt Serverfelder in unser Modell`() {
        val dto = RecipeDto(id = 1, title = "Suppe",            // Arrange
                            category = "VORSPEISE", cookTimeMin = 30)
        val recipe = toRecipe(dto)                               // Act
        assertEquals(Category.STARTER, recipe.category)          // Assert
        assertEquals(30, recipe.minutes)
    }

    @Test
    fun `unbekannte Kategorie faellt auf DESSERT zurueck`() {    // der GRENZFALL —
        val dto = RecipeDto(id = 1, title = "X", category = "TAPAS")   // das else aus E16
        assertEquals(Category.DESSERT, toRecipe(dto).category)   // war eine Design-
    }                                                            // Entscheidung: festnageln!

    @Test
    fun `toDto und toRecipe sind Umkehrfunktionen`() {           // hübscher Dritter:
        val recipe = Recipe(id = 7, title = "Käsespätzle",       // hin und zurück =
                            category = Category.MAIN, minutes = 25)    // wieder das Original
        assertEquals(recipe, toRecipe(toDto(recipe)))            // (data class == , E3!)
    }
}
