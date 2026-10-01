Review policy: isolated `evaluator-skill/SKILL.md` and its required references; supplied bundle digest `e3772db1964652621ed4603f2ecc4c12b695b86637f8e336ad2d4dbc2eaed3fe`. Inspection stayed inside the packet. No builds, tests, application code, or network resources were used; no files were modified.

IDs are scoped independently to each fixture. The pass conditions and evidence requirements below are frozen for subsequent re-review.

# Profile setup

## Android UltraReview — confidence 2.5/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not assessed; not submitted.**

Target:

- Base `base@4a1359633c723bd539a81664038da19c9783c4c7`
- Merge base `4a1359633c723bd539a81664038da19c9783c4c7`
- Head `defective@876b6408dab4eb3b4b320b397ac1ba6d3d8ebe6f`, matching checked-out `HEAD`
- Merge result: not used.

Intent basis: `requests/profile-setup.md`, accepted base `FEATURE.md` and `ARCHITECTURE.md`.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Inspected the complete `base...HEAD` diff, both Java files, and all tracked architecture, feature, build, and manifest context.

### Summary

The controller violates the required persistence-before-completion contract and has two bounded submission/permission defects. **2.5/5** reflects one P1 plus material secondary gaps. Confidence in these static causal findings is high; runtime and build compatibility remain unverified.

### What's good

- The repository interface provides a direct seam for checking persistence ordering and failure recovery.
- The existing check expresses the successful completion outcome, although it covers only that path.

### Blocking themes

- Setup reports completion before persistence succeeds.
- Repeated submission can invoke persistence repeatedly.
- Permission denial leaves manual entry disabled in the modeled state.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** focused regression checks for B1–B3; execution prohibited in this phase.
- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`; exact-target build/test evidence is absent.
- Static inspection is the findings’ evidence basis, not a passing validation result.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and expected qualifying evidence | Status |
|---|---|---|---|---|
| B1 | P1 finding | Complete setup only after saving the city successfully. | During `save`, `complete` and `navigated` remain false. A thrown save failure leaves both false and permits another submission; a successful retry saves the selected city before setting both true. **Evidence:** focused automated checks with observing, failing, and subsequently successful repository doubles, passing on the reviewed target. | Open |
| B2 | P2 finding | Prevent duplicate persistence/completion for one setup submission. | A second submission while the first save is in flight does not invoke another save; rapid repetition after successful completion does not recommit that setup. The accepted successful submission produces one completion/navigation transition. **Evidence:** deterministic repeated-submission checks with a controlled repository, passing on the reviewed target. | Open |
| B3 | P2 finding | Restore manual entry after location denial. | Calling `onLocationDenied()` clears `locating`; manual city submission remains usable and can save successfully. **Evidence:** a focused denial-then-manual-submission automated check passing on the reviewed target. | Open |
| B4 | Validation gap | Establish relevant exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant build/test jobs, succeed for the reviewed head or qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, toolchain/environment, underlying results and exit status. B1–B3’s behavioral assertions remain their own requirements. | Open |

Credible contributor logs or authoritative CI can qualify. The existing Java harness is sufficient for the modeled behavior; no device matrix or new framework is required.

### False-blocker audit

Reopened the anchors and searched all tracked Java callers and state mutations. There is no submission guard, rollback, or denial-state reset. `FixtureCheck` calls `submit` once with a successful repository, so it does not defeat these findings.

The duplicate-work finding is based on repeated `save` calls; the boolean `navigated` alone does not prove two real navigation events. No process-death restoration, location-success/cancellation behavior, UI implementation, or storage framework is added to the pass conditions.

### Proposed decision — not submitted

**REQUEST_CHANGES**

### Draft inline findings

1. **[P1][B1] Publish completion only after persistence succeeds** — `app/src/main/java/fixture/profile/ProfileSetupController.java:18–20 (RIGHT)`  
   Evidence basis: `Static trace`. `submit` sets both success flags before calling `save`. If saving throws, setup remains marked complete and navigated despite lacking the required saved city. Move the success transition after successful persistence and retain a retryable incomplete state on failure. Verify ordering and failure-then-retry behavior as specified in B1.

2. **[P2][B2] Guard repeated setup submissions** — `app/src/main/java/fixture/profile/ProfileSetupController.java:17–20 (RIGHT)`  
   Evidence basis: `Static trace`. Every invocation reaches `repository.save`, including overlapping calls and rapid repetition after completion. Neither an in-flight guard nor a completed-state guard prevents duplicate commits. Admit one submission for this setup, while allowing retry after failure, and verify B2’s repeated-submission cases.

3. **[P2][B3] Clear locating state when permission is denied** — `app/src/main/java/fixture/profile/ProfileSetupController.java:23–24 (RIGHT)`  
   Evidence basis: `Static trace`. `locating` starts true and the denial handler changes nothing. Under the explicitly modeled manual-entry gate, denying optional permission therefore leaves manual entry disabled. Clear the terminal loading state and verify denial followed by manual submission.

---

# Order upload

## Android UltraReview — confidence 2.5/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not assessed; not submitted.**

Target:

- Base `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`
- Merge base `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`
- Head `defective@92efc1ba93b352870851adedac9a362197b6f683`, matching checked-out `HEAD`
- Merge result: not used.

Intent basis: `requests/order-upload.md`, accepted base `FEATURE.md` and `ARCHITECTURE.md`, including the stated durable app-provided ledger contract.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Inspected the complete `base...HEAD` diff, both Java files, and all tracked architecture, feature, build, and manifest context.

### Summary

Each attempt generates a new operation identity, acknowledged work is uploaded again, and cancellation becomes retry. **2.5/5** reflects a significant P1 identity defect plus two bounded independent gaps. Confidence in the static findings is high; no backend behavior or runtime result is claimed.

### What's good

- The gateway seam can directly expose upload count, operation keys, and cancellation outcomes.
- Normal gateway return maps to success.

### Blocking themes

- Retry identity is unstable across attempts and worker recreation.
- Acknowledgement does not suppress later delivery.
- Cancellation loses its distinct control-flow meaning.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** focused regression checks for B1–B3; execution prohibited in this phase.
- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`; exact-target build/test evidence is absent.
- The existing check covers only one successful upload and supplies no executed evidence.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and expected qualifying evidence | Status |
|---|---|---|---|---|
| B1 | P1 finding | Preserve one operation identity across retries and worker recreation. | An order’s idempotency key is retained with its work before uploading; after a transient failure, retrying that order—including through a recreated worker using the same durable ledger—passes the same key to the gateway. **Evidence:** a focused automated failure/retry/recreation check recording gateway keys, passing on the reviewed target. | Open |
| B2 | P2 finding | Skip upload after acknowledgement. | After an upload returns successfully and acknowledgement is recorded, subsequent delivery of that order, including through a recreated worker using the same durable ledger, returns success without another gateway upload. **Evidence:** a focused automated success/redelivery/recreation check asserting upload count, passing on the reviewed target. | Open |
| B3 | P2 finding | Preserve cancellation as cancellation. | Gateway cancellation remains observable to the caller as cancellation, rather than `RETRY` or ordinary success; a transient transport failure still returns `RETRY`. **Evidence:** focused automated cancellation and transient-failure checks passing on the reviewed target. | Open |
| B4 | Validation gap | Establish relevant exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant build/test jobs, succeed for the reviewed head or qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, toolchain/environment, underlying results and exit status. B1–B3’s behavioral assertions remain their own requirements. | Open |

