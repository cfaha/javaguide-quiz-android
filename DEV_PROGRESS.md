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
1. Persist practice state, wrong-book, and favorites to Room.
2. Implement repository layer mapping Room entity <-> domain model.
3. Add category filtering and random mode.
4. Add result summary page for whole session.
5. Add instrumentation/UI tests for answer flow.
