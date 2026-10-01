Applied `adjudication/pass-conditions.md` as authorized review authority. Profile B2’s post-success-repeat clause is explicitly withdrawn; order B3’s `CancellationException` case remains supported. All fixture-local B IDs are preserved.

No code was executed or files modified. All three reviews are now **4.0/5, validation-limited — COMMENT**. No E path is published below 4.5.

# Profile setup

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs; not submitted.**

Target:

- Base: `base@4a1359633c723bd539a81664038da19c9783c4c7`
- Merge base: `4a1359633c723bd539a81664038da19c9783c4c7`
- Head: `3ec59ede43c81b313208100d52872340692f4b39`
- Merge result: not used.

Intent basis: `requests/profile-setup.md`, accepted `FEATURE.md` / `ARCHITECTURE.md`, and the authorized adjudication.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted**, carried forward from the complete phase-2 inspection. Target identity was verified; unchanged source was not rescanned.

### Summary

All supported source defects are repaired. The remaining gates are execution evidence, so **4.0/5** is validation-limited. **No source change is requested.**

The decision changes from `REQUEST_CHANGES` to `COMMENT` because the adjudication removes the unsupported remainder of B2—not because new execution evidence exists.

### Correction and adjudication continuity

**Reviewer rubric correction:** I withdraw only B2’s clause:

> “Rapid repetition after successful completion does not recommit that setup.”

My prior interpretation exceeded the accepted in-flight boundary. A later invocation after completion does not establish the retained duplicate-submission defect, and assigning `navigated = true` again does not demonstrate another actual navigation.

B2 retains its ID, in-flight root cause, failure retryability, and focused automated evidence requirement. Its corrected condition is:

> “A second submission while the first save is in flight causes no additional save or completion/navigation; the original submission completes/navigates only after its save succeeds.”

### Previous findings

- **Fixed:** None fully closed; required execution evidence remains absent.
- **Still open:**
  - **B1, original P1:** Code repaired; persistence-ordering and failure/retry evidence pending.
  - **B2, original P2:** Supported in-flight code repaired; focused evidence pending.
  - **B3, original P2:** Code repaired; denial/manual-entry evidence pending.
  - **B4:** Exact-target build/test evidence pending.
- **Withdrawn:** Only B2’s post-success-repeat clause. B2 itself remains Open.
- **New:** None.

### What's good

The carried-forward assessment confirms persistence precedes completion, failure releases the submission guard, reentrant in-flight submission is suppressed, and denial clears `locating`.

### No blocking findings

