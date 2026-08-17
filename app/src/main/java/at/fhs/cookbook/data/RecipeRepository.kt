package at.fhs.cookbook.data

import at.fhs.cookbook.model.Recipe

// E16: das Repository — die EINE Tür zu den Daten. UI → ViewModel → Repository → API, niemals quer.

class RecipeRepository(private val api: CookBookApi) {   // bekommt die Tür zum Netz

    suspend fun getRecipes(): List<Recipe> =
        api.listRecipes().map { toRecipe(it) }            // laden + übersetzen — mehr nicht

    suspend fun getRecipe(id: Int): Recipe =              // heute ungenutzt — Block 5 braucht es
        toRecipe(api.getRecipe(id))
}
