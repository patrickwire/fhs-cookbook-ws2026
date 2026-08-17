package at.fhs.cookbook.data

import at.fhs.cookbook.model.Recipe
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

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

    // E19: hochladen — und danach die Server-Wahrheit FRISCH holen.
    // getRecipe(id) lag seit E16 bereit: „Block 5 braucht es" — heute ist es so weit.
    suspend fun uploadPhoto(id: Int, file: File): Recipe {
        val part = MultipartBody.Part.createFormData(          // Kopiervorlage —
            "photo", file.name,                                // verstehen beim Lesen reicht
            file.asRequestBody("image/jpeg".toMediaType()),
        )
        api.uploadImage(id, part)
        return getRecipe(id)                                   // frisches imageUrl vom Server —
    }                                                          // URLs NIE selbst zusammenbauen!
}
