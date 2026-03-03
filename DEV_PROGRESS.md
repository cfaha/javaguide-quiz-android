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

## Self-check / bug check
- Build chain previously restored (latest CI passed once after fixes).
- Local verification blocker: current host missing Java runtime (`./gradlew` fails with `java: not found`), so unit tests/build not runnable locally.
- Risk check:
  - Blank scoring is strict equivalence after normalization; synonyms not yet supported.
  - Newly added Room mapping path needs CI validation for serialization/migration edge cases.

## Next development plan
1. Harden instrumentation assertions (reduce text-fragile checks) and add edge-case coverage for blank/multi question interactions.
2. Add release build workflow and signed artifact pipeline.
3. Extend CI assertions from seed JSON validation to Room seed/readback consistency checks.
