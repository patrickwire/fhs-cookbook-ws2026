package at.fhs.cookbook.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

// E21: das Herz reist statt zu springen — animate*AsState.
// Deklarativ wie immer (E7): wir beschreiben nur das ZIEL; die Reise
// (Zwischenwerte, Timing, Umlenken bei Doppel-Tipp) übernimmt Compose.

@Composable
fun FavoriteButton(
    favorite: Boolean,
    onFavoriteClick: () -> Unit,             // Event nach oben — UDF unverändert
    modifier: Modifier = Modifier,
) {
    val heartScale by animateFloatAsState(
        targetValue = if (favorite) 1.3f else 1f,
        animationSpec = spring(               // Federphysik: Dinge haben Masse
            dampingRatio = Spring.DampingRatioMediumBouncy,
        ),
        label = "heartScale",                 // fürs Tooling (Animation Preview)
    )
    val heartTint by animateColorAsState(
        targetValue = if (favorite) MaterialTheme.colorScheme.primary
                      else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "heartTint",
    )
    IconButton(onClick = onFavoriteClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = if (favorite) "Aus Favoriten entfernen"
                                 else "Zu Favoriten hinzufügen",   // E7-Disziplin —
            tint = heartTint,                                      // zahlt in E20-Tests ein!
            modifier = Modifier.scale(heartScale),
        )
    }
}