Credible contributor logs or authoritative CI can qualify. Worker recreation with the contractually durable ledger is sufficient for the modeled persistence boundary; a new storage engine or OS process-kill test is not required.

### False-blocker audit

Reopened the upload and exception paths and searched all tracked callers and contracts. No persisted identity, acknowledgement check, or cancellation exception branch exists. The declared `CANCELLED` result is never used.

B1 and B2 are distinct: a stable key protects retry identity but does not satisfy the explicit requirement to avoid another upload after acknowledgement. B2 is P2 without double-counting B1’s duplicate-operation risk. No concurrent-worker arbitration, crash between remote acceptance and local acknowledgement, ledger-write failure, or unspecified permanent-error taxonomy is added to the frozen path.

### Proposed decision — not submitted

**REQUEST_CHANGES**

### Draft inline findings

1. **[P1][B1] Reuse the persisted operation key on retry** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:20 (RIGHT)`  
   Evidence basis: `Static trace`. Each `run` generates a fresh UUID. If an upload is accepted remotely but its response fails, retrying the same order presents a different identity and cannot rely on the original operation’s idempotency protection. Obtain and retain the operation key with the work request before uploading, and verify key reuse across retry and worker recreation.

2. **[P2][B2] Skip acknowledged orders before calling the gateway** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:20–21 (RIGHT)`  
   Evidence basis: `Static trace`. After one successful `run`, delivering the same order again immediately calls `upload` again. No acknowledgement is recorded or consulted. Record successful acknowledgement and short-circuit later delivery, using the durable ledger contract; verify that redelivery leaves gateway call count unchanged.

3. **[P2][B3] Preserve cancellation through the exception handler** — `app/src/main/java/fixture/orders/OrderUploadWorker.java:22–23 (RIGHT)`  
   Evidence basis: `Static trace`. A gateway `CancellationException` is caught by `catch (Exception)` and converted to `RETRY`. The caller therefore receives an ordinary retry outcome for cancelled work, contrary to the feature contract. Preserve the cancellation signal before handling retryable failure, and verify cancellation separately from transient transport failure.

---

# Account deep link

## Android UltraReview — confidence 2.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local refs reviewed; live currentness was not assessed; not submitted.**

Target:

- Base `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`
- Merge base `a7f5c33bb149bbb01033230b40d35b2313f80c37`
- Head `defective@4f86f0725efc9c204f9f8843d9b2553776851962`, matching checked-out `HEAD`
- Merge result: not used.

