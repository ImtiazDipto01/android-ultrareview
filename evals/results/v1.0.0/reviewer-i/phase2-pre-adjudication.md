All three reviews use the isolated policy and phase-1 ledger. I inspected each complete `base...HEAD` diff, the repair delta, and relevant tracked context. No code was executed, no network resources were accessed, and no files or repository state were modified.

Each fixture retains its own B1–B4. No items are added, renumbered, split, merged, or dropped.

# Profile setup

## Android UltraReview — confidence 4.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not assessed; not submitted.**

Target: base `base@4a1359633c723bd539a81664038da19c9783c4c7`; merge base `4a1359633c723bd539a81664038da19c9783c4c7`; head `3ec59ede43c81b313208100d52872340692f4b39`, matching checked-out `HEAD`; merge result not used.

Intent basis: `requests/profile-setup.md` and unchanged, accepted `FEATURE.md` / `ARCHITECTURE.md`.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Both Java files, the complete repair delta, and all tracked feature, architecture, manifest, and build context were inspected.

### Summary

Persistence ordering, failed-save recovery, permission denial, and in-flight reentrant submission are repaired. **B2 remains partially repaired:** immediately submitting again after a successful return invokes another save.

The score is **4.0/5** because one bounded P2 remains and qualifying execution evidence is absent. Confidence in the static traces is high; no passing build or behavioral result is claimed.

### Previous findings

- **Fixed:** None fully closed; required execution evidence is absent.
- **Still open:**
  - **B1 — original P1:** Code repaired; regression evidence pending. Success flags follow `save`, and `finally` releases the submission guard after failure.
  - **B2 — P2:** Partial code repair. The `saving` guard rejects reentrant submission during persistence, but permits immediate repetition after successful completion.
  - **B3 — original P2:** Code repaired; regression evidence pending. Denial clears `locating`.
  - **B4:** Exact-target build/test evidence pending.
- **Withdrawn:** None.
- **New:** None.

### What's good

- The `finally` block preserves retry after a failed save.
- Added checks describe ordering, recovery, denial, and reentrant submission outcomes.

### Blocking themes

- B2’s retained rapid-repeat condition remains unsatisfied after successful completion.
- B1–B4 lack qualifying execution evidence.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`; prohibited in this phase.
- **NOT RUN:** focused B2 execution. The existing `:app:fixtureExcellenceCheck` contains the in-flight reentrant case, but no post-success repeat assertion.
- The added test source is inspection evidence only.

### Path to 4.5/5

| ID | Source | Required outcome | Frozen objective pass condition and expected evidence | Status |
|---|---|---|---|---|
| B1 | Original P1; code repaired | Complete only after successful persistence. | During `save`, both success flags remain false. Failure leaves both false and permits another submission; successful retry saves the selected city before setting both true. **Evidence:** passing focused automated checks with observing, failing, and subsequently successful repository doubles. | Open — evidence only |
| B2 | P2; partial repair | Prevent duplicate persistence/completion for one setup submission. | A second submission while the first save is in flight does not invoke another save; rapid repetition after successful completion does not recommit that setup. The accepted successful submission produces one completion/navigation transition. **Evidence:** passing deterministic repeated-submission checks with a controlled repository. | Open — code and evidence |
| B3 | Original P2; code repaired | Restore manual entry after denial. | `onLocationDenied()` clears `locating`; manual city submission remains usable and can save successfully. **Evidence:** a passing denial-then-manual-submission automated check. | Open — evidence only |
| B4 | Validation gap | Establish exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant jobs, succeed for this head or a qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, environment/toolchain, underlying results, and exit status. B1–B3 retain their behavioral requirements. | Open — evidence only |

Focused evidence must identify the reviewed target and its executed assertions/results. Credible contributor evidence or authoritative CI can qualify.

### False-blocker audit

The post-success clause in B2 remains supported by the controlling request: **“Rapid repeated submission must not commit or navigate twice.”** `FEATURE.md` explicitly requires in-flight suppression but does not exempt immediate repetition after success. On the same controller, two consecutive successful `submit("Dhaka")` calls perform two saves: `saving` becomes false after the first call, and neither success flag guards the second.

I therefore retain this clause rather than withdraw it merely because the repair tests only reentrant submission. No arbitrary multithreaded execution requirement is added. The concrete consequence is duplicate persistence; the boolean `navigated` does not establish two real navigation events.

B1’s ordering/failure/retry and B3’s denial/manual-entry clauses remain supported. No process-death, location-success, or additional lifecycle condition is introduced.

### Proposed decision — not submitted

**REQUEST_CHANGES**

### Draft inline findings

1. **[P2][B2] Guard repeated setup submissions** — `app/src/main/java/fixture/profile/ProfileSetupController.java:19 (RIGHT)`  
   Evidence basis: `Static trace`. The guard handles submission during a save, but `finally` clears it after success. An immediate second successful submission therefore calls `repository.save` again. Preserve the in-flight guard and prevent recommitting the already-completed setup; verify the retained post-success repeat condition alongside the existing reentrant case. This is the original B2, not a new finding.

---

# Order upload

## Android UltraReview — confidence 4.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not assessed; not submitted.**

Target: base `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; merge base `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; head `c802b55d7510f0cf008e78ddea0e7527508f81e4`, matching checked-out `HEAD`; merge result not used.

Intent basis: `requests/order-upload.md` and unchanged, accepted `FEATURE.md` / `ARCHITECTURE.md`, including the app-provided durable ledger contract.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Both Java files, the complete repair delta, and all tracked feature, architecture, manifest, and build context were inspected.

### Summary

Stable retry identity and acknowledgement suppression are repaired. **B3 remains partially repaired:** interruption returns `CANCELLED`, but the previously identified `CancellationException` path still returns `RETRY`.

The score is **4.0/5** because one bounded P2 remains and qualifying execution evidence is absent. Confidence in the static traces is high; backend and scheduler runtime behavior remain unverified.

### Previous findings

- **Fixed:** None fully closed; required execution evidence is absent.
- **Still open:**
  - **B1 — original P1:** Code repaired; evidence pending. Using the order identity as the key preserves it across retries and worker recreation without separately generated state.
  - **B2 — original P2:** Code repaired; evidence pending. Acknowledged orders return success before reaching the gateway.
  - **B3 — P2:** Partial code repair. `InterruptedException` preserves cancellation and restores the interrupt flag; `CancellationException` still reaches the generic retry handler.
  - **B4:** Exact-target build/test evidence pending.
- **Withdrawn:** None.
- **New:** None.

### What's good

- Reusing the supplied order identity avoids recreating a random operation key.
- The durable ledger boundary supports acknowledgement across worker recreation.
- Added checks describe retry identity, interrupted cancellation, and acknowledged redelivery.

### Blocking themes

- B3 still converts the originally identified cancellation exception into ordinary retry.
- B1–B4 lack qualifying execution evidence.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`; prohibited in this phase.
- **NOT RUN:** acknowledged-redelivery checks in `:app:fixtureExcellenceCheck`.
- **NOT RUN:** B3’s retained `CancellationException` regression case; the current cancellation check supplies only `InterruptedException`.

