# Worked review example

Use this synthetic example only to calibrate structure, severity, stable IDs, and convergence. Repository facts in a real review control the outcome.

## Scenario and working matrix

The PR description is the complete stated intent; there is no external ticket:

- persist the profile before marking setup complete;
- on save failure, remain in setup and allow retry; and
- after location denial, keep manual city entry usable.

The exact head has no qualifying Gradle build/test evidence.

| Requirement | Evidence | Status |
|---|---|---|
| Persist before completion | Static coroutine trace shows completion and navigation occur before the suspend save returns | Missing |
| Save failure remains recoverable | Static trace shows setup has already left the back stack | Missing |
| Denial restores manual entry | Denial branch leaves `isLocating` true while the form is disabled | Missing |

## Complete draft review

```markdown
## Android UltraReview — confidence 3.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact refs were reviewed, but live currentness could not be refreshed; not submitted.**

Target: base `main@abc123`, merge base `abc123`, head `def456`; merge result not used.

Intent basis: PR-authored description only; no independent product source was accessible.

Coverage: 7/7 changed paths classified; 6 behavior-relevant paths inspected; 1 generated, binary, or metadata path assessed; no material omissions.

### Summary

This snapshot does not yet preserve the profile-setup contract. A failed save can leave setup marked complete without a stored profile, and location denial leaves manual entry disabled. The PR description is specific enough to establish stated intent; the lack of a separate ticket does not lower confidence by itself. Validation gap: `NOT RUN` — no qualifying exact-target build or test was executed or directly observed; `B3` names the evidence needed.

### What's good

- Profile persistence is isolated behind the repository's existing abstraction.
- The location dependency and coroutine dispatcher are injected, so denial and ordering can be tested deterministically.

### Blocking themes

- Setup completion currently occurs before required persistence succeeds.
- Permission denial leaves the only manual recovery path disabled.

### Validation performed

No qualifying check was executed or directly observed. The required exact-target build/test event remains the summary-only `B3` validation gap.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition | Status |
|---|---|---|---|---|
| B1 | P1 inline finding | Commit completion only after persistence | A failing save keeps the completion marker false and setup visible; focused coroutine regression evidence passes | Open |
| B2 | P2 inline finding | Restore manual entry after denial | Denial clears locating state and leaves manual entry usable; focused regression evidence passes | Open |
| B3 | validation gap | Validate the exact target | The repository-documented affected-variant build and relevant tests pass on this head or its qualifying current merge result | Open |

### Proposed decision — not submitted

**REQUEST CHANGES**

### Draft inline findings

1. **[P1][B1] Commit completion only after the profile save succeeds** — `feature/setup/src/main/kotlin/example/ProfileSetupViewModel.kt:88 (RIGHT)`

   Evidence basis: `Static trace`.

   If persistence fails or the process ends first, this path has already stored the completion marker and emitted navigation. On relaunch, the user bypasses setup without the required profile. Await the repository write and commit completion/navigation only on success; retain the draft and expose retry on failure. Add a failing-repository coroutine test that keeps the marker false and emits no completion effect.

2. **[P2][B2] Restore manual entry when location permission is denied** — `feature/setup/src/main/kotlin/example/ProfileSetupViewModel.kt:141 (RIGHT)`

   Evidence basis: `Static trace`.

   The denial branch never clears `isLocating`, while that state disables the form. A user who denies permission cannot continue manually without relaunching. End locating state on every terminal path and add a denial-path test proving manual entry remains usable.
```

## Expected convergence

1. After both repairs and focused tests, mark `B1` and `B2` `Done`. If the full exact-target affected-variant build/tests are still missing, keep only `B3` `Open`, use `4.0/5 (validation-limited)`, request no source change, and recommend `COMMENT`.
2. When qualifying exact-target build/tests arrive, mark `B3` `Done`. The baseline becomes `4.5/5` and the recommendation becomes `APPROVE`.
3. If lifecycle/concurrency evidence is the only genuine 5.0 gap, publish one optional item such as `E1`: prove a cancelled pending save cannot later emit completion after the user leaves or retries.
4. When that exact pass condition succeeds on the current target, mark `E1` `Done`, add no replacement items for unchanged scope, and award `5.0/5` with `APPROVE`.

## Complete clean 4.5 to 5.0 re-review

After the code repairs and exact-target baseline validation arrive, the clean 4.5 review can look like this:

