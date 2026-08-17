package at.fhs.cookbook.data

import at.fhs.cookbook.model.Recipe
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

// E16: das Repository — die EINE Tür zu den Daten. UI → ViewModel → Repository → API, niemals quer.
// E20: die Tür bekommt einen RAHMEN (interface, E15-Vokabel) — rein mechanisches Refactoring;
// im ViewModel ändert sich KEINE Zeile Logik. DAS ist der Beweis, dass die Tür-Architektur echt war.

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getRecipe(id: Int): Recipe
    suspend fun addRecipe(recipe: Recipe): Recipe
    suspend fun updateRecipe(recipe: Recipe): Recipe
    suspend fun setFavorite(id: Int, favorite: Boolean): Recipe
    suspend fun deleteRecipe(id: Int)
    suspend fun uploadPhoto(id: Int, file: File): Recipe
}

class NetworkRecipeRepository(private val api: CookBookApi) : RecipeRepository {   // bekommt die Tür zum Netz

    override suspend fun getRecipes(): List<Recipe> =
        api.listRecipes().map { toRecipe(it) }            // laden + übersetzen — mehr nicht

    override suspend fun getRecipe(id: Int): Recipe =
        toRecipe(api.getRecipe(id))

    // E17: Muster jeder Zeile: toDto rein → api → toRecipe raus. Kein try/catch —
    // Fehler fängt das ViewModel, nur der UiState kann sie anzeigen (E12/E16).
    override suspend fun addRecipe(recipe: Recipe): Recipe =
        toRecipe(api.createRecipe(toDto(recipe)))

    override suspend fun updateRecipe(recipe: Recipe): Recipe =
        toRecipe(api.updateRecipe(recipe.id, toDto(recipe)))

    override suspend fun setFavorite(id: Int, favorite: Boolean): Recipe =
        toRecipe(api.setFavorite(id, FavoritePatch(favorite)))

    override suspend fun deleteRecipe(id: Int) = api.deleteRecipe(id)

    // E19: hochladen — und danach die Server-Wahrheit FRISCH holen.
    // getRecipe(id) lag seit E16 bereit: „Block 5 braucht es" — heute ist es so weit.
    override suspend fun uploadPhoto(id: Int, file: File): Recipe {
        val part = MultipartBody.Part.createFormData(          // Kopiervorlage —
            "photo", file.name,                                // verstehen beim Lesen reicht
            file.asRequestBody("image/jpeg".toMediaType()),
        )
        api.uploadImage(id, part)
        return getRecipe(id)                                   // frisches imageUrl vom Server —
    }                                                          // URLs NIE selbst zusammenbauen!
}
