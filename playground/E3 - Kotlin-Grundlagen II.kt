/*
 * FH Salzburg · Native Mobile Applications (Android / Kotlin) · WS 2026/27
 * Einheit 3 — Kotlin-Grundlagen II   ·   Patrick Müller · me@patrickwire.de
 *
 * Läuft ohne Android im Browser:  play.kotlinlang.org
 * Ausgaben mit Kotlin 2.x geprüft (2026-07-25).
 */

enum class Category { STARTER, MAIN, DESSERT }

data class Recipe(
    val title: String,
    val category: Category,
    val minutes: Int,
    val favorite: Boolean = false,   // Default-Wert
)

sealed interface UiState
data object Loading : UiState
data class Data(val items: List<Recipe>) : UiState

fun render(state: UiState): String = when (state) {
    Loading -> "lädt…"
    is Data -> "${state.items.size} Rezepte"   // when deckt alle Fälle ab, kein else nötig
}

fun main() {
    val recipes = listOf(
        Recipe("Salat", Category.STARTER, 10),
        Recipe("Pasta", Category.MAIN, 25, favorite = true),
        Recipe("Eis",   Category.DESSERT, 5),
    )

    // --- Listen & Lambdas ({ it -> ... }, it = aktuelles Element) ---
    println(recipes.filter { it.minutes <= 20 }.map { it.title })  // → [Salat, Eis]
    println(recipes.count { it.favorite })                          // → 1

    // --- when & sealed ---
    println(render(Loading))         // → lädt…
    println(render(Data(recipes)))   // → 3 Rezepte
}
