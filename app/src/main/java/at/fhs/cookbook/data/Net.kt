package at.fhs.cookbook.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// E15: Retrofit-Aufbau — Kopiervorlage. Einmal einrichten, nie wieder anfassen.

const val BASE_URL = "https://www.patrickwire.de/fh/"   // muss mit "/" enden!
const val MY_KEY = "max-mustermann"                     // ← eigenen Schlüssel wählen — IMMER denselben

// ---------- API-Interface: Aufruf beschreiben — Retrofit schreibt den Netzwerk-Code ----------

interface CookBookApi {                     // interface = nur Versprechen, keine Umsetzung
    @GET("cookbook.php")
    suspend fun scrandle(@Query("r") r: String = "scrandle"): ScrandlePair
    // listRecipes / getRecipe kommen in E16 dazu
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
