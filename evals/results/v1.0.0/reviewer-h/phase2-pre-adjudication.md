# Profile setup

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not checked; not submitted.**

Target: `fixtures/profile-setup`  
Base: `base@4a1359633c723bd539a81664038da19c9783c4c7`  
Merge base: `4a1359633c723bd539a81664038da19c9783c4c7`  
Head: `3ec59ede43c81b313208100d52872340692f4b39`  
Previous head: `876b6408dab4eb3b4b320b397ac1ba6d3d8ebe6f`  
Merge result: not used.

Intent basis: `requests/profile-setup.md` and the unchanged accepted `FEATURE.md` and `ARCHITECTURE.md`.

Coverage: **2/2 changed paths inspected; 0 assessed; 0 omitted.** Inspected the complete `base...HEAD` diff, repair delta, both Java files, and relevant tracked feature, architecture, build, and manifest context.

### Summary

The supported code defects are repaired: persistence precedes completion, failure releases the submission guard, an in-flight reentrant submission is ignored, and location denial restores manual entry.

One earlier condition exceeded the modeled scope and is explicitly corrected below. After that correction, only qualifying execution evidence remains missing. The policy therefore requires **4.0/5, validation-limited**, rather than carrying forward a code-defect severity cap. **No source change is requested.**

Confidence in the static repair assessment is high. Compilation and actual execution remain unverified.

### Previous findings

- **Fixed:** None formally; the required execution evidence has not been supplied.
- **Still open:**
  - **B1, originally P1:** Code repaired. `submit()` saves before setting completion/navigation and clears `saving` in `finally`. Focused execution evidence remains missing.
  - **B2, originally P2:** Supported code repair is complete. The `saving` guard rejects reentrant submission during the active save. Focused execution evidence remains missing.
  - **B3, originally P2:** Code repaired. `onLocationDenied()` clears `locating`. Focused execution evidence remains missing.
  - **B4:** Exact-target compilation and baseline execution evidence remain missing.
- **Withdrawn — separable clause only:** B2’s requirement that “repeating submission after successful completion does not save again,” and its associated after-success execution requirement.
- **New:** None.

### What's good

The baseline check now exercises ordering, failure followed by retry, and denial recovery. The separate `excellence()` check exercises reentrant submission. These are inspected test definitions, not passing results.

### No blocking findings

No supported actionable code defect remains. All four path items remain open for evidence.

### Validation performed

None. Builds, Gradle, tests, fixture entry points, and application code were **NOT RUN**, as instructed. No qualifying external evidence was supplied.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and required evidence | Status |
|---|---|---|---|---|
| B1 | Prior P1; evidence remaining | Complete only after successful persistence; preserve retry after failure. | During the active setup save, `complete` and `navigated` remain false. A thrown save failure leaves both false; a subsequent successful submission persists the selected city before setting them true. An executed focused regression check must assert this ordering and failure-then-retry sequence on the reviewed target. | **Open — code repaired; execution missing** |
| B2 | Prior P2; corrected scope; evidence remaining | Prevent duplicate submission effects during the active setup operation. | A second submission while the first save is in progress does not invoke another save. That operation produces one successful save and one completion/navigation transition. An executed deterministic check must exercise an overlapping or reentrant call. The after-completion repeat clause is withdrawn. | **Open — code repaired; execution missing** |
| B3 | Prior P2; evidence remaining | Restore manual entry after location denial. | Calling `onLocationDenied()` clears `locating`; a subsequent manual city submission can reach the repository and complete after a successful save. An executed focused check must cover that sequence. | **Open — code repaired; execution missing** |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources. Evidence must identify this reviewed SHA, commands/tasks, environment, underlying results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. B1–B3 retain their focused assertions. | **Open — evidence missing** |

At this head, baseline execution is the available evidence route for B1/B3; `:app:fixtureExcellenceCheck` supplies the existing reentrant scenario for B2. Equivalent focused evidence remains acceptable. The task’s name does not make the locked B2 condition optional.

### False-blocker audit

**Reviewer rubric correction:** The request’s rapid-submission requirement is concretely specified by `FEATURE.md` as “Ignore a second submission while one is in flight.” My earlier unconditional after-completion prohibition extended that contract into reuse of an already-completed controller. That clause and its evidence requirement are withdrawn; B2’s root cause, ID, original supported severity, and in-flight regression requirement remain.

Correspondingly, B1’s “during save” assertion is scoped to active setup and failure/retry. Any reading that requires resetting completion for later reuse of an already-completed controller is withdrawn as the same overbroad lifecycle extension.

The current reentrant guard satisfies the modeled synchronous boundary. No arbitrary multithreaded submission, controller recreation, actual navigation-event counter, or process-death requirement is added. Reopening the source and checks revealed no remaining supported defect.

