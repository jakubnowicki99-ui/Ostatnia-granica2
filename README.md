# Ostatnia Granica — projekt Android

Pierwszy natywny szkielet gry pod Androida, przygotowany jako baza pod właściwą produkcję.

## Założenia
- Kotlin + Jetpack Compose
- Android min SDK 26, target/compile SDK 36
- package/applicationId: `pl.ostatniagranica.game`
- responsywny interfejs: telefon + szeroki ekran/Fold
- orientacja pozostawiona systemowi
- wersjonowanie: 0.1.0 / versionCode 1

## Uruchomienie
1. Otwórz folder w Android Studio.
2. Pozwól Android Studio zsynchronizować Gradle i pobrać zależności.
3. Uruchom konfigurację `app` na telefonie/emulatorze.

## Kierunek dalszej produkcji
Ten projekt jest działającym szkieletem natywnej aplikacji, a nie ukończoną grą. Kolejne moduły powinny obejmować: trwały zapis stanu, mapę świata 2.5D, system budowy i produkcji, mieszkańców/pracowników, zwiad, terytorium, drogi, NPC/fakcje, bohaterów, oddziały, walkę, misje, ekonomię, audio, grafiki i przygotowanie AAB do Google Play.
