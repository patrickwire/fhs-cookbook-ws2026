package at.fhs.cookbook.data

import at.fhs.cookbook.model.Recipe

// E16: das Repository — die EINE Tür zu den Daten. UI → ViewModel → Repository → API, niemals quer.

class RecipeRepository(private val api: CookBookApi) {   // bekommt die Tür zum Netz

    suspend fun getRecipes(): List<Recipe> =
        api.listRecipes().map { toRecipe(it) }            // laden + übersetzen — mehr nicht

    suspend fun getRecipe(id: Int): Recipe =              // seit E16 bereit — E19 braucht es
        toRecipe(api.getRecipe(id))

    // E17: Muster jeder Zeile: toDto rein → api → toRecipe raus. Kein try/catch —
    // Fehler fängt das ViewModel, nur der UiState kann sie anzeigen (E12/E16).
    suspend fun addRecipe(recipe: Recipe): Recipe =
        toRecipe(api.createRecipe(toDto(recipe)))

    suspend fun updateRecipe(recipe: Recipe): Recipe =
        toRecipe(api.updateRecipe(recipe.id, toDto(recipe)))

    suspend fun setFavorite(id: Int, favorite: Boolean): Recipe =
        toRecipe(api.setFavorite(id, FavoritePatch(favorite)))

    suspend fun deleteRecipe(id: Int) = api.deleteRecipe(id)
}
