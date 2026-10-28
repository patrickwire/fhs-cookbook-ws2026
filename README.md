# CookBook · Stand nach E8

Begleitprojekt zur Lehrveranstaltung an der FH Salzburg, WS 2026/27.
Dieser Stand entspricht der vereinfachten Oberfläche am Ende von Einheit 8.

## Diesen Kursstand herunterladen

```sh
git clone --branch unterricht/e08 --single-branch https://github.com/patrickwire/fhs-cookbook-ws2026.git CookBook-E8
```

Alternativ oben den Branch **unterricht/e08** wählen und über **Code → Download ZIP** herunterladen.
Die bisherigen Kurs-Branches bleiben als ältere Stände erhalten.

## Öffnen und starten

1. Diesen Ordner in Android Studio über **Open** öffnen.
2. Den Gradle-Abgleich abschließen lassen. Beim ersten Mal ist Internet nötig.
3. Ein Android-Gerät oder einen Emulator ab API 24 auswählen und **Run** drücken.

Das Projekt verwendet die Werkzeugversionen des in der Vorlesung verwendeten
CookBook-Projekts. Benötigt werden Android SDK 37, die passenden Build Tools und
JDK 25 (wie im Vorlesungsprojekt festgelegt). In Android Studio lässt sich dafür
die mitgelieferte JDK wählen.
Fehlende SDK-Komponenten bietet Android Studio beim Abgleich zur Installation an.
Der Gradle-Wrapper liegt vollständig bei, einschließlich Windows-Startdatei.

## Was auf dem Bildschirm steht

Unter der Überschrift **CookBook** stehen drei feste Rezeptkarten. Jede Karte
enthält einen Bildplatzhalter, Titel, Kategorie und Kochzeit sowie ein leeres Herz.
Das Herz ist in diesem Lernstand eine Anzeige. Beim Antippen verändert sich nichts.

## Den Code lesen

| Datei | Aufgabe | Bezug |
|---|---|---|
| `MainActivity.kt` | Startet Compose und ruft den Bildschirm im mitgelieferten Theme auf | Projektgrundlage / E7 |
| `CookBookScreen.kt` | Ordnet Überschrift und drei Karten mit `Column` und `spacedBy` an | E7/E8 |
| `RecipeItem.kt` | Baut eine wiederverwendbare Karte aus `Card`, `Row`, `Column`, `Box`, `Text` und `Icon` | E7/E8 |
| `ui/theme/` | Enthält das Theme aus dem Android-Studio-Projekt | Projektgrundlage |

Beginne mit `RecipeItemPreview`: Dort siehst du eine Karte allein.
`RecipeItemGrosseSchriftPreview` zeigt einen langen Titel bei größerer Schrift.
`CookBookScreenPreview` zeigt den gesamten Bildschirm.

Die Karte erhält zwei Texte:

```kotlin
RecipeItem("Kürbissuppe", "Hauptgang · 25 Min")
```

`weight(1f)` gibt dem Textbereich den übrigen Platz und hält das Herz rechts.
`padding` erzeugt Platz innerhalb der Karte. `spacedBy(8.dp)` regelt die Abstände
zwischen den Karten. Die Kartenhöhe wächst mit dem Text.
Der Bildplatzhalter belegt 56 dp und verwendet eine Farbrolle des Themes.

`safeDrawingPadding()` am Bildschirm hält den Inhalt von Status- und
Navigationsleiste fern. Diese eine Zeile gehört zur Bildschirmgrundlage.

## Bewusst einfacher Lernstand

In der Vorlesung wurden vorgezogene Konzepte entfernt. Dieser Stand führt das fort:

- Die eigenen UI-Funktionen verwenden keinen State und kein `remember`.
- Die Rezeptkarte erhält Texte, keine Funktionen als Parameter.
- Es gibt keine Klickbehandlung, keine Deaktivierungsparameter und kein Umschalten des Herzens.
- Die drei Karten stehen als direkte Aufrufe im Code. Eine datengetriebene `LazyColumn` folgt in E10.
- Das eigene Theme aus dem Theme Builder und `Scaffold` folgen in E11.

Die Kotlin-Experimente aus E2–E4 und die einzelnen UI-Demos aus E7 gehören in das
separate Playground-Projekt. CookBook enthält hier die fertige Rezeptkarten-Oberfläche.

## Selbst ausprobieren

1. Ändere Titel und Untertitel einer Karte.
2. Ergänze eine vierte Karte durch einen weiteren `RecipeItem`-Aufruf.
3. Entferne kurz `weight(1f)` und vergleiche die Position des Herzens.
4. Ändere `spacedBy(8.dp)` auf `16.dp` und prüfe den Unterschied zu `padding`.
5. Öffne die Vorschau mit großer Schrift: Der Titel soll umbrechen und das Herz sichtbar bleiben.

## Bauen

macOS / Linux:

```sh
./gradlew :app:assembleDebug :app:lintDebug
```

Windows:

```bat
gradlew.bat :app:assembleDebug :app:lintDebug
```

Die Debug-APK liegt anschließend unter `app/build/outputs/apk/debug/app-debug.apk`.
Lokale SDK-Pfade, Android-Studio-Einstellungen und Build-Dateien sind von Git ausgeschlossen.

## Nächster Kursschritt

E9 ergänzt am selben Projekt Zustand und Interaktion. E8 bleibt als klarer
Ausgangspunkt erhalten, damit man die Änderungen vergleichen kann.
