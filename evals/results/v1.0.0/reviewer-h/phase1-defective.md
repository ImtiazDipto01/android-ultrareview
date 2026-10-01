# Profile setup

## Android UltraReview — confidence 2.5/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed using Git objects; live currentness was not checked; not submitted.**

Target: `fixtures/profile-setup`; base `base@4a1359633c723bd539a81664038da19c9783c4c7`; merge base `4a1359633c723bd539a81664038da19c9783c4c7`; head `876b6408dab4eb3b4b320b397ac1ba6d3d8ebe6f` (`HEAD = defective`); merge result not used.

Intent basis: `requests/profile-setup.md` and the unchanged, base-branch `FEATURE.md` and `ARCHITECTURE.md`.

Coverage: **2/2 changed paths inspected; 0 assessed; 0 omitted.** Read the complete `base...HEAD` diff, both added Java files, and surrounding tracked architecture, feature, build, and manifest sources.

### Summary

Setup reports completion before persistence succeeds, accepts duplicate submissions, and leaves manual entry disabled after location denial. The persistence defect is P1; the repeated-action and denial defects are bounded P2s. This supports **2.5/5**.

Confidence in these static findings is high. Android UI integration and execution behavior remain unverified. Required compilation and focused checks are **NOT RUN**, as instructed; absent qualifying evidence independently caps merge confidence at 4.0.

### What's good

The repository interface provides a small, controllable seam for testing save ordering and failures. The supplied check covers the intended successful completion state, although it has not been executed.

### Blocking themes

- Completion can precede—and survive—failed persistence.
- Repeated submission can perform duplicate saves.
- Optional permission denial leaves the manual-entry gate closed.

### Validation performed

No qualifying build, test, fixture entry point, or runtime check was executed or supplied. Static inspection is not reported as a passing test.

### Path to 4.5/5

These IDs and conditions are frozen for this fixture.

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | P1 finding | Complete only after successful persistence; preserve retry after failure. | During `save`, `complete` and `navigated` remain false. A thrown save failure leaves both false; a subsequent successful submission persists the selected city before setting them true. An executed focused regression check must assert this ordering and failure-then-retry sequence on the reviewed target. | Open |
| B2 | P2 finding | Prevent duplicate submission effects within one setup operation. | A second submission while the first save is in progress does not invoke another save; repeating submission after successful completion does not save again. The operation produces one successful save and one completion/navigation transition. Executed deterministic checks must exercise an overlapping or reentrant call and a repeat after success. | Open |
| B3 | P2 finding | Restore manual entry after location denial. | Calling `onLocationDenied()` clears `locating`; a subsequent manual city submission can reach the repository and complete after a successful save. An executed focused check must cover that sequence. | Open |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources. Evidence must identify the exact reviewed SHA, commands/tasks, environment, underlying results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. B1–B3 retain their focused assertions; this adds no duplicate scenario requirement. | Open |

### Proposed decision — not submitted

**REQUEST CHANGES** — GitHub event `REQUEST_CHANGES`.

### Draft inline findings

1. **[P1][B1] Publish completion only after the save succeeds** — `app/src/main/java/fixture/profile/ProfileSetupController.java:18–20 (RIGHT)`  
   Evidence basis: `Static trace`. `submit()` sets both completion flags before calling the fallible repository. If `save()` throws, the selected city was not successfully persisted but setup remains marked complete and navigated. Move the success transition after persistence and preserve incomplete, retryable state on failure. Verify the ordering and retry sequence specified in B1.

2. **[P2][B2] Guard repeated submissions before invoking the repository** — `app/src/main/java/fixture/profile/ProfileSetupController.java:17–20 (RIGHT)`  
   Evidence basis: `Static trace`. Every call reaches `repository.save`, without checking an in-flight or completed operation. A repeated call therefore saves again, including a call made while an earlier save remains active. Guard the setup operation against duplicate submission, while allowing retry after failure. Verify B2’s two repeated-call cases.

3. **[P2][B3] Clear the location gate when permission is denied** — `app/src/main/java/fixture/profile/ProfileSetupController.java:23–24 (RIGHT)`  
   Evidence basis: `Static trace`. `locating` starts true and the denial handler never changes it. The modeled manual-entry gate consequently stays disabled after optional permission denial. Clear that transient state and verify denial followed by manual submission.

### False-blocker audit

Reopened each anchor and checked the complete controller and its sole tracked caller. No guard or recovery path defeats these traces; the existing check only exercises success. The navigation representation is a boolean, so duplicate saves are directly demonstrated; an actual double screen transition is not claimed as observed. No requirement is added for controller recreation, process death, cancellation, UI frameworks, or error-message presentation.

---

# Order upload

## Android UltraReview — confidence 2.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed using Git objects; live currentness was not checked; not submitted.**