No actionable P0–P2 source defect remains under the corrected contract.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`.
- **NOT RUN:** B2’s focused in-flight check, present in `:app:fixtureExcellenceCheck`.

Execution remains prohibited in this phase. Test source is not passing evidence; the “excellence” task name does not make an existing B requirement optional.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | Original P1; code repaired | Complete only after successful persistence. | During `save`, both success flags remain false. Failure leaves both false and permits another submission; successful retry saves the selected city before setting both true. **Evidence:** passing focused automated checks with observing, failing, and subsequently successful repository doubles. | Open — evidence only |
| B2 | Original P2; corrected core repaired | Suppress in-flight duplicate submission while preserving retryability. | A second submission while the first save is in flight causes no additional save or completion/navigation; the original submission completes/navigates only after its save succeeds. Failure remains retryable. **Evidence:** passing deterministic in-flight repeated-submission checks with a controlled repository; B1’s failure/retry evidence may also cover retryability. | Open — evidence only |
| B3 | Original P2; code repaired | Restore manual entry after denial. | `onLocationDenied()` clears `locating`; manual city submission remains usable and can save successfully. **Evidence:** a passing denial-then-manual-submission automated check. | Open — evidence only |
| B4 | Validation gap | Establish exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant jobs, succeed for the reviewed head or qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, environment/toolchain, underlying results, and exit status. B1–B3 retain their behavioral requirements. | Open — evidence only |

Focused results must identify the tested target, assertions, and outcomes. Credible contributor evidence or authoritative CI can qualify.

### Confidence, limitations, and false-blocker audit

Confidence in the carried-forward static repairs is high. Build and behavioral success remain unverified. No permanent single-use-controller requirement, arbitrary multithreaded guarantee, actual navigation counter, or additional lifecycle scenario survives the correction.

### Proposed decision — not submitted

**COMMENT**

---

# Order upload

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs; not submitted.**

Target:

- Base: `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`
- Merge base: `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`
- Head: `7ddac75b89545a0fd8fb6b6332c8724cfda080ce`
- Previous reviewed head: `c802b55d7510f0cf008e78ddea0e7527508f81e4`
- Merge result: not used.

Intent basis: `requests/order-upload.md`, accepted `FEATURE.md` / `ARCHITECTURE.md`, and the authorized adjudication.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Inspected the complete current `base...HEAD` diff and repair delta. Unchanged integration context was carried forward; the governing request and feature/architecture contracts were rechecked.

### Summary

The new handler catches `CancellationException` before the generic exception branch and returns `CANCELLED`. The added baseline assertion exercises that exact case. Stable identity, acknowledgement suppression, and interruption handling remain intact.

All retained source defects are repaired. **4.0/5** is validation-limited because no qualifying execution evidence exists. **No source change is requested.**

### Adjudication continuity

The adjudication confirms that B3’s `CancellationException` case belongs to the original cancellation-swallowing defect. Its condition and evidence requirement are unchanged.

At the previous head, that exception became `RETRY`. At the current head, the new specific catch returns `CANCELLED`. This is a repair of B3, not withdrawal, reclassification into a new root cause, or a new finding.

### Previous findings

- **Fixed:** None fully closed; required execution evidence remains absent.
- **Still open:**
  - **B1, original P1:** Code repaired; retry/recreation identity evidence pending.
  - **B2, original P2:** Code repaired; acknowledged-redelivery evidence pending.
  - **B3, original P2:** Code now repaired for both interruption and `CancellationException`; execution evidence pending.
  - **B4:** Exact-target build/test evidence pending.
- **Withdrawn:** None.
- **New:** None.

### What's good

Both modeled cancellation cases now produce cancellation outcomes. Acknowledgement still follows successful upload, and acknowledged work returns before reaching the gateway.

### No blocking findings

No actionable P0–P2 source defect remains. B3’s previously retained path is repaired by static inspection.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`, including the new `CancellationException` assertion.
- **NOT RUN:** acknowledged-redelivery checks in `:app:fixtureExcellenceCheck`.

Execution remains prohibited. Added assertions describe intended outcomes but do not establish passing results.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | Original P1; code repaired | Preserve operation identity across retry/recreation. | The order’s key is retained with its work before uploading; after transient failure, retry—including through a recreated worker using the same durable ledger—passes the same key. **Evidence:** a passing automated failure/retry/recreation check recording gateway keys. | Open — evidence only |
| B2 | Original P2; code repaired | Skip upload after acknowledgement. | After successful upload and recorded acknowledgement, subsequent delivery, including through a recreated worker using the same durable ledger, returns success without another upload. **Evidence:** a passing automated success/redelivery/recreation check asserting gateway call count. | Open — evidence only |
| B3 | Original P2; code repaired | Preserve cancellation as cancellation. | Gateway cancellation remains observable as cancellation, rather than `RETRY` or ordinary success; transient transport failure still returns `RETRY`. **Evidence:** passing focused cancellation and transient-failure checks, retaining the `CancellationException` case. | Open — evidence only |
| B4 | Validation gap | Establish exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant jobs, succeed for this head or a qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, environment/toolchain, underlying results, and exit status. B1–B3 retain their behavioral requirements. | Open — evidence only |

Focused results must identify the tested target, assertions, and outcomes. Credible contributor evidence or authoritative CI can qualify.