```markdown
## Android UltraReview — confidence 4.5/5

**Review type: AI-assisted.**

Target: base `main@abc123`, merge base `abc123`, head `ghi789`; merge result not used.

Intent basis: PR-authored description only; no independent product source was accessible.

Coverage: 9/9 changed paths classified; 8 behavior-relevant paths inspected; 1 generated, binary, or metadata path assessed; no material omissions.

### Summary

The setup contract is now met. Persistence succeeds before completion is committed, failure remains recoverable, and permission denial restores manual entry. Exact-target affected-variant build and focused tests pass, so the PR is merge-ready. The one remaining item is optional evidence for unusually complete lifecycle/concurrency confidence.

### Previous findings

- Fixed: `B1`, `B2`, and validation item `B3` are `Done`.
- Still open: None.
- No longer applies: None.
- Withdrawn: None.
- New: None.

### What's good

- Completion now has one persistence-backed commit point.
- The focused failure and permission-denial tests assert user-observable recovery outcomes.

### No blocking findings

No P0–P2 finding remains.

### Validation performed

- `PASS` — `./gradlew :feature:setup:testDebugUnitTest`, affected debug unit tests, head `ghi789`; provenance: reviewer-executed; completed successfully.
- `PASS` — `./gradlew :app:assembleDebug`, application debug variant, head `ghi789`; provenance: reviewer-executed; completed successfully.

### Path to 5.0/5 — optional, non-blocking

The PR is approved; this item is optional and does not block merge.

| ID | Gate and current gap | Minimum sufficient evidence | Objective pass condition | Status |
|---|---|---|---|---|
| E1 | Lifecycle/concurrency proof: no direct evidence covers a pending save completing after setup leaves | A deterministic focused test on the exact target, or equivalent direct runtime evidence | With the save suspended, leaving setup and then completing it cannot set completion, navigate, or mutate retired setup state | Open |

### Proposed decision — not submitted

**APPROVE**
```

When the contributor adds only the evidence locked in `E1`, the next re-review can close without a new code sweep or replacement requirement. The test-only SHA change is evidential, not a material scope expansion, so the published `E1` remains locked:

```markdown
## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

Target: base `main@abc123`, merge base `abc123`, head `jkl012`; merge result not used.

Intent basis: PR-authored description only; no independent product source was accessible.

Coverage: 10/10 changed paths classified; 9 behavior-relevant paths inspected; 1 generated, binary, or metadata path assessed; no material omissions. The delta from `ghi789` is the focused cancellation test plus its fixture, and the complete current diff remains covered.

### Summary

The 4.5 baseline still holds, and the previously locked lifecycle/concurrency evidence item now passes on the exact target. The test-only delta adds evidence without materially expanding scope, so `E1` remains the complete locked path. Every applicable proportional gate is covered, so the mandatory result is 5.0/5.

### Previous findings

- Fixed: `B1`, `B2`, and `B3` remain `Done`.
- Still open: None.
- No longer applies: None.
- Withdrawn: None.
- New: None.
- Optional evidence: `E1` is `Done`.

### What's good

- The new deterministic test proves late completion cannot escape the setup lifetime.
- The repair and evidence stay within the existing architecture and authorized scope.

### No blocking findings

No P0–P2 finding remains.

### Validation performed

- `PASS` — `./gradlew :feature:setup:testDebugUnitTest`, including the locked `E1` cancellation case, head `jkl012`; provenance: reviewer-executed; completed successfully.
- `PASS` — `./gradlew :app:assembleDebug`, application debug variant, head `jkl012`; provenance: reviewer-executed; completed successfully.

### 5.0 evidence — complete

- `E1` — `Done`: after setup leaves, late save success and failure produce no completion, navigation, or retired-state mutation.
- Product proof, regression protection, failure/recovery, and lifecycle/concurrency gates are `Covered`; persistence/integration is covered by the focused tests and exact-target build.
- Triggered permission recovery is `Covered` by the denial-path test; UI layout, external-component, sensitive-data, and release-only subchecks are `N/A` for this scope.

### Proposed decision — not submitted

**APPROVE**
```

## P2 versus P3 calibration boundaries

| Changed situation | Classification | Why |
|---|---|---|
| Denying an optional location permission leaves the required manual city form permanently disabled | `P2` | A supported recovery path is concretely unusable until relaunch. |
| The same working denial screen could use smoother animation or friendlier copy, with no accepted copy contract | `P3 / non-blocking` | This is polish, not a demonstrated functional or accessibility failure. |
| A deterministic test shows an older search response overwriting the newer visible query | `P2` | The trigger and wrong user-visible result are both demonstrated, even if the affected flow is bounded. |
| A reviewer can imagine overlapping searches, but the traced owner serializes every entry and no bypass exists | `No finding` | A theoretical race without a reachable trigger is not review debt to assign. |
| At a project-supported large font scale, the changed confirmation action is clipped and cannot be reached | `P2` | The changed UI blocks task completion in a supported accessibility state. |
| Spacing differs from a preferred value but content, focus, touch target, and task completion remain correct and no enforced token applies | `P3 / non-blocking` | Aesthetic or token preference alone does not materially regress users. |
| A changed optional share flow crashes only in the supported minified release variant because reflected metadata is removed | `P2` | A concrete supported release path fails; raise severity only if its actual blast radius warrants it. |
| The reviewer prefers generated adapters or narrower keep rules, but the exact minified path passes and no size/security contract is violated | `P3 / non-blocking` | Modernization or theoretical optimization is not a merge blocker. |