Target: `fixtures/order-upload`; base `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; merge base `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`; head `92efc1ba93b352870851adedac9a362197b6f683` (`HEAD = defective`); merge result not used.

Intent basis: `requests/order-upload.md` and the unchanged, base-branch `FEATURE.md` and `ARCHITECTURE.md`, including the supplied durable-ledger contract.

Coverage: **2/2 changed paths inspected; 0 assessed; 0 omitted.** Read the complete `base...HEAD` diff, both added Java files, and surrounding tracked architecture, feature, build, and manifest sources.

### Summary

Retries create new operation keys, acknowledged orders remain eligible for upload, and cancellation becomes retry. The two P1 defects undermine independent core upload guarantees; cancellation adds a bounded P2 recovery defect. This supports **2.0/5**.

Confidence in the static traces is high. Actual server effects, scheduler integration, and execution remain unverified. Required compilation and focused checks are **NOT RUN**, as instructed; absent qualifying evidence independently caps merge confidence at 4.0.

### What's good

The gateway interface permits deterministic success, transport-failure, and cancellation checks. Normal gateway return maps directly to `SUCCESS`.

### Blocking themes

- Retry identity changes between attempts.
- Successful acknowledgement does not suppress later delivery.
- Cancellation is classified as an ordinary retry.

### Validation performed

No qualifying build, test, fixture entry point, or runtime check was executed or supplied.

### Path to 4.5/5

These IDs and conditions are frozen for this fixture.

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | P1 finding | Reuse the order’s operation key across retry and worker recreation. | For one pending order, an attempt followed by transient failure and retry uses the same key, including when retry uses a newly created worker backed by the same supplied durable ledger. An executed focused check must capture and compare gateway keys across those attempts. | Open |
| B2 | P1 finding | Suppress uploads after recorded acknowledgement. | After a successful upload records acknowledgement, repeated delivery of that order returns success without another gateway upload, including through a recreated worker using the same supplied durable ledger. An executed focused check must verify the gateway invocation count remains one. | Open |
| B3 | P2 finding | Preserve cancellation as cancellation. | Gateway cancellation reaches the caller as cancellation, through propagation or an explicit cancellation result, never `RETRY` or `SUCCESS`; a transient transport failure still produces `RETRY`. Executed focused checks must distinguish these outcomes. | Open |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources. Evidence must identify the exact reviewed SHA, commands/tasks, environment, underlying results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. B1–B3 retain their focused assertions. | Open |

### Proposed decision — not submitted

**REQUEST CHANGES** — GitHub event `REQUEST_CHANGES`.

### Draft inline findings

1. **[P1][B1] Reuse a persisted operation key for retry attempts** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:20 (RIGHT)`  
   Evidence basis: `Static trace`. Each invocation generates a fresh UUID. After a transport failure, retrying the same order therefore presents a different idempotency key; a server that accepted the earlier request before its response was lost cannot identify the retry by that key. Obtain the operation key from durable per-order state before uploading. Verify stable keys across retry and worker recreation as specified in B1.

2. **[P1][B2] Skip orders whose acknowledgement is already recorded** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:20–21 (RIGHT)`  
   Evidence basis: `Static trace`. After an upload returns successfully, the worker returns `SUCCESS` without recording or consulting acknowledgement state. Calling `run()` again for that order unconditionally invokes the gateway again, violating the explicit repeated-delivery requirement. Record successful acknowledgement and check it before subsequent uploads. Verify B2’s no-additional-upload outcome.

3. **[P2][B3] Preserve cancellation before mapping failures to retry** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:22–23 (RIGHT)`  
   Evidence basis: `Static trace`. A gateway-thrown cancellation exception enters the general `Exception` catch and becomes `RETRY`. The caller loses the cancellation outcome and may schedule another attempt. Separate cancellation from retryable failure and verify the distinct outcomes in B3.

### False-blocker audit

The full worker contains no stored key, acknowledgement state, or cancellation branch; its sole tracked check covers only success. Stable-key reuse and acknowledgement suppression remain separate findings: fixing either alone leaves the other contract broken.

The architecture explicitly guarantees the supplied ledger’s durability. These conditions require correct use of that boundary, not implementation of a database or physical process-kill testing. They do not add crash-window atomicity, simultaneous-worker coordination, or an unmodeled permanent-failure taxonomy. No actual duplicate financial transaction is claimed.

---

# Account deep link

## Android UltraReview — confidence 2.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed using Git objects; live currentness was not checked; not submitted.**

Target: `fixtures/account-deeplink`; base `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`; merge base `a7f5c33bb149bbb01033230b40d35b2313f80c37`; head `4f86f0725efc9c204f9f8843d9b2553776851962` (`HEAD = defective`); merge result not used.

Intent basis: `requests/account-deeplink.md` and the unchanged, base-branch `FEATURE.md` and `ARCHITECTURE.md`. The platform parser supplies an untrusted account identifier to this router.

