package at.fhs.cookbook.data

import at.fhs.cookbook.model.Category
import at.fhs.cookbook.model.Recipe
import kotlinx.serialization.Serializable

// E16: DTO — die Form, in der der SERVER redet (Feldnamen = JSON-Vertrag).

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val category: String,                     // "HAUPTGANG" — als Text, nicht als enum!
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