The task name “excellence” does not make B2’s previously required evidence optional.

### Path to 4.5/5

| ID | Source | Required outcome | Frozen objective pass condition and expected evidence | Status |
|---|---|---|---|---|
| B1 | Original P1; code repaired | Preserve operation identity across retry/recreation. | The order’s key is retained with its work before uploading; after transient failure, retry—including through a recreated worker using the same durable ledger—passes the same key. **Evidence:** a passing automated failure/retry/recreation check recording gateway keys. | Open — evidence only |
| B2 | Original P2; code repaired | Skip upload after acknowledgement. | After successful upload and recorded acknowledgement, subsequent delivery, including through a recreated worker using the same durable ledger, returns success without another upload. **Evidence:** a passing automated success/redelivery/recreation check asserting gateway call count. | Open — evidence only |
| B3 | P2; partial repair | Preserve cancellation as cancellation. | Gateway cancellation remains observable as cancellation, rather than `RETRY` or ordinary success; transient transport failure still returns `RETRY`. **Evidence:** passing focused cancellation and transient-failure checks, including the previously identified `CancellationException` path. | Open — code and evidence |
| B4 | Validation gap | Establish exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant jobs, succeed for this head or a qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, environment/toolchain, underlying results, and exit status. B1–B3 retain their behavioral requirements. | Open — evidence only |

Focused evidence must identify the reviewed target and its executed assertions/results. Credible contributor evidence or authoritative CI can qualify.

### False-blocker audit

The unchanged request and accepted architecture require cancellation to remain cancellation, without restricting it to interruption. `Gateway.upload` still permits `Exception`; it has no contract excluding `CancellationException`. That exception bypasses the new `InterruptedException` branch and reaches `catch (Exception)`. Adding an interruption-only test does not narrow the controlling contract or disprove the original trigger.

B3 therefore remains supported rather than being withdrawn or extended to a new failure category.

For B1, the stable work identifier itself is a sufficient key; no separate UUID, key field, or ledger key-storage API is required. For B2, the app-provided ledger’s durability remains an accepted boundary. No concrete disk implementation, OS process-kill test, concurrent-worker arbitration, crash-window guarantee, or permanent-error taxonomy is added.

