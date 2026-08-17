# CookBook — Kurs-Repo

„Android-Entwicklung mit Kotlin & Jetpack Compose" · FH Salzburg · MMT-B · WS 2026/27 · Patrick Müller

Dieses Repo wächst mit dem Kurs: **ein Commit pro Einheit** (siehe `git log`), dazu Branches
`unit/eNN` (Stand nach Einheit NN) und `start/blockN` (Einstiegsstand für Block N — dein
Sicherheitsnetz: nicht fertig geworden? `git checkout start/blockN` und weiter geht's).

## Setup
1. Android Studio installieren (Win/Linux/macOS — Details im FH-Wiki), Standard-AVD anlegen (Pixel, API 36).
2. Dieses Repo klonen, in Android Studio öffnen, Gradle-Sync abwarten (braucht beim ersten Mal Netz).
3. ▶ Run — die App begrüßt dich.

Versionen sind zentral in `gradle/libs.versions.toml` gepinnt — **bitte keine Versionen ändern**.

> Hinweis (Stand 2026-08-16): Diese Stände sind aus den geprüften Kurs-Codebeispielen
> zusammengesetzt; der Compile-Test des Gesamt-Repos in Android Studio steht noch aus.
