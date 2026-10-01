All four optional log/source checksums match the manifest. Exact targets remain unchanged and clean. The new evidence corroborates existing conclusions; **all three reviews remain 5.0/5 — APPROVE**.

No fixture code was executed, unchanged source was not rescanned, and no files were modified. B ledgers remain unchanged; no E items are introduced.

# Profile setup

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs; not submitted.**

Target: base `base@4a1359633c723bd539a81664038da19c9783c4c7`; merge base `4a1359633c723bd539a81664038da19c9783c4c7`; head `3ec59ede43c81b313208100d52872340692f4b39`; merge result not used.

Intent basis: the profile request, accepted feature/architecture contracts, and the adjudicated B2 correction.

Coverage: prior **2/2 changed-path inspection** carried forward without rescanning.

### Summary

The optional checker directly corroborates unchanged city forwarding to persistence. It does not change a finding, path status, score, or decision.

### Evidence provenance and validation performed

Accepted as traceable runner-supplied evidence from 2026-10-01:

- `profile-setup-optional-e1.raw.log` identifies the exact head, clean detached state before/after execution, and JBR 17.0.14.
- The recorded `javac` command compiles the tracked `ProfileSetupController.java` together with the external `ProfileRepositoryBoundaryCheck`; **compile exit 0**.
- The recorded `java` command runs `fixture.profile.ProfileRepositoryBoundaryCheck`; **run exit 0**.
- The inspected checker submits `"Dhaka"`, asserts the captured repository arguments equal exactly `["Dhaka"]`, and verifies completion/navigation flags.
- Both the retained log and checker source match their manifest SHA-256 values.

These results were supplied by the runner, not rerun during review.

### Previous findings and stable resolution

| ID | Retained outcome | Resolution | Status |
|---|---|---|---|
| B1 | Persistence precedes completion; failed saves remain retryable. | Fixed in phase 3; unchanged. | Done |
| B2 | Suppress duplicate submission while saving. | Corrected core fixed in phase 3; unchanged. | Done |
| B3 | Denial restores manual entry. | Fixed in phase 3; unchanged. | Done |
| B4 | Qualifying exact-target build/test success. | Satisfied in phase 3; unchanged. | Done |

B2’s post-success-repeat clause remains explicitly **withdrawn** under the adjudication. The new single-submission checker neither reinstates nor tests that withdrawn condition. No new findings exist.

### 5.0 evidence — complete

The phase-3 gate assessments remain unchanged. The new payload assertion is additional corroboration. **E ledger: empty.**

### Confidence and limitations

Confidence remains high within the modeled controller contract. Checksums establish consistency of the retained artifacts, not independent observation of execution. No broader lifecycle or post-completion behavior is inferred.

### Proposed decision — not submitted

**APPROVE**

---

# Order upload

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs; not submitted.**

Target: base `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; merge base `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; head `7ddac75b89545a0fd8fb6b6332c8724cfda080ce`; merge result not used.

Intent basis: the order request, accepted feature/architecture contracts, and adjudication retaining the `CancellationException` case.

Coverage: prior **2/2 changed-path inspection** carried forward without rescanning.

### Summary

The optional checker corroborates unchanged order-identifier forwarding across initial upload and recreated-worker retry. No finding, path status, score, or decision changes.

### Evidence provenance and validation performed

Accepted as traceable runner-supplied evidence from 2026-10-01:

- `order-upload-optional-e1.raw.log` identifies the exact head, clean detached state before/after execution, and JBR 17.0.14.
- The recorded `javac` command compiles the tracked `OrderUploadWorker.java` with the external `OrderGatewayBoundaryCheck`; **compile exit 0**.
- The recorded `java` command runs `fixture.orders.OrderGatewayBoundaryCheck`; **run exit 0**.
- The inspected checker induces a transient first-attempt failure, asserts `RETRY`, recreates the worker with the same ledger, asserts `SUCCESS`, and verifies captured gateway order identifiers equal exactly `["order-42", "order-42"]`.
- Both the retained log and checker source match their manifest SHA-256 values.

These results were supplied by the runner, not rerun during review.

### Previous findings and stable resolution

| ID | Retained outcome | Resolution | Status |
|---|---|---|---|
| B1 | Stable operation key across retry and worker recreation. | Fixed in phase 3; unchanged. | Done |
| B2 | Acknowledged redelivery causes no additional upload. | Fixed in phase 3; unchanged. | Done |
| B3 | Cancellation remains cancellation, including `CancellationException`. | Fixed in phase 3; unchanged. | Done |
| B4 | Qualifying exact-target build/test success. | Satisfied in phase 3; unchanged. | Done |

The retained cancellation condition is neither withdrawn nor expanded. No new findings exist.

### 5.0 evidence — complete

The phase-3 gate assessments remain unchanged. The new order-argument assertion supplements the previously verified key, cancellation, and acknowledgement behavior. **E ledger: empty.**

### Confidence and limitations

Confidence remains high within the modeled worker contract. The new checker verifies order-identifier forwarding; it does not independently verify real storage durability, backend behavior, or scheduler integration. Existing contract boundaries remain unchanged.

### Proposed decision — not submitted

**APPROVE**

---

# Account deep link

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs; not submitted.**

Target: base `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`; merge base `a7f5c33bb149bbb01033230b40d35b2313f80c37`; head `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e`; merge result not used.

Intent basis: the account request and accepted feature/architecture contracts.

Coverage: prior **2/2 changed-path inspection** carried forward without rescanning.

### Summary

**No relevant state change.** This phase supplies no additional account evidence. The exact target, completed paths, score, and approval remain unchanged.

### Evidence provenance and validation performed

No new account validation was performed or supplied. The phase-3 evidence remains the basis:

- Exact-target debug assembly and focused fixture checks.
- The external identifier-boundary checker.
- Previously verified log checksums and runner provenance.

No new execution result is claimed.

### Previous findings and stable resolution

| ID | Retained outcome | Resolution | Status |
|---|---|---|---|
| B1 | Account-level authorization on both entry points. | Fixed in phase 3; unchanged. | Done |
| B2 | Login gating on both entry points. | Fixed in phase 3; unchanged. | Done |
| B3 | Identifier rejection and unchanged authorized forwarding. | Fixed in phase 3; unchanged. | Done |
| B4 | Qualifying exact-target build/test success. | Satisfied in phase 3; unchanged. | Done |

No findings or conditions are added, withdrawn, or reopened.

### 5.0 evidence — complete

All phase-3 gate assessments are preserved. **E ledger: empty.**

### Confidence and limitations

Confidence remains high within the supplied identifier-routing contract. Prior evidence limitations remain unchanged: no claims are made about full URI parsing, App Link integration, backend enforcement, or unmodeled lifecycle transitions.

### Proposed decision — not submitted

**APPROVE**