# Profile setup

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/profile-setup`  
Base: `base@4a1359633c723bd539a81664038da19c9783c4c7`  
Merge base: `4a1359633c723bd539a81664038da19c9783c4c7`  
Head: `3ec59ede43c81b313208100d52872340692f4b39` — unchanged from the preceding review.  
Merge result: not used.

Intent basis: `requests/profile-setup.md`, accepted `FEATURE.md` and `ARCHITECTURE.md`, and the profile disposition in `adjudication/pass-conditions.md`.

Coverage: **2/2 changed paths previously inspected; 0 omitted.** The unchanged code assessment and complete-diff coverage are carried forward without rescanning. Target identity and clean working-tree state were confirmed.

### Summary

The supported code repairs remain complete by static inspection: persistence precedes completion, save failure remains retryable, in-flight reentrant submission is suppressed, and permission denial restores manual entry.

Execution evidence remains the sole gate. **No source change is requested.** The score remains **4.0/5**, with **COMMENT**.

Confidence in the carried-forward static assessment is high; compilation and execution remain unverified.

### Previous findings

- **Fixed:** None formally; required execution evidence remains absent.
- **Still open:**
  - **B1, originally P1:** Code repaired; ordering and failure/retry execution evidence missing.
  - **B2, originally P2:** In-flight suppression repaired; focused execution evidence missing.
  - **B3, originally P2:** Denial recovery repaired; focused execution evidence missing.
  - **B4:** Exact-target compilation and baseline execution evidence missing.
- **Withdrawn — clause only:** B2’s post-success-repeat prohibition and its associated execution requirement.
- **New:** None.

### Adjudication continuity

The adjudication confirms that the accepted duplicate-submission contract concerns a submission **while another is in flight**, not permanent single-use of the controller.

Only B2’s post-success-repeat clause and associated evidence requirement are withdrawn. B2 retains its ID, supported root cause, and in-flight outcome. B1’s supported persistence-ordering and failure-retry requirements remain intact; no B1 condition is dropped.

### What's good

The previously inspected checks provide focused scenarios for ordering, retry, denial recovery, and reentrant submission.

### No blocking findings

No supported actionable code defect remains. Open items concern missing execution evidence.

### Validation performed

None. No builds, tests, Gradle tasks, fixture entry points, or application code were executed, and no qualifying execution evidence was supplied.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | Prior P1 | Complete only after successful persistence; preserve failure retryability. | During the active setup save, `complete` and `navigated` remain false. Save failure leaves both false; a subsequent successful submission persists the selected city before setting them true. An executed focused check must establish ordering and failure followed by successful retry. | **Open — code repaired; evidence missing** |
| B2 | Prior P2; adjudicated correction | Suppress duplicate effects while saving. | **A second submission while the first save is in flight causes no additional save or completion/navigation; the original submission completes/navigates only after its save succeeds.** Preserve failure retryability. An executed deterministic overlapping or reentrant check must establish this outcome. Post-success repetition is excluded by the explicit correction. | **Open — code repaired; evidence missing** |
| B3 | Prior P2 | Restore manual entry after denial. | `onLocationDenied()` clears `locating`; subsequent manual submission reaches the repository and completes after successful saving. An executed focused check must cover that sequence. | **Open — code repaired; evidence missing** |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources, on the head above. Evidence must identify SHA, commands/tasks, environment, results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. | **Open — evidence missing** |

B1–B3 retain their focused evidence requirements independently of B4. The existing baseline check supplies B1/B3 scenarios; `:app:fixtureExcellenceCheck` supplies the reentrant B2 scenario. Equivalent evidence remains acceptable.

### False-blocker audit

The adjudication resolves the post-completion clause without discarding the supported in-flight defect. Reassigning the modeled navigation boolean does not prove a second actual navigation. No arbitrary threading, controller recreation, or process-death requirement is added.

### Proposed decision — not submitted

**COMMENT.** Validation remains missing. No `E#` path is published below 4.5.

---

# Order upload

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target reviewed; not submitted.**