### Confidence, limitations, and false-blocker audit

Confidence in the new cancellation repair is high: the specific catch precedes the generic retry handler, and its outcome preserves the modeled cancellation signal. No runtime result is inferred.

The stable order identifier remains a sufficient operation key; no separate key-storage mechanism is required. Ledger durability remains an accepted external contract. No new cancellation category, concurrent-worker requirement, crash-window guarantee, storage implementation, or OS process-kill test is added.

### Proposed decision — not submitted

**COMMENT**

---

# Account deep link

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs; not submitted.**

Target:

- Base: `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`
- Merge base: `a7f5c33bb149bbb01033230b40d35b2313f80c37`
- Head: `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e`
- Merge result: not used.

Intent basis: `requests/account-deeplink.md` and accepted `FEATURE.md` / `ARCHITECTURE.md`. The modeled input remains an account identifier supplied by the platform route parser.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted**, carried forward from the complete phase-2 inspection. Target identity was verified; unchanged source was not rescanned.

### Summary

No code, authority, or evidence state changed for this fixture. The prior assessment remains: both entry points share login, canonical-identifier, and account-level authorization checks before opening data.

All source defects are repaired; **4.0/5** remains validation-limited. **No source change is requested.**

### Adjudication continuity

Neither adjudication disposition changes the account review. B1–B4, their supported conditions, and their evidence requirements remain unchanged.

### Previous findings

- **Fixed:** None fully closed; required execution evidence remains absent.
- **Still open:**
  - **B1, original P1:** Code repaired; account-authorization evidence pending.
  - **B2, original P1:** Code repaired; logged-out evidence pending.
  - **B3, original P2:** Code repaired; complete frozen identifier-validation evidence pending.
  - **B4:** Exact-target build/test evidence pending.
- **Withdrawn:** None.
- **New:** None.

### What's good

The carried-forward assessment confirms both handlers use the same guarded operation and pass the same authorized identifier to account opening.

### No blocking findings

No actionable P0–P2 source defect remains. Open items concern verification only.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`.
- **NOT RUN:** allowed-account and malformed-identifier checks in `:app:fixtureExcellenceCheck`.
- The previously inspected test source does not cover the entire frozen B3 set, including empty identifiers through both handlers. Complete qualifying focused evidence remains necessary.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | Original P1; code repaired | Enforce account-level authorization on both paths. | For a logged-in session, both handlers reject an unauthorized canonical account without calling `Accounts.open`; an authorized canonical identifier opens exactly that account. **Evidence:** passing automated allowed/denied-account checks for both entry points, recording opening calls. | Open — evidence only |
| B2 | Original P1; code repaired | Apply login gating equally. | With `loggedIn == false`, both handlers return false and make no opening call for an otherwise valid account identifier. **Evidence:** passing automated logged-out checks for both entry points. | Open — evidence only |
| B3 | Original P2; code repaired | Validate identifiers before opening data. | Both handlers reject null, empty, and noncanonical identifiers without opening data; an authorized canonical identifier is opened unchanged. Validation uses an explicit canonical rule. **Evidence:** passing automated null, empty, noncanonical, and accepted-canonical cases through both handlers. No particular regex is imposed. | Open — evidence only |
| B4 | Validation gap | Establish exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant jobs, succeed for this head or a qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, environment/toolchain, underlying results, and exit status. B1–B3 retain their behavioral requirements. | Open — evidence only |

Focused results must identify the tested target, assertions, and outcomes. Credible contributor evidence or authoritative CI can qualify.

### Confidence, limitations, and false-blocker audit

Confidence in the carried-forward static repairs remains high. Build and behavioral success remain unverified.

The existing false-blocker boundaries are unchanged: no prescribed regex, full URI parsing, App Link verification, logout transition, restored back stack, device matrix, or backend implementation requirement is added. Direct checks of the modeled Java handlers remain sufficient.

### Proposed decision — not submitted

**COMMENT**