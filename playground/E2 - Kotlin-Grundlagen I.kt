/*
 * FH Salzburg · Native Mobile Applications (Android / Kotlin) · WS 2026/27
 * Einheit 2 — Kotlin-Grundlagen I   ·   Patrick Müller · me@patrickwire.de
 *
 * Läuft ohne Android im Browser:  play.kotlinlang.org
 * Oder lokal:  kotlinc "E2 - Kotlin-Grundlagen I.kt" -include-runtime -d e2.jar && java -jar e2.jar
 * Ausgaben mit Kotlin 2.x geprüft (2026-07-25).
 */

fun greet(name: String): String = "Hallo, $name!"

fun main() {
    // --- val vs. var ---
    val name = "CookBook"   // unveränderlich – bevorzugen!
    var count = 0           // veränderlich
    count = count + 1
    val portions = 4
    println("$name, count=$count, portions=$portions")   // → CookBook, count=1, portions=4

    // --- Null-Safety ---
    val note: String? = null      // ?  = darf null sein
    val len = note?.length ?: 0   // ?. nur wenn nicht null, ?: Ersatzwert
    println(len)                  // → 0

    // --- Funktionen & String-Templates ---
    println(greet("Kurs"))                 // → Hallo, Kurs!
    println("Portionen: ${portions * 2}")  // $x = Variable, ${...} = Ausdruck  → Portionen: 8
}
