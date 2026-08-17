package at.fhs.cookbook.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// E15: Retrofit-Aufbau — Kopiervorlage. Einmal einrichten, nie wieder anfassen.

const val BASE_URL = "https://www.patrickwire.de/fh/"   // muss mit "/" enden!
const val MY_KEY = "max-mustermann"                     // ← eigenen Schlüssel wählen — IMMER denselben

// ---------- API-Interface: Aufruf beschreiben — Retrofit schreibt den Netzwerk-Code ----------

interface CookBookApi {                     // interface = nur Versprechen, keine Umsetzung
    @GET("cookbook.php")
    suspend fun scrandle(@Query("r") r: String = "scrandle"): ScrandlePair

    @GET("cookbook.php")                      // NEU in E16
    suspend fun listRecipes(@Query("r") r: String = "recipes"): List<RecipeDto>

    @GET("cookbook.php")                      // NEU in E16 — Block 5 braucht es
    suspend fun getRecipe(@Query("id") id: Int, @Query("r") r: String = "recipes"): RecipeDto

    @POST("cookbook.php")                     // E17 · Anlegen: Antwort 201 + Rezept MIT id
    suspend fun createRecipe(@Body recipe: RecipeDto,
                             @Query("r") r: String = "recipes"): RecipeDto

    @PUT("cookbook.php")                      // E17 · Ersetzen: der Body muss VOLLSTÄNDIG sein
    suspend fun updateRecipe(@Query("id") id: Int, @Body recipe: RecipeDto,
                             @Query("r") r: String = "recipes"): RecipeDto

    @PATCH("cookbook.php")                    // E17 · Teil-Ändern: nur die mitgeschickten Felder
    suspend fun setFavorite(@Query("id") id: Int, @Body patch: FavoritePatch,
                            @Query("r") r: String = "recipes"): RecipeDto

    @DELETE("cookbook.php")                   // E17 · Antwort 204: kein Body → kein Rückgabetyp!
    suspend fun deleteRecipe(@Query("id") id: Int, @Query("r") r: String = "recipes")
}

// ---------- Aufbau ----------

val cookBookClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->              // Stempel: hängt an JEDE Anfrage deinen Schlüssel
        chain.proceed(chain.request().newBuilder().addHeader("X-Api-Key", MY_KEY).build())
    }
    .build()

val cookBookJson = Json { ignoreUnknownKeys = true }   // unbekannte JSON-Felder still überlesen

val api: CookBookApi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(cookBookClient)
    .addConverterFactory(cookBookJson.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(CookBookApi::class.java)        // erzeugt die Umsetzung unseres interface
