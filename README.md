# JavaGuide Quiz Android

An Android MVP quiz app based on JavaGuide interview content.

## Stack
- Kotlin + Jetpack Compose
- Room
- MVVM
- GitHub Actions APK build

## Build APK in CI
Push to `main` and check Actions:
- Workflow: `Android CI APK`
- Artifact: `app-debug-apk`

## Local Build
```bash
./gradlew assembleDebug
```

## Files
- `PLAN.md` project plan
- `question.schema.json` question data schema
