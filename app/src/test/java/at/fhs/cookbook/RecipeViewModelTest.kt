package at.fhs.cookbook

import at.fhs.cookbook.data.RecipeRepository
import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

// E20-Stretch: ViewModel-Test mit FAKE hinter der Tür — das wörtliche E16-Versprechen.
// Der Fake ist ehrlicher Code, kein Mocking-Framework.

// ── Kopiervorlage: einmal ins Projekt, für alle ViewModel-Tests ──
// (viewModelScope will den Main-Dispatcher, den es auf der Test-JVM nicht gibt — die Rule tauscht ihn aus.)
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) = Dispatchers.setMain(dispatcher)
    override fun finished(description: Description) = Dispatchers.resetMain()
}

// ── Der Fake: erfüllt dasselbe interface — ohne Netz, ohne Wartezeit ──
class FakeRecipeRepository : RecipeRepository {
    val recipes = mutableListOf(
        Recipe(id = 1, title = "Kürbissuppe",  category = Category.STARTER, minutes = 30),
        Recipe(id = 2, title = "Käsespätzle", category = Category.MAIN,    minutes = 25),
    )
    override suspend fun getRecipes(): List<Recipe> = recipes.toList()
    override suspend fun getRecipe(id: Int): Recipe = recipes.first { it.id == id }
    override suspend fun addRecipe(recipe: Recipe): Recipe {
        val created = recipe.copy(id = (recipes.maxOfOrNull { it.id } ?: 0) + 1)
        recipes += created                              // der Fake „vergibt die id" wie der Server
        return created
    }
    override suspend fun updateRecipe(recipe: Recipe): Recipe = recipe
    override suspend fun setFavorite(id: Int, favorite: Boolean): Recipe =
        getRecipe(id).copy(favorite = favorite)
    override suspend fun deleteRecipe(id: Int) { recipes.removeAll { it.id == id } }
    override suspend fun uploadPhoto(id: Int, file: File): Recipe = getRecipe(id)
}

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `laedt Rezepte beim Start`() = runTest {
        val vm = RecipeViewModel(FakeRecipeRepository())   // dasselbe ViewModel, andere Tür —
        advanceUntilIdle()                                 // alle Nebenjobs zu Ende laufen lassen
        assertEquals(2, vm.uiState.value.recipes.size)     // niemand merkt etwas (E16!)
        assertEquals(false, vm.uiState.value.isLoading)    // beide Ausgänge löschen isLoading!
    }
}
