package at.fhs.cookbook.model

// Das Recipe aus E2/E3 — neu ist nur die id (stabiler Key für die Liste).

enum class Category { STARTER, MAIN, DESSERT }

data class Recipe(
    val id: Int,                        // stabil, eindeutig, für immer — der Key der Liste
    val title: String,
    val category: Category = Category.MAIN,
    val minutes: Int = 0,
    val favorite: Boolean = false,
    val ingredients: List<String> = emptyList(),   // NEU in E14 — Default bricht nichts
    val steps: List<String> = emptyList(),         // NEU in E14
    val imageUrl: String? = null,                  // NEU in E16 — kommt vom Server
)

// startRecipes ist in Rente — die Rezepte kommen seit E16 vom Server.