Coverage: **2/2 changed paths inspected; 0 assessed; 0 omitted.** Read the complete `base...HEAD` diff, both added Java files, and surrounding tracked architecture, feature, build, and manifest sources.

### Summary

Neither entry point checks account-level authorization; warm-start entry also bypasses login. Both accept invalid identifiers without a guard. Two independent P1 access-control defects and one bounded P2 input-validation defect support **2.0/5**.

Confidence in the modeled boundary violations is high. The packet does not demonstrate a deployed exploit or downstream backend behavior. Required compilation and focused checks are **NOT RUN**, as instructed; absent qualifying evidence independently caps merge confidence at 4.0.

### What's good

Cold-start entry already rejects logged-out callers. The `Accounts` seam makes unwanted opens observable in focused checks.

### Blocking themes

- Login alone does not authorize access to a particular account.
- Warm-start entry omits the login requirement.
- Invalid identifiers reach the account-opening boundary.

### Validation performed

No qualifying build, test, fixture entry point, or runtime check was executed or supplied.

### Path to 4.5/5

These IDs and conditions are frozen for this fixture.

| ID | Source | Required outcome | Objective pass condition and qualifying evidence | Status |
|---|---|---|---|---|
| B1 | P1 finding | Enforce account-level authorization on both entry points. | For a logged-in caller and valid account identifier, denied authorization makes each entry point return false with zero opens. Allowed authorization opens exactly the requested account and returns true. Executed focused checks must cover allow and deny through both methods. | Open |
| B2 | P1 finding | Apply the login requirement equally to cold and warm entry. | With `loggedIn=false`, both methods return false and never call `Accounts.open`, even for an otherwise valid, authorized identifier. Executed focused checks must cover both methods. | Open |
| B3 | P2 finding | Reject invalid account identifiers before opening data. | Both methods reject null, empty/blank, and noncanonical identifiers under the account-ID rule without calling `Accounts.open`. The existing valid example `acct-7` remains usable when login and authorization permit it. Executed focused checks must cover these rejection categories and the valid example; no particular regex or additional identifier grammar is prescribed. | Open |
| B4 | Validation gap | Establish exact-target compilation and baseline execution. | Successful `:app:compileFixtureChecks` and `:app:fixtureBaselineCheck`, or equivalent Java 17 compilation and baseline execution of the tracked fixture sources. Evidence must identify the exact reviewed SHA, commands/tasks, environment, underlying results, and inspectable output. Direct, authoritative CI, or complete traceable contributor evidence qualifies. B1–B3 retain their focused assertions. | Open |

### Proposed decision — not submitted

**REQUEST CHANGES** — GitHub event `REQUEST_CHANGES`.

### Draft inline findings

1. **[P1][B1] Authorize the requested account before opening it** — `app/src/main/java/fixture/links/AccountDeepLinkRouter.java:18 (RIGHT)`  
   Evidence basis: `Static trace`. A logged-in caller can supply an account identifier and immediately reach `accounts.open`; warm entry does the same. Neither method consults resource-level authorization, and the `Accounts` interface supplies no guaranteed authorization gate. Enforce authorization for the requested account before either open. Verify the allow/deny matrix in B1.

2. **[P1][B2] Require login on warm-start account links** — `app/src/main/java/fixture/links/AccountDeepLinkRouter.java:23 (RIGHT)`  
   Evidence basis: `Static trace`. Constructing the router with `loggedIn=false` blocks `openColdStart`, but `onNewIntent` still calls `accounts.open` and returns true. Apply the same login gate to warm entry and verify that both logged-out paths produce zero opens.

3. **[P2][B3] Validate identifiers before passing them to account access** — `app/src/main/java/fixture/links/AccountDeepLinkRouter.java:18 (RIGHT)`  
   Evidence basis: `Static trace`. With login permitted, even null or empty input is forwarded unchanged to `accounts.open`; warm entry is also unguarded. The accepted architecture requires canonical identifier validation at this boundary. Reject invalid identifiers before opening data and verify B3’s rejection and valid-input cases.

### False-blocker audit

Reopened both entry points and checked their interface and sole tracked caller. No upstream guarantee makes the identifier trusted, and no downstream authorization contract defeats these findings. The existing check covers only logged-in cold-start success.

The router receives an identifier, so URI scheme/host parsing and manifest App Link verification are outside these findings. The constructor’s immutable login value does not model logout, account switching, or live session refresh; those transitions are not added to the frozen path. The packet does not specify a complete identifier grammar, so this review does not invent one.

---

All three checkouts remained unchanged. IDs are scoped to their respective fixtures. Future re-reviews must preserve these supported conditions and evidence requirements; any clause shown to exceed the modeled contract must be explicitly withdrawn as a rubric correction.