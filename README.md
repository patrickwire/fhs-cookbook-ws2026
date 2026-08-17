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

## Landkarte

| Branch | Stand |
|---|---|
| `main` | kompletter Kursverlauf, ein Commit je Einheit (E1–E21) |
| `unit/e01` … `unit/e21` | Endstand der jeweiligen Einheit (E5/E6 und E22–E24 haben keinen Code) |
| `start/block1` … `start/block6` | Einstiegsstand für Block N — das Sicherheitsnetz |
| `bug/e20` | präparierter Bug für die Debugger-Jagd in E20 (Symptom: Filter zeigt die falschen Rezepte) |

`playground/` enthält die Kotlin-Snippets aus E2/E3 (play.kotlinlang.org) — sie gehören nicht zum App-Build.

Vor E15: in `app/src/main/java/at/fhs/cookbook/data/Net.kt` den eigenen `MY_KEY` setzen — und dann nie wieder wechseln.

> Hinweis (Stand 2026-08-16): Diese Stände sind aus den geprüften Kurs-Codebeispielen
> zusammengesetzt; der Compile-Test des Gesamt-Repos in Android Studio steht noch aus
> (E9–E12-Kern getestet am 2026-07-29; Vermerk entfernen, sobald alle Stände gebaut sind).