### Proposed decision — not submitted

**COMMENT** — execution evidence is the sole remaining gate. No optional `E#` path is published below 4.5.

---

# Order upload

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not checked; not submitted.**

Target: `fixtures/order-upload`  
Base: `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Merge base: `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Head: `c802b55d7510f0cf008e78ddea0e7527508f81e4`  
Previous head: `92efc1ba93b352870851adedac9a362197b6f683`  
Merge result: not used.

Intent basis: `requests/order-upload.md` and the unchanged accepted `FEATURE.md` and `ARCHITECTURE.md`, including the supplied ledger’s durability guarantee.

Coverage: **2/2 changed paths inspected; 0 assessed; 0 omitted.** Inspected the complete `base...HEAD` diff, repair delta, both Java files, and relevant tracked feature, architecture, build, and manifest context.

### Summary

The modeled code defects are repaired. The order identifier supplies a stable retry key, successful uploads are acknowledged through the ledger, acknowledged deliveries bypass the gateway, and the represented interruption signal returns `CANCELLED` while restoring the interrupt flag.

Required execution evidence is absent. This makes the review **4.0/5, validation-limited**. **No source change is requested.**

Confidence in the static repair assessment is high. Gateway, scheduler, and durable-store execution have not been observed; the supplied ledger contract remains the authority for durability.

### Previous findings

- **Fixed:** None formally; required execution evidence is absent.
- **Still open:**
  - **B1, originally P1:** Code repaired. The same `orderId` supplies the key across attempts and worker recreation. Focused execution evidence remains missing.
  - **B2, originally P1:** Code repaired. The worker checks acknowledgement before uploading and records it after success. Focused execution evidence remains missing.
  - **B3, originally P2:** Code repaired for the modeled `InterruptedException` cancellation signal. Focused execution evidence remains missing.
  - **B4:** Exact-target compilation and baseline execution evidence remain missing.
- **Withdrawn:** None.
- **New:** None.

### What's good

The baseline check explicitly compares keys across recreated workers and distinguishes transient failure from interruption. The separate `excellence()` check exercises acknowledged redelivery through another worker sharing the ledger.

### No blocking findings

No supported actionable code defect remains. The prior findings remain open solely because their required evidence has not passed.

### Validation performed

None. Builds, Gradle, tests, fixture entry points, and application code were **NOT RUN**, as instructed. No qualifying external evidence was supplied.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and required evidence | Status |
|---|---|---|---|---|
| B1 | Prior P1; evidence remaining | Reuse the order’s operation key across retry and worker recreation. | For one pending order, an attempt followed by transient failure and retry uses the same key, including when retry uses a newly created worker backed by the same supplied durable ledger. An executed focused check must capture and compare gateway keys across those attempts. | **Open — code repaired; execution missing** |
| B2 | Prior P1; evidence remaining | Suppress uploads after recorded acknowledgement. | After a successful upload records acknowledgement, repeated delivery of that order returns success without another gateway upload, including through a recreated worker using the same supplied durable ledger. An executed focused check must verify the gateway invocation count remains one. | **Open — code repaired; execution missing** |
| B3 | Prior P2; evidence remaining | Preserve cancellation as cancellation. | Gateway cancellation reaches the caller as cancellation, through propagation or an explicit cancellation result, never `RETRY` or `SUCCESS`; a transient transport failure still produces `RETRY`. Executed focused checks must distinguish these outcomes. The represented cancellation signal here is `InterruptedException`. | **Open — code repaired; execution missing** |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources. Evidence must identify this reviewed SHA, commands/tasks, environment, underlying results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. B1–B3 retain their focused assertions. | **Open — evidence missing** |

At this head, baseline execution supplies the existing B1/B3 scenarios; `:app:fixtureExcellenceCheck` supplies B2’s acknowledged-redelivery scenario. Equivalent focused evidence remains acceptable.

### False-blocker audit

Each frozen clause was checked against the request, accepted documents, and current interfaces:

- B1 requires stable operation identity, not a separately generated UUID or ledger key-allocation API. Reusing the persisted work request’s order identity satisfies that outcome.
- B2 relies on the explicitly guaranteed durable ledger. The in-memory test double does not require replacing the production durability contract or adding physical process-kill testing.
- B3 concerns the represented interruption signal. The broad `throws Exception` signature alone does not establish additional cancellation families or a permanent-error taxonomy requiring new handling.
- No concurrent-worker exclusion, crash-window transaction protocol, or actual server duplicate-transaction claim is added.

No supported clause requires withdrawal, and no new root cause was established. Existing test definitions do not constitute execution evidence.

