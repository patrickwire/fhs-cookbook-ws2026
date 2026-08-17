package at.fhs.cookbook.data

import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import kotlinx.serialization.Serializable

// E16: DTO — die Form, in der der SERVER redet (Feldnamen = JSON-Vertrag).

@Serializable
data class RecipeDto(
    val id: Int = 0,
    val title: String,
    val category: String = "HAUPTGANG",       // als Text, nicht als enum!
    val cookTimeMin: Int = 0,                 // der Server nennt es anders als wir
    val favorite: Boolean = false,
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val imageUrl: String? = null,
)

// Mapping: der Übersetzer — Server-Form rein, unsere Form raus.
fun toRecipe(dto: RecipeDto) = Recipe(
    id = dto.id,
    title = dto.title,
    category = when (dto.category) {          // Text → enum (when aus E3)
        "VORSPEISE" -> Category.STARTER
        "HAUPTGANG" -> Category.MAIN
        else -> Category.DESSERT              // Auffangnetz: lieber falsch einsortiert als Absturz
    },
    minutes = dto.cookTimeMin,                // andere Namen? Genau dafür ist Mapping da
    favorite = dto.favorite,
    ingredients = dto.ingredients,
    steps = dto.steps,
    imageUrl = dto.imageUrl,
)

@Serializable
data class FavoritePatch(val favorite: Boolean)   // PATCH: NUR das Feld, das sich ändert

// E17: die Gegenrichtung des Mappings — unsere Form rein, Server-Form raus.
fun toDto(recipe: Recipe) = RecipeDto(
    id = recipe.id,
    title = recipe.title,
    category = when (recipe.category) {     // enum → Text: KEIN else nötig —
        Category.STARTER -> "VORSPEISE"     // das when über ein enum ist vollständig,
        Category.MAIN    -> "HAUPTGANG"     // der Compiler prüft alle Fälle (E3)
        Category.DESSERT -> "DESSERT"
    },
    cookTimeMin = recipe.minutes,
    favorite = recipe.favorite,
    ingredients = recipe.ingredients,
    steps = recipe.steps,
    imageUrl = recipe.imageUrl,
)
