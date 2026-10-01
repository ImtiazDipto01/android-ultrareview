All seven raw-log SHA-256 values match the checksum manifest. The supplied evidence qualifies for the unchanged reviewed targets; no builds, tests, or application code were executed during this review, and no source was rescanned.

**All three fixtures reach 5.0/5 — APPROVE.** The evidence closes their locked B paths and covers every applicable proportional evidence gate. Neither the historical task name nor the evidence document’s “merge-readiness” label determines the score. No concrete additional gap warrants an E item.

# Profile setup

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; not submitted.**

Target: base `base@4a1359633c723bd539a81664038da19c9783c4c7`; merge base `4a1359633c723bd539a81664038da19c9783c4c7`; head `3ec59ede43c81b313208100d52872340692f4b39`; merge result not used.

Intent basis: the profile request, accepted feature/architecture contracts, and authorized adjudication.

Coverage: **2/2 changed paths inspected; no omissions**, carried forward. Target identity was verified without rescanning unchanged source.

### Summary

Qualifying execution evidence now verifies the previously inspected repairs. The 4.5 baseline is complete, and all applicable evidence gates are covered; the policy therefore requires **5.0/5**.

### Evidence provenance and validation performed

Accepted as **traceable runner-supplied evidence**, not reviewer-executed validation:

- Runner date: 2026-10-01; macOS 27.0 arm64.
- Recorded environment: Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, Android SDK 36.
- The evidence manifest binds both runs to this exact head and records clean detached state before and after execution.
- **PASS:** `:app:assembleDebug :app:fixtureBaselineCheck`, with `--rerun-tasks`; reported exit 0, raw log shows success and 35 executed tasks.
- **PASS:** `:app:fixtureExcellenceCheck`, with `--rerun-tasks`; reported exit 0, raw log shows success and two executed tasks.

Verified logs: `profile-setup-baseline.raw.log` and `profile-setup-excellence.raw.log`.

The executed assertions cover persistence-before-completion, failure leaving setup incomplete, successful retry, denial clearing locating state, and one save under in-flight reentrant submission.

### Previous findings and resolution

| ID | Resolution | Code/evidence result | Path status |
|---|---|---|---|
| B1 | Fixed | Previously inspected persistence ordering and retry repair now has passing focused execution evidence. | Done |
| B2 | Fixed, corrected core | The executed reentrant case produces one save and successful outer completion. | Done |
| B3 | Fixed | Executed denial followed by manual submission verifies recovery and successful saving. | Done |
| B4 | Fixed | Exact-head debug assembly and baseline checks succeeded with qualifying provenance. | Done |

**Withdrawn clause preserved:** B2 does not require suppression of post-success invocation. The adjudicated in-flight condition remains unchanged. No findings or conditions are added.

### What's good

The same focused checks exercise both failure recovery and the ordering invariant, while the separate reentrant case verifies the retained duplicate-submission risk.

### No blocking findings

All supported B conditions are satisfied.

### 5.0 evidence — complete

The bundled policy enumerates six proportional gates:

| Gate | Assessment |
|---|---|
| Product proof | **Covered:** executed checks exercise the material setup outcomes. |
| Regression protection | **Covered:** focused automated assertions cover every retained defect. |
| Failure and recovery | **Covered:** failed save, retry, and optional-permission denial are exercised. |
| Lifecycle, process, and concurrency | **Covered within scope:** in-flight reentrant submission and completion ordering are exercised. Unmodeled lifecycle transitions are not required. |
| Critical persistence and integration | **Covered:** checks observe the repository boundary before completion, failure recovery, and successful retry; exact-target debug assembly passes. |
| Triggered platform quality | **Covered within scope:** the modeled denial/manual-entry transition has direct automated evidence. No actual permission UI or additional device behavior is changed by this fixture. |

No E item is needed.

### Confidence, limitations, and false-blocker audit

Confidence is high for the modeled contract. The raw Gradle logs are bound to the SHA and environment by the runner manifest; their provenance was not independently witnessed. Published distribution/APK hashes could not be recomputed because those binaries are absent, but the retained logs were verified.