### Proposed decision — not submitted

**REQUEST_CHANGES**

### Draft inline findings

1. **[P2][B3] Preserve cancellation through the exception handler** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:32–33 (RIGHT)`  
   Evidence basis: `Static trace`. A gateway `CancellationException` still enters the generic handler and returns `RETRY`; the new branch handles only `InterruptedException`. Preserve this cancellation signal before retry classification and verify it separately from transient failure. This is the original B3 trigger, not a new finding.

---

# Account deep link

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not assessed; not submitted.**

Target: base `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`; merge base `a7f5c33bb149bbb01033230b40d35b2313f80c37`; head `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e`, matching checked-out `HEAD`; merge result not used.

Intent basis: `requests/account-deeplink.md` and unchanged, accepted `FEATURE.md` / `ARCHITECTURE.md`. The interface receives an account identifier already supplied by the platform route parser.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Both Java files, the complete repair delta, and all tracked feature, architecture, manifest, and build context were inspected.

### Summary

Both entry points now share login, canonical-identifier, and account-level authorization checks before opening data. The retained source defects are repaired.

**4.0/5 is validation-limited:** required execution evidence is the only remaining gate. No source change is requested. Confidence in the static repair assessment is high; build and behavioral success remain unverified.

### Previous findings

- **Fixed:** None fully closed; required execution evidence is absent.
- **Still open:**
  - **B1 — original P1:** Code repaired; allowed/denied-account execution evidence pending.
  - **B2 — original P1:** Code repaired; logged-out execution evidence pending.
  - **B3 — original P2:** Code repaired; complete frozen identifier-validation evidence pending.
  - **B4:** Exact-target build/test evidence pending.
- **Withdrawn:** None.
- **New:** None.

### What's good

- Both public handlers delegate to the same guarded operation.
- Authorization and opening receive the same identifier.
- Invalid input and logged-out sessions are rejected before account opening.

### No blocking findings

No actionable P0–P2 source defect remains. The open B items represent missing verification, not unrepaired security findings.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`; prohibited in this phase.
- **NOT RUN:** allowed-account and malformed-identifier checks in `:app:fixtureExcellenceCheck`.
- The existing source checks denied and logged-out behavior through both handlers. Its malformed-input cases cover a noncanonical cold-start identifier and a null warm-start identifier, but do not supply the entire frozen B3 case set, including empty identifiers.

Passing existing task logs alone must be evaluated against those actual assertions; task names cannot substitute for coverage.

### Path to 4.5/5

| ID | Source | Required outcome | Frozen objective pass condition and expected evidence | Status |
|---|---|---|---|---|
| B1 | Original P1; code repaired | Enforce account-level authorization on both paths. | For a logged-in session, both handlers reject an unauthorized canonical account without calling `Accounts.open`; an authorized canonical identifier opens exactly that account. **Evidence:** passing automated allowed/denied-account checks for both entry points, recording opening calls. | Open — evidence only |
| B2 | Original P1; code repaired | Apply login gating equally. | With `loggedIn == false`, both handlers return false and make no opening call for an otherwise valid account identifier. **Evidence:** passing automated logged-out checks for both entry points. | Open — evidence only |
| B3 | Original P2; code repaired | Validate identifiers before opening data. | Both handlers reject null, empty, and noncanonical identifiers without opening data; an authorized canonical identifier is opened unchanged. Validation uses an explicit canonical rule. **Evidence:** passing automated null, empty, noncanonical, and accepted-canonical cases through both handlers. No particular regex is imposed. | Open — evidence only |
| B4 | Validation gap | Establish exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant jobs, succeed for this head or a qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, environment/toolchain, underlying results, and exit status. B1–B3 retain their behavioral requirements. | Open — evidence only |

Focused evidence must identify the reviewed target and its executed assertions/results. Credible contributor evidence or authoritative CI can qualify. Direct Java-handler checks remain sufficient for the modeled behavior.

### False-blocker audit

Both handlers reach `openAuthorized`, which rejects logged-out sessions, null values, and identifiers outside `acct-[0-9]+`, then checks authorization before opening the unchanged identifier. These guards defeat all three original source-level causal traces.

The packet specifies canonical validation but no exhaustive grammar. The implementation supplies an explicit rule consistent with the existing accepted example; no alternative regex or numeric-range constraint is imposed. The frozen null/empty/noncanonical checks remain representable at the supplied identifier interface.

No URI parsing, scheme/host validation, App Link verification, logout transition, restored back stack, device matrix, or backend implementation is added. No prior clause requires withdrawal on the available evidence.

### Proposed decision — not submitted

**COMMENT**

No optional E path is published: the current score remains below 4.5.