### Proposed decision — not submitted

**COMMENT** — execution evidence is the sole remaining gate. No optional `E#` path is published below 4.5.

---

# Account deep link

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not checked; not submitted.**

Target: `fixtures/account-deeplink`  
Base: `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`  
Merge base: `a7f5c33bb149bbb01033230b40d35b2313f80c37`  
Head: `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e`  
Previous head: `4f86f0725efc9c204f9f8843d9b2553776851962`  
Merge result: not used.

Intent basis: `requests/account-deeplink.md` and the unchanged accepted `FEATURE.md` and `ARCHITECTURE.md`. The router receives an account identifier from the platform parser.

Coverage: **2/2 changed paths inspected; 0 assessed; 0 omitted.** Inspected the complete `base...HEAD` diff, repair delta, both Java files, and relevant tracked feature, architecture, build, and manifest context.

### Summary

Both entry points now delegate to one guarded operation. It checks login and canonical identifier form, obtains account-level authorization, and opens the exact authorized identifier. All three code defects are repaired by static inspection.

Required execution evidence remains absent, giving **4.0/5, validation-limited**. **No source change is requested.**

Confidence in the static repair assessment is high. Compilation, routing execution, and downstream integration remain unverified.

### Previous findings

- **Fixed:** None formally; required execution evidence is absent.
- **Still open:**
  - **B1, originally P1:** Code repaired. Both methods reach `authorization.canOpen(accountId)` before opening account data. Focused execution evidence remains missing.
  - **B2, originally P1:** Code repaired. Both methods share the `loggedIn` guard. Focused execution evidence remains missing.
  - **B3, originally P2:** Code repaired. The null check and full-string `acct-[0-9]+` match reject absent, blank, and noncanonical identifiers before opening. Focused execution evidence remains missing.
  - **B4:** Exact-target compilation and baseline execution evidence remain missing.
- **Withdrawn:** None.
- **New:** None.

### What's good

The shared operation prevents cold/warm guard divergence and passes the same identifier to authorization and account opening. The tracked checks exercise denied access, logged-out access, representative invalid input, and authorized exact-account opening.

### No blocking findings

No supported actionable code defect remains. Remaining path requirements concern execution evidence.

### Validation performed

None. Builds, Gradle, tests, fixture entry points, and application code were **NOT RUN**, as instructed. No qualifying external evidence was supplied.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and required evidence | Status |
|---|---|---|---|---|
| B1 | Prior P1; evidence remaining | Enforce account-level authorization on both entry points. | For a logged-in caller and valid account identifier, denied authorization makes each entry point return false with zero opens. Allowed authorization opens exactly the requested account and returns true. Executed focused checks must cover allow and deny through both methods. | **Open — code repaired; execution missing** |
| B2 | Prior P1; evidence remaining | Apply the login requirement equally to cold and warm entry. | With `loggedIn=false`, both methods return false and never call `Accounts.open`, even for an otherwise valid, authorized identifier. Executed focused checks must cover both methods. | **Open — code repaired; execution missing** |
| B3 | Prior P2; evidence remaining | Reject invalid account identifiers before opening data. | Both methods reject null, empty/blank, and noncanonical identifiers under the account-ID rule without calling `Accounts.open`. The existing valid example `acct-7` remains usable when login and authorization permit it. Executed focused checks must cover these rejection categories and the valid example; no particular regex or additional identifier grammar is prescribed. | **Open — code repaired; execution missing** |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources. Evidence must identify this reviewed SHA, commands/tasks, environment, underlying results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. B1–B3 retain their focused assertions. | **Open — evidence missing** |

The existing baseline and excellence checks together contain B1’s allow/deny cases and B2’s logged-out cases. The excellence check contains representative malformed/null input and successful opening. **Empty/blank execution coverage from the frozen B3 condition is not present in those check definitions** and still requires equivalent focused evidence. No new scenario or repository test-framework requirement is added.

### False-blocker audit

All frozen clauses remain supported by the identifier-validation and authenticated account-authorization contract. The implementation’s shared guard supports both entry points; no separate implementation is required.

The selected regex is a valid implementation of the modeled canonical rule, not a reviewer-mandated grammar. No additional numeric range, leading-zero restriction, URI parsing, host verification, or manifest integration is imposed.

The constructor models a fixed login state. Logout, account switching, live-session refresh, and authorization-exception behavior are not represented requirements and are not added. The tests’ stronger assertion that invalid/logged-out input avoids authorization calls also does not become a new B condition.

### Proposed decision — not submitted

**COMMENT** — execution evidence is the sole remaining gate. No optional `E#` path is published below 4.5.

---

All three exact heads were rechecked and their working trees remained unchanged. No files or repository state were modified.