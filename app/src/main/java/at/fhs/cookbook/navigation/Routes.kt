package at.fhs.cookbook.navigation

import kotlinx.serialization.Serializable

// E14: Routen — Namen der Szenen, als Klassen statt Text (der Compiler findet Tippfehler).

@Serializable                 // macht die Klasse für Maschinen lesbar (auch für Retrofit, E15!)
object RecipeListRoute        // object = Klasse mit genau EINER Instanz

@Serializable
data class RecipeDetailRoute(val id: Int)   // Route mit Gepäck: nur die id reist