Intent basis: `requests/account-deeplink.md`, accepted base `FEATURE.md` and `ARCHITECTURE.md`. The modeled input is an account identifier already supplied by the platform route parser.

Coverage: **2/2 changed paths classified; 2 inspected; 0 assessed; 0 omitted.** Inspected the complete `base...HEAD` diff, both Java files, and all tracked architecture, feature, build, and manifest context.

### Summary

Resource authorization is absent from both entry points, warm entry also bypasses login, and identifiers are passed through without validation. **2.0/5** reflects independent P1 failures at the feature’s central authorization boundary plus a P2 input-validation defect. Static confidence is high; actual backend data disclosure has not been demonstrated and is not assumed.

### What's good

- Cold entry rejects a logged-out session.
- The `Accounts` callback allows focused checks of whether data opening was attempted and which identifier was passed.

### Blocking themes

- Login is treated as sufficient authority to open any supplied account.
- Warm entry omits even the login check.
- Malformed identifiers reach the account-opening boundary.

### Validation performed

No qualifying check was executed or supplied.

- **NOT RUN:** focused regression checks for B1–B3; execution prohibited in this phase.
- **NOT RUN:** `:app:assembleDebug` and `:app:fixtureBaselineCheck`; exact-target build/test evidence is absent.
- The existing check exercises only logged-in cold entry with `"acct-7"`.

### Path to 4.5/5

| ID | Source | Required outcome | Objective pass condition and expected qualifying evidence | Status |
|---|---|---|---|---|
| B1 | P1 finding | Enforce account-level authorization on both entry points. | For a logged-in session, both handlers reject a canonical identifier for an unauthorized account without calling `Accounts.open`; an authorized canonical identifier opens exactly that account. **Evidence:** focused automated allowed/denied-account checks for both entry points, recording opening calls, passing on the reviewed target. | Open |
| B2 | P1 finding | Apply login gating equally to cold and warm entry. | With `loggedIn == false`, both handlers return false and make no `Accounts.open` call for an otherwise valid account identifier. **Evidence:** focused automated logged-out checks for both entry points passing on the reviewed target. | Open |
| B3 | P2 finding | Validate the supplied identifier before opening data. | Both handlers reject null, empty, and noncanonical account identifiers without an opening call; an authorized canonical identifier is opened unchanged. Validation uses an explicit canonical account-ID rule. **Evidence:** focused automated checks covering null, empty, a noncanonical identifier, and an accepted canonical identifier through both handlers, passing on the reviewed target. No particular regex is imposed. | Open |
| B4 | Validation gap | Establish relevant exact-target build/test success. | `:app:assembleDebug` and `:app:fixtureBaselineCheck`, or equivalent relevant build/test jobs, succeed for the reviewed head or qualifying merge result. **Evidence:** inspectable logs identifying SHA, commands/tasks, `app` debug/build scope, toolchain/environment, underlying results and exit status. B1–B3’s behavioral assertions remain their own requirements. | Open |

Credible contributor logs or authoritative CI can qualify. Direct execution of the modeled Java handlers is sufficient; platform routing instrumentation is not an additional gate.

### False-blocker audit

Reopened both handlers, the `Accounts` interface, the sole caller, and the accepted contracts. No account-authorization or identifier-validation guard defeats the findings. The cold login guard does not protect warm entry.

The packet does not specify a complete identifier grammar, so no numeric range or particular regex is required. Full URI parsing, scheme/host validation, App Link verification, logout transitions, restored back stacks, and backend authorization implementation are outside these frozen conditions. The findings concern calls crossing the modeled `Accounts.open` boundary, not an asserted production exploit.

### Proposed decision — not submitted

**REQUEST_CHANGES**

### Draft inline findings

1. **[P1][B1] Authorize the requested account before opening it** — `app/src/main/java/fixture/links/AccountDeepLinkRouter.java:18 (RIGHT)`  
   Evidence basis: `Static trace`. A logged-in caller can supply an unauthorized account identifier, and both handlers pass it directly to `Accounts.open`. Login does not establish resource-level authority, which the accepted architecture explicitly requires independently. Check permission for the requested account on both paths before opening it; verify allowed and denied accounts as specified in B1.

2. **[P1][B2] Require login on warm-start account links** — `app/src/main/java/fixture/links/AccountDeepLinkRouter.java:23 (RIGHT)`  
   Evidence basis: `Static trace`. Constructing the router with `loggedIn == false` and calling `onNewIntent` still opens the account and returns true. Cold entry rejects that same session. Apply the login gate to warm entry and verify that both logged-out paths return false without opening data.

3. **[P2][B3] Reject malformed account identifiers before opening data** — `app/src/main/java/fixture/links/AccountDeepLinkRouter.java:18 (RIGHT)`  
   Evidence basis: `Static trace`. With a logged-in session, null or empty identifiers reach `Accounts.open`; warm entry has the same unchecked forwarding. This violates the explicit identifier-validation contract regardless of downstream behavior. Validate the supplied identifier’s canonical form on both paths and verify rejection without opening data, as specified in B3.