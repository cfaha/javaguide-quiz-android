# Dev Progress

## Completed in this iteration
- Implemented end-to-end quiz flow for single/multi/blank questions.
- Added scoring engine with normalization for blank answers.
- Added PracticeViewModel with score/wrong-book/favorite state management.
- Added practice mode switch (顺序/随机) and category filtering.
- Added session-complete summary page with restart flow.
- Replaced placeholder home UI with functional practice UI.
- Added unit tests for scoring logic.
- Added Room repository mapping for `QuestionEntity/QuestionOptionEntity` <-> domain `Question`.
- Wired `PracticeViewModel` startup load to `PracticeRepository.loadOrSeedQuestions()` and DB-backed favorites/wrong-book restore.
- Updated Compose instrumentation test to cover home entry and start-practice flow (mode/filter controls visibility).
- Added CI pre-build validation for `questions.json` (non-empty, unique ids, valid type/options/answers) to guard Room seed/readback inputs.
- Refactored `PracticeViewModel` for repository injection to support persistence interaction testing.
- Added instrumentation test `PracticeViewModelPersistenceTest` to verify favorites/wrong-book survive ViewModel recreation via Room-backed repository.
- Extended `QuizHomeScreenTest` with instrumentation coverage for answer submit→result→next flow and mode/category interactions.
- Added UI instrumentation assertion for favorite toggle state (`收藏` ↔ `取消收藏`) to reduce interaction blind spots in question flow.
- Added release workflow `.github/workflows/android-release.yml` (tag/workflow_dispatch trigger) with optional keystore-based signing env pipeline and release artifact upload.
- Updated app release signing to support CI-injected env vars (`SIGNING_STORE_FILE` / `SIGNING_STORE_PASSWORD` / `SIGNING_KEY_ALIAS` / `SIGNING_KEY_PASSWORD`).

## Self-check / bug check
- Build chain previously restored (latest CI passed once after fixes).
- Local verification blocker: current host missing Java runtime (`./gradlew` fails with `java: not found`), so unit tests/build not runnable locally.
- Risk check:
  - Blank scoring is strict equivalence after normalization; synonyms not yet supported.
  - Newly added Room mapping path needs CI validation for serialization/migration edge cases.

## Next development plan
1. Harden instrumentation assertions (reduce text-fragile checks) and add edge-case coverage for blank/multi question interactions.
2. Extend CI assertions from seed JSON validation to Room seed/readback consistency checks.
3. Add release automation follow-up: optional GitHub Release publishing and signed artifact verification step.