No post-completion suppression, device matrix, new persistence implementation, or additional lifecycle requirement is introduced.

### Proposed decision — not submitted

**APPROVE**

---

# Order upload

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; not submitted.**

Target: base `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; merge base `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; head `7ddac75b89545a0fd8fb6b6332c8724cfda080ce`; merge result not used.

Intent basis: the order request, accepted feature/architecture contracts, and adjudication retaining the `CancellationException` case.

Coverage: **2/2 changed paths inspected; no omissions**, carried forward from the corrected review. Target identity was verified without rescanning unchanged source.

### Summary

The evidence verifies stable retry identity, both retained cancellation cases, and acknowledgement-based suppression after worker recreation. All B conditions and applicable proportional gates are complete, supporting **5.0/5**.

### Evidence provenance and validation performed

Accepted as **traceable runner-supplied evidence**, not reviewer-executed validation:

- Runner date: 2026-10-01; macOS 27.0 arm64.
- Recorded environment: Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, Android SDK 36.
- The manifest identifies this exact head and clean detached state before and after execution.
- **PASS:** `:app:assembleDebug :app:fixtureBaselineCheck`, with `--rerun-tasks`; reported exit 0, raw log shows success and 35 executed tasks.
- **PASS:** `:app:fixtureExcellenceCheck`, with `--rerun-tasks`; reported exit 0, raw log shows success and two executed tasks.

Verified logs: `order-upload-baseline-v2.raw.log` and `order-upload-excellence-v2.raw.log`.

The baseline assertions exercise transient failure, recreation with the same operation key, successful retry, interrupted cancellation, and explicit `CancellationException`. The additional check verifies one upload across successful delivery and acknowledged redelivery through a recreated worker.

### Previous findings and resolution

| ID | Resolution | Code/evidence result | Path status |
|---|---|---|---|
| B1 | Fixed | Executed failure/retry/recreation checks record matching gateway keys. | Done |
| B2 | Fixed | Executed acknowledged redelivery returns success while upload count remains one. | Done |
| B3 | Fixed | Executed interruption and `CancellationException` cases return `CANCELLED`; transient failure remains `RETRY`. | Done |
| B4 | Fixed | Exact-head debug assembly and baseline checks succeeded with qualifying provenance. | Done |

The adjudicated `CancellationException` condition remains intact and is now verified. No withdrawal, new finding, or path alteration occurs.

### What's good

The evidence tests worker recreation directly at the accepted ledger boundary and distinguishes cancellation from retryable transport failure.

### No blocking findings

All supported B conditions are satisfied.

### 5.0 evidence — complete

| Gate | Assessment |
|---|---|
| Product proof | **Covered:** executed assertions verify retry identity, cancellation, success, and acknowledged redelivery. |
| Regression protection | **Covered:** focused automated checks cover each retained defect. |
| Failure and recovery | **Covered:** transient failure/retry and both retained cancellation cases are exercised. |
| Lifecycle, process, and concurrency | **Covered within scope:** worker recreation and repeated delivery are exercised using the accepted ledger boundary. |
| Critical persistence and integration | **Covered:** stable work identity and acknowledgement suppression are verified; exact-target debug assembly passes. Ledger durability remains an accepted external contract. |
| Triggered platform quality | **Covered within scope:** the modeled worker cancellation/retry results are exercised. No additional scheduler or Android-component integration is introduced by this fixture. |

No E item is needed.

### Confidence, limitations, and false-blocker audit

Confidence is high for the modeled worker contract. Evidence is traceable runner output rather than an independent rerun. Raw-log hashes match; unavailable distribution/APK binaries were not independently checked.

The in-memory test ledger exercises the worker’s use of the contractually durable app-provided ledger; it does not claim to test a storage implementation. No disk implementation, process-kill test, concurrent-worker arbitration, or crash-window guarantee is added.

### Proposed decision — not submitted

**APPROVE**

---

# Account deep link

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; not submitted.**

Target: base `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`; merge base `a7f5c33bb149bbb01033230b40d35b2313f80c37`; head `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e`; merge result not used.

