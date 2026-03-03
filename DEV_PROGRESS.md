# Dev Progress

## Completed in this iteration
- Implemented end-to-end quiz flow for single/multi/blank questions.
- Added scoring engine with normalization for blank answers.
- Added PracticeViewModel with score/wrong-book/favorite state management.
- Added practice mode switch (顺序/随机) and category filtering.
- Added session-complete summary page with restart flow.
- Replaced placeholder home UI with functional practice UI.
- Added unit tests for scoring logic.

## Self-check / bug check
- Build chain previously restored (latest CI passed once after fixes).
- Risk check:
  - Blank scoring is strict equivalence after normalization; synonyms not yet supported.
  - Favorites/wrong-book currently in-memory, Room schema is ready but repository wiring pending.

## Next development plan
1. Implement repository layer mapping Room entity <-> domain model and wire ViewModel to Room.
2. Persist wrong-book and favorites to Room (load on app start, save on change).
3. Add instrumentation/UI tests for answer flow and filter/mode switching.
4. Add release build workflow and signed artifact pipeline.
