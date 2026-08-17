package at.fhs.cookbook.model

// Das Recipe aus E2/E3 — neu ist nur die id (stabiler Key für die Liste).

enum class Category { STARTER, MAIN, DESSERT }

data class Recipe(
    val id: Int,                        // stabil, eindeutig, für immer — der Key der Liste
    val title: String,
    val category: Category = Category.MAIN,
    val minutes: Int = 0,
    val favorite: Boolean = false,
)

// Beispieldaten fürs Live-Coding — gehen in E16 in Rente, wenn der Server liefert.
val startRecipes = listOf(
    Recipe(1, "Kürbissuppe", Category.STARTER, 25, favorite = true),
    Recipe(2, "Salat", Category.STARTER, 10),
    Recipe(3, "Curry", Category.MAIN, 40),
)
