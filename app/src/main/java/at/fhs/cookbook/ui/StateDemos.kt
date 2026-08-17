package at.fhs.cookbook.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue          // die zwei Importe,
import androidx.compose.runtime.setValue          // die `by` braucht (Alt+Enter)
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// E9-Demos aus dem Live-Coding. (Werden in E12 als „toter Code" wieder gelöscht.)

var brokenCount = 0   // ändert sich wirklich — aber niemand sagt Compose Bescheid

@Composable
fun BrokenCounter() {
    Button(onClick = { brokenCount = brokenCount + 1 }) {
        Text("Geklickt: $brokenCount")   // → bleibt sichtbar bei 0
    }
}

@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }   // beobachtbar + überlebt Recomposition
    Button(onClick = { count++ }) {               // ++ heißt: um 1 erhöhen
        Text("Geklickt: $count")                  // → zählt sichtbar hoch
    }
}
