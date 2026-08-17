package at.fhs.cookbook.data

import kotlinx.serialization.Serializable

// E15: die JSON-Felder als data class — Namen müssen exakt stimmen.

@Serializable
data class Dish(val id: Int, val name: String, val imageUrl: String? = null, val rating: Double)

@Serializable
data class ScrandlePair(val left: Dish, val right: Dish)