Intent basis: the account request and accepted feature/architecture contracts. Input is an account identifier supplied by the platform parser.

Coverage: **2/2 changed paths inspected; no omissions**, carried forward. Target identity was verified without rescanning unchanged source.

### Summary

The supplied runs verify authorization, login gating, identifier rejection, and unchanged forwarding of authorized identifiers. The external checker supplies the previously missing empty-identifier evidence. All B conditions and applicable gates are complete, supporting **5.0/5**.

### Evidence provenance and validation performed

Accepted as **traceable runner-supplied evidence**, not reviewer-executed validation:

- Runner date: 2026-10-01; macOS 27.0 arm64.
- Gradle environment: Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, Android SDK 36.
- **PASS:** `:app:assembleDebug :app:fixtureBaselineCheck`, with `--rerun-tasks`; reported exit 0, raw log shows success and 35 executed tasks.
- **PASS:** `:app:fixtureExcellenceCheck`, with `--rerun-tasks`; reported exit 0, raw log shows success and two executed tasks.
- **PASS:** external `AccountIdentifierBoundaryCheck`, compiled with the exact tracked router using JBR 17.0.14. Its log identifies this SHA, clean before/after state, compile exit 0, run exit 0, and the successful boundary assertions.

Verified logs: `account-deeplink-baseline.raw.log`, `account-deeplink-excellence.raw.log`, and `account-deeplink-empty-blank-focused.raw.log`.

I read the external checker. It calls both handlers with empty and whitespace-only identifiers, asserts rejection, and confirms zero authorization and account-opening calls.

### Previous findings and resolution

| ID | Resolution | Code/evidence result | Path status |
|---|---|---|---|
| B1 | Fixed | Executed denied-account cases cover both entry points; allowed cases verify exact authorized identifiers reach account opening. | Done |
| B2 | Fixed | Executed logged-out cases return false through both handlers without opening data. | Done |
| B3 | Fixed | Combined focused checks cover null, empty, noncanonical, and accepted identifiers across both handlers, including the external empty/blank checks. | Done |
| B4 | Fixed | Exact-head debug assembly and baseline checks succeeded with qualifying provenance. | Done |

No adjudicated condition is changed.

For B3, the evidence is equivalent coverage of the frozen behavior: both entry points exercise the previously verified shared validator; null and malformed cases exercise its rejection branches, empty/blank cases exercise both public handlers, and accepted cases verify unchanged forwarding through both. This does not require adding a redundant Cartesian product of every input class and wrapper.

### What's good

The evidence checks effects at the authorization and opening boundaries, rather than relying only on return values. The source-external checker adds coverage without modifying the reviewed target.

### No blocking findings

All supported B conditions are satisfied.

### 5.0 evidence — complete

| Gate | Assessment |
|---|---|
| Product proof | **Covered:** executed checks verify login, resource authorization, rejection, and exact identifier forwarding. |
| Regression protection | **Covered:** repository checks plus the retained executable boundary checker protect the material outcomes. |
| Failure and recovery | **Covered:** logged-out, unauthorized, null, malformed, empty, and blank inputs are exercised. |
| Lifecycle, process, and concurrency | **N/A beyond exercised entry points:** routing is synchronous with fixed session state; both cold and warm handlers are tested. No lifecycle restoration or concurrent state transition is modeled. |
| Critical persistence and integration | **Covered:** authorization and opening receive the expected identifiers, disallowed inputs cause no opening, and exact-target debug assembly passes. |
| Triggered platform quality | **Covered within scope:** the changed account authorization/input boundary has direct automated evidence. Platform URI parsing and App Link integration are outside the modeled interface. |

No E item is needed.

### Confidence, limitations, and false-blocker audit

Confidence is high for the bounded router contract. The external log explicitly records target, commands, runtime, and exit statuses; the Gradle runs obtain their target binding from the runner manifest. These are accepted traceable results, not independently rerun checks.

No new regex, URI parser, logout transition, backend implementation, device matrix, or optional framework is required. The evidence does not claim those unmodeled behaviors were tested.

### Proposed decision — not submitted

**APPROVE**