Target: `fixtures/order-upload`  
Base: `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Merge base: `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Head: `7ddac75b89545a0fd8fb6b6332c8724cfda080ce`  
Previous repaired head: `c802b55d7510f0cf008e78ddea0e7527508f81e4`  
Merge result: not used.

Intent basis: `requests/order-upload.md`, accepted `FEATURE.md` and `ARCHITECTURE.md`, and the order disposition in `adjudication/pass-conditions.md`.

Coverage: **2/2 changed paths inspected; 0 omitted.** Read the complete current `base...HEAD` diff and the complete repair delta from `c802b55…`, including both Java files. Unchanged surrounding contract and build context is carried forward.

### Summary

The advanced target explicitly catches `CancellationException` before the general retry catch and returns `CANCELLED`. It preserves the interruption branch, stable order-based key, and durable-ledger acknowledgement handling. A focused cancellation-exception assertion has also been added.

All retained code defects are now repaired by static inspection. Execution evidence remains absent, so the current assessment is **4.0/5, validation-limited**, with **COMMENT**. **No source change is requested.**

Confidence in the current static repair assessment is high; compilation and execution remain unverified.

### Previous findings

- **Fixed:** None formally; required execution evidence remains absent.
- **Still open:**
  - **B1, originally P1:** Stable retry identity remains repaired; execution evidence missing.
  - **B2, originally P1:** Acknowledged redelivery suppression remains repaired; execution evidence missing.
  - **B3, originally P2:** The advanced target repairs both interruption and `CancellationException` handling; execution evidence missing.
  - **B4:** Exact-target compilation and baseline execution evidence missing.
- **Withdrawn:** No B item or supported B3 condition.
- **New:** None. `CancellationException` belongs to retained B3.

### Adjudication continuity

My preceding review incorrectly narrowed B3 to `InterruptedException`. The adjudication establishes that `CancellationException` also fits the modeled gateway interface and is governed by the existing cancellation contract.

At `c802b55…`, that exception reached the general catch and returned `RETRY`. B3 therefore still contained an actionable P2, and my prior validation-only assessment and **COMMENT** disposition were incorrect. That head warranted an ordinary **4.0/5 / REQUEST_CHANGES** assessment.

I retract that narrowing. B3’s original cancellation outcome remains unchanged; no new ID or stronger requirement is introduced. At the current `7ddac75…` head, the new catch repairs the retained case, leaving only execution evidence missing.

### What's good

The repair adds a specific cancellation branch and a corresponding baseline assertion while preserving the separate interruption behavior and transient-failure retry scenario.

### No blocking findings

No retained actionable code defect remains on the current target. B3 remains open for evidence, not for another code repair.

### Validation performed

None. No builds, tests, Gradle tasks, fixture entry points, or application code were executed. The new assertion was inspected but has not been observed passing.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | Prior P1 | Reuse the operation key across retry and worker recreation. | For one pending order, transient failure followed by retry uses the same key, including through a recreated worker sharing the supplied durable ledger. An executed focused check must capture and compare the gateway keys. | **Open — code repaired; evidence missing** |
| B2 | Prior P1 | Suppress uploads after recorded acknowledgement. | Following successful upload and acknowledgement, repeated delivery returns success without another gateway upload, including through a recreated worker sharing the ledger. An executed focused check must show that the gateway invocation count remains one. | **Open — code repaired; evidence missing** |
| B3 | Prior P2; original scope retained | Preserve cancellation as cancellation. | Gateway cancellation reaches the caller through propagation or an explicit cancellation result, never `RETRY` or `SUCCESS`; transient transport failure still returns `RETRY`. Executed focused checks must distinguish these outcomes, retaining both the interruption and `CancellationException` cases. | **Open — code repaired; evidence missing** |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources, on the current head above. Evidence must identify SHA, commands/tasks, environment, results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. | **Open — evidence missing** |

The current baseline check contains B1 and both B3 cancellation scenarios. `:app:fixtureExcellenceCheck` contains B2’s acknowledged-redelivery scenario. These focused requirements remain locked regardless of task names; equivalent evidence is acceptable.

### False-blocker audit

The cancellation-exception case is representable without adding a lifecycle assumption or integration. Its retention corrects my earlier false exclusion rather than expanding B3.

Stable identity does not require a separately generated UUID. Ledger durability remains an accepted interface guarantee; physical process-kill testing, concurrent-worker exclusion, and crash-window transaction protocols are not added. Inspection of the new catch confirms it precedes the general retry branch and does not acknowledge a cancelled upload.

### Proposed decision — not submitted

**COMMENT.** The current code repair is complete; exact-target execution evidence remains missing. No `E#` path is published below 4.5.

---

# Account deep link

## Android UltraReview — confidence 4.0/5 (validation-limited)

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/account-deeplink`  
Base: `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`  
Merge base: `a7f5c33bb149bbb01033230b40d35b2313f80c37`  
Head: `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e` — unchanged from the preceding review.  
Merge result: not used.

Intent basis: `requests/account-deeplink.md` and accepted `FEATURE.md` and `ARCHITECTURE.md`. The adjudication supplies no changed account disposition.

Coverage: **2/2 changed paths previously inspected; 0 omitted.** The unchanged code assessment and complete-diff coverage are carried forward without rescanning. Target identity and clean working-tree state were confirmed.

### Summary

Both entry points retain the shared login, identifier-validation, and account-authorization gate before opening the exact authorized account. The code assessment remains unchanged.

Execution evidence is the sole remaining gate: **4.0/5, validation-limited**, with **COMMENT**. **No source change is requested.**

Confidence in the carried-forward static assessment is high; compilation and execution remain unverified.

### Previous findings

- **Fixed:** None formally; required execution evidence remains absent.
- **Still open:**
  - **B1, originally P1:** Account-level authorization repaired; execution evidence missing.
  - **B2, originally P1:** Shared login enforcement repaired; execution evidence missing.
  - **B3, originally P2:** Identifier validation repaired; execution evidence missing.
  - **B4:** Exact-target compilation and baseline execution evidence missing.
- **Withdrawn:** None.
- **New:** None.

### Adjudication continuity

No account finding, pass condition, evidence requirement, or code classification changes. The profile and order dispositions do not alter this fixture’s path.

### What's good

The shared operation prevents cold/warm guard divergence and preserves the exact identifier between authorization and opening.

### No blocking findings

No supported actionable code defect remains. Open items concern execution evidence.

### Validation performed

None. No builds, tests, Gradle tasks, fixture entry points, or application code were executed, and no qualifying execution evidence was supplied.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | Prior P1 | Enforce account-level authorization through both entry points. | For a logged-in caller and valid identifier, denied authorization returns false with zero opens through each method. Allowed authorization opens exactly the requested account and returns true. Executed focused checks must cover allow and deny through both methods. | **Open — code repaired; evidence missing** |
| B2 | Prior P1 | Apply the login requirement equally. | With `loggedIn=false`, both methods return false and never call `Accounts.open`, even for an otherwise valid, authorized identifier. Executed focused checks must cover both methods. | **Open — code repaired; evidence missing** |
| B3 | Prior P2 | Reject invalid identifiers before opening data. | Both methods reject null, empty/blank, and noncanonical identifiers under the account-ID rule without calling `Accounts.open`. The valid example `acct-7` remains usable when login and authorization permit it. Executed focused checks must cover these rejection categories and the valid example; no particular regex or additional grammar is prescribed. | **Open — code repaired; evidence missing** |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources, on the head above. Evidence must identify SHA, commands/tasks, environment, results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. | **Open — evidence missing** |

The carried-forward evidence assessment remains unchanged: baseline and excellence check definitions cover authorization, login, representative invalid input, and successful opening. The frozen B3 requirement for **empty/blank execution coverage** still needs equivalent focused evidence; it was not present in those definitions.

### False-blocker audit

No new identifier grammar, URI parsing, manifest verification, live-session transition, logout, or account-switch requirement is added. The existing tests’ stronger authorization-call-order assertions do not become new B conditions. All supported conditions and evidence requirements remain unchanged.

### Proposed decision — not submitted

**COMMENT.** Execution evidence remains missing. No `E#` path is published below 4.5.

---

No files or repository state were modified. No application or fixture code was executed, and no external links in the adjudication were followed.