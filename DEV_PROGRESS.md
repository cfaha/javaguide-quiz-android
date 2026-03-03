# Dev Progress

## Completed in this iteration
- Implemented end-to-end quiz flow for single/multi/blank questions.
- Added scoring engine with normalization for blank answers.
- Added PracticeViewModel with score/wrong-book/favorite state management.
- Replaced placeholder home UI with functional practice UI.
- Added unit tests for scoring logic.

## Self-check / bug check
- Known fixed issue: invalid Material3 theme parent (already fixed in prior commit).
- Risk check:
  - Last question UX currently disables next button (expected).
  - Blank scoring is strict equivalence after normalization; synonyms not yet supported.
  - Favorites/wrong-book currently in-memory, not persisted to Room yet.

## Next development plan
1. Implement repository layer mapping Room entity <-> domain model and wire ViewModel to Room.
2. Add category filtering and random mode.
3. Add result summary page for whole session.
4. Add instrumentation/UI tests for answer flow.
5. Add release build workflow and signed artifact pipeline.
