package at.fhs.cookbook.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// E21: Auftritt und Abgang mit Ansage — AnimatedVisibility.
// Unterschied zum harten if: die Composable bleibt im Baum, bis die Exit-Animation fertig ist.

@Composable
fun AnimatedErrorBanner(error: String?, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = error != null,
        enter = slideInVertically() + fadeIn(),   // Übergänge sind mit + kombinierbar
        exit = slideOutVertically() + fadeOut(),
        modifier = modifier,
    ) {
        Text(
            text = error ?: "",                    // ?: "" — der Smart-Cast-Trick (E16)
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(16.dp),
        )
    }
}
