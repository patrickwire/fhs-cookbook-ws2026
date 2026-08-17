package at.fhs.cookbook.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import at.fhs.cookbook.model.Recipe
import java.io.File

// E19: der Foto-Knopf im Detail-Screen — zwei Launcher, sauber verkettet.
// Knopf → Erlaubnis-Launcher → (granted) → Foto-Launcher → (success) → ViewModel.
// Launcher entstehen auf COMPOSABLE-Ebene (wie remember) — nie im onClick!

@Composable
fun TakePhotoButton(
    recipe: Recipe,
    onPhotoTaken: (File) -> Unit,                   // meldet nach oben → viewModel.attachPhoto
    onDenied: () -> Unit,                           // z. B. Snackbar (E18): freundlich ohne Kamera
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current              // Zugang der Composable zur Android-Welt

    // Ziel-Datei in UNSEREM (privaten) Cache — Durchgangsware auf dem Weg zum Server
    val photoFile = remember { File(context.cacheDir, "recipe_photo.jpg") }

    // Die Datei als ZUGRIFFS-TICKET für die fremde Kamera-App (Sandbox!):
    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",  // muss zur authority im Manifest passen
            photoFile,
        )
    }

    // Launcher 2 (zuerst definiert — sein Callback wird unten gebraucht): das Foto
    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->                                  // true = die Kamera hat in photoUri geschrieben
        if (success) onPhotoTaken(photoFile)
    }

    // Launcher 1: die Erlaubnis — bei Erfolg zündet er Launcher 2.
    // Schon gewährt? Dann KEIN Dialog: der Callback kommt sofort mit true.
    // 2× abgelehnt? Dann auch kein Dialog: sofort false — Ablehnung ist ein
    // ZUSTAND, kein Fehler; der Weg zurück führt nur über die Einstellungen.
    val requestCamera = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) takePicture.launch(photoUri) else onDenied()
    }

    Button(
        onClick = { requestCamera.launch(Manifest.permission.CAMERA) },
        modifier = modifier,
    ) {
        Text("📷 Foto aufnehmen")
    }
}
