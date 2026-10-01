# Profile setup

## Android UltraReview — confidence 4.5/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/profile-setup`  
Base: `base@4a1359633c723bd539a81664038da19c9783c4c7`  
Merge base: `4a1359633c723bd539a81664038da19c9783c4c7`  
Head: `3ec59ede43c81b313208100d52872340692f4b39`  
Merge result: not used.

Intent basis: the request, accepted feature/architecture documents, and adjudicated profile correction.

Coverage: **2/2 changed paths previously inspected; no omissions.** Code assessment carried forward without rescanning. Exact head and clean working-tree state confirmed.

### Summary

All locked merge-readiness conditions now have qualifying evidence. **The PR is approved at 4.5/5.** One optional gap remains in direct regression evidence for the selected city’s value at the repository boundary.

Confidence is high within the modeled coordinator/repository contract. This review does not claim device-level UI or physical-storage validation.

### Evidence provenance

Accepted as **traceable supplied evidence**, not reviewer-executed testing. The runner report identifies this exact SHA, clean detached state before and after execution, macOS 27.0 arm64, Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, and SDK 36.

Both retained log checksums match `evidence/merge-readiness-checksums.txt`. The runner report supplies SHA binding and exit status; raw logs corroborate the underlying task execution and successful completion. APK and Gradle-distribution hashes were supplied, but those binaries were unavailable for independent checksum verification.

### Previous findings

| ID | Resolution | Path status | Qualifying behavior/evidence |
|---|---|---|---|
| B1 — prior P1 | **Fixed** | **Done** | Baseline assertions check incomplete state inside saving, incomplete state after failure, and successful retry followed by completion/navigation. |
| B2 — prior P2 | **Fixed** | **Done** | Reentrant submission occurs inside the repository callback; the executed check asserts one save and successful outer completion. |
| B3 — prior P2 | **Fixed** | **Done** | Baseline asserts denial clears `locating`, then manually submits successfully. |
| B4 | **Fixed** | **Done** | Exact-target fixture compilation, baseline execution, and debug assembly succeed. |

**Still open:** None.  
**Withdrawn:** Only B2’s adjudicated post-success-repeat clause and associated evidence requirement.  
**New:** None.

### What's good

The executed checks exercise the retained failure/retry, ordering, denial, and in-flight duplication requirements directly.

### No blocking findings

No actionable defect or merge-readiness evidence gap remains.

### Validation performed

- **PASS** — `:app:assembleDebug :app:fixtureBaselineCheck`, with `--rerun-tasks`; supplied exit **0**, **35/35** actionable tasks executed. Includes `:app:compileFixtureChecks`. Output: `evidence/logs/profile-setup-baseline.raw.log`.
- **PASS** — `:app:fixtureExcellenceCheck`, with `--rerun-tasks`; supplied exit **0**, **2/2** actionable tasks executed. Its reentrant-submission assertion closes B2. Output: `evidence/logs/profile-setup-excellence.raw.log`.

Both results apply to the exact head above. No fixture code was executed during this review.

### 5.0 gate assessment

The isolated policy enumerates six proportional gates; all were assessed.

| Gate | Assessment |
|---|---|
| Product proof | **Gap:** ordering and recovery are proven, but the executed repository callbacks do not assert the supplied city value. |
| Regression protection | **Gap:** the selected-city forwarding outcome lacks a focused value assertion. |
| Failure and recovery | **Covered:** failed save, retry, and permission-denial recovery. |
| Lifecycle, process, and concurrency | **Covered:** modeled save ordering and in-flight reentrant submission. Unrepresented lifecycle transitions are outside scope. |
| Critical persistence and integration | **Gap:** ordering is covered; direct proof of the selected city delivered to the repository remains missing. |
| Triggered platform quality | **Covered:** the modeled denial/manual-entry boundary is exercised. No additional UI, performance, or device-matrix requirement is triggered. |

### Path to 5.0/5 — optional, non-blocking

| ID | Gate and current gap | Minimum sufficient evidence | Objective pass condition | Status |
|---|---|---|---|---|
| E1 | Product proof, regression protection, and repository-boundary value proof | An executed focused automated check on the reviewed target that captures the argument received by `Repository.save` during a successful submission. Use the existing harness or an equivalent source-external checker, with exact-SHA, command, environment, exit status, and inspectable assertion/output provenance. | The captured city equals the city supplied to `submit`, unchanged. | **Open** |

This is the complete optional path for the current scope. B1’s locked ordering/retry requirement is satisfied and is not reopened.

### False-blocker audit

The post-completion duplicate clause remains withdrawn. No controller-recreation, multithreaded submission, physical persistence, or actual navigation-event-counter requirement is added. The task name supplied no scoring credit; its actual reentrant assertion satisfied B2.

### Proposed decision — not submitted

**APPROVE.** E1 is optional and does not affect approval.

---

# Order upload

## Android UltraReview — confidence 4.5/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/order-upload`  
Base: `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Merge base: `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Head: `7ddac75b89545a0fd8fb6b6332c8724cfda080ce`  
Merge result: not used.

Intent basis: the request, accepted feature/architecture documents, and adjudication retaining the `CancellationException` case within B3.

Coverage: **2/2 changed paths previously inspected; no omissions.** Code assessment carried forward without rescanning. Exact head and clean working-tree state confirmed.

### Summary

The supplied execution evidence closes every locked B condition, including both cancellation cases. **The PR is approved at 4.5/5.** One optional gap remains: the executed checks capture the idempotency key but do not assert the actual order identifier supplied to the gateway.

Confidence is high within the modeled worker/gateway/ledger contract. Physical ledger durability remains an accepted interface guarantee, not a newly claimed runtime result.

### Evidence provenance

Accepted as **traceable supplied evidence**. The runner identifies this exact SHA, clean detached state before and after execution, macOS 27.0 arm64, Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, and SDK 36.

Both version-2 log checksums match the manifest. The report binds the logs and exit statuses to this target; raw outputs corroborate successful compilation, task execution, and debug assembly. Unpublished APK and distribution binaries were not independently verified.

### Previous findings

| ID | Resolution | Path status | Qualifying behavior/evidence |
|---|---|---|---|
| B1 — prior P1 | **Fixed** | **Done** | Baseline captures keys across transient failure and a recreated worker, asserts two attempts, and verifies identical expected keys. |
| B2 — prior P1 | **Fixed** | **Done** | The additional check asserts successful acknowledged redelivery through a recreated worker and exactly one gateway upload. |
| B3 — prior P2 | **Fixed** | **Done** | Baseline separately asserts `CANCELLED` for interruption and `CancellationException`, while transient failure returns `RETRY`. |
| B4 | **Fixed** | **Done** | Exact-target fixture compilation, baseline execution, and debug assembly succeed. |

**Still open:** None.  
**Withdrawn:** No B item or supported cancellation condition.  
**New:** None.

The earlier erroneous narrowing of B3 remains retracted. `CancellationException` is retained and now has qualifying execution evidence.

### What's good

The evidence exercises worker recreation, stable retry identity, acknowledged redelivery, and distinct cancellation outcomes.

### No blocking findings

No actionable defect or merge-readiness evidence gap remains.

### Validation performed

- **PASS** — `:app:assembleDebug :app:fixtureBaselineCheck`, with `--rerun-tasks`; supplied exit **0**, **35/35** actionable tasks executed, including fixture compilation. Output: `evidence/logs/order-upload-baseline-v2.raw.log`.
- **PASS** — `:app:fixtureExcellenceCheck`, with `--rerun-tasks`; supplied exit **0**, **2/2** actionable tasks executed. Its acknowledged-redelivery assertion closes B2. Output: `evidence/logs/order-upload-excellence-v2.raw.log`.

Both results apply to the exact head above. No fixture code was executed during this review.

### 5.0 gate assessment

| Gate | Assessment |
|---|---|
| Product proof | **Gap:** retry identity and delivery outcomes are proven; gateway order-argument identity is not asserted. |
| Regression protection | **Gap:** existing assertions protect keys and call counts, but not the gateway’s order argument. |
| Failure and recovery | **Covered:** transient retry, interruption, explicit cancellation, and acknowledged redelivery. |
| Lifecycle, process, and concurrency | **Covered:** retry and redelivery through recreated workers sharing the supplied ledger. |
| Critical persistence and integration | **Gap:** acknowledgement and key contracts are covered; direct proof of the requested order reaching the gateway remains missing. |
| Triggered platform quality | **Covered:** modeled background-delivery and cancellation boundaries are exercised. No actual scheduler or additional platform matrix is required. |

### Path to 5.0/5 — optional, non-blocking

| ID | Gate and current gap | Minimum sufficient evidence | Objective pass condition | Status |
|---|---|---|---|---|
| E1 | Product proof, regression protection, and gateway-boundary identity proof | An executed focused automated check capturing the gateway’s `orderId` argument on the initial attempt and retry through a recreated worker. The existing harness or equivalent source-external checker may supply exact-SHA, command, environment, exit status, and inspectable assertion/output provenance. | Both captured order identifiers equal the requested order identifier, unchanged. | **Open** |

This is the complete optional path for the current scope. The already-satisfied key-stability and acknowledgement conditions remain unchanged.

### False-blocker audit

The adjudicated cancellation condition is preserved. No new cancellation family, permanent-failure taxonomy, concurrent-worker protocol, crash-window transaction requirement, or physical process-kill test is added. The additional task closes B2 because of its assertions, not its historical name.

### Proposed decision — not submitted

**APPROVE.** E1 is optional and does not affect approval.

---

# Account deep link

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/account-deeplink`  
Base: `base@a7f5c33bb149bbb01033230b40d35b2313f80c37`  
Merge base: `a7f5c33bb149bbb01033230b40d35b2313f80c37`  
Head: `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e`  
Merge result: not used.

Intent basis: the request and accepted feature/architecture documents. No account pass condition or adjudication changed.

Coverage: **2/2 changed paths previously inspected; no omissions.** Code assessment carried forward without rescanning. The newly supplied source-external boundary checker was read. Exact head and clean working-tree state confirmed.

### Summary

All locked B conditions now have qualifying evidence, including the previously missing empty/blank cases. The same executed assertions also cover every applicable proportional 5.0 gate. **The policy therefore requires 5.0/5 and approval; no additional E item is justified.**

Confidence is high for the modeled identifier-routing and authorization boundary. This does not claim verification of an external URI parser, deployed backend, or device-level App Link integration.

### Evidence provenance

Accepted as **traceable supplied evidence**. The runner identifies this exact SHA and clean detached state before and after execution, with macOS 27.0 arm64, Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, and SDK 36.

All three log checksums match the manifest. The external-checker log additionally records the exact target, JBR 17.0.14 runtime, `javac` and `java` commands, and separate compile/run exit statuses. Its supplied source directly asserts both handlers reject empty and whitespace-only identifiers without authorization or opening.

The checker source is not itself listed in the checksum manifest; its inspectable source and execution record provide the supplied provenance. Unpublished APK and distribution binaries were not independently verified.

### Previous findings

| ID | Resolution | Path status | Qualifying behavior/evidence |
|---|---|---|---|
| B1 — prior P1 | **Fixed** | **Done** | Executed checks cover denied cold/warm access and allowed cold/warm access, asserting the exact identifiers received by authorization and opening. |
| B2 — prior P1 | **Fixed** | **Done** | Baseline checks both logged-out entry points and asserts zero account opens. |
| B3 — prior P2 | **Fixed** | **Done** | Executed fixture checks cover malformed/null input and valid identifiers. The external checker adds empty/blank cases through both handlers with zero opens. |
| B4 | **Fixed** | **Done** | Exact-target fixture compilation, baseline execution, and debug assembly succeed. |

**Still open:** None.  
**Withdrawn:** None.  
**New:** None.

### What's good

The evidence checks the trust-boundary outcomes directly, including exact authorized-account forwarding and rejection before opening data.

### No blocking findings

No actionable defect or merge-readiness evidence gap remains.

### Validation performed

- **PASS** — `:app:assembleDebug :app:fixtureBaselineCheck`, with `--rerun-tasks`; supplied exit **0**, **35/35** actionable tasks executed. Output: `evidence/logs/account-deeplink-baseline.raw.log`.
- **PASS** — `:app:fixtureExcellenceCheck`, with `--rerun-tasks`; supplied exit **0**, **2/2** actionable tasks executed. Output: `evidence/logs/account-deeplink-excellence.raw.log`.
- **PASS** — Source-external `AccountIdentifierBoundaryCheck`: exact tracked router compiled with JBR 17, compile exit **0**, checker run exit **0**. Assertions cover empty and whitespace-only identifiers through both handlers. Output: `evidence/logs/account-deeplink-empty-blank-focused.raw.log`.

All results apply to the exact head above. No checker or fixture code was executed during this review.

### 5.0 evidence — complete

| Gate | Assessment |
|---|---|
| Product proof | **Covered:** login, account authorization, invalid-input rejection, and exact permitted-account opening have executed assertions. |
| Regression protection | **Covered:** focused automated fixture checks and the supplied automated boundary checker cover the material modeled outcomes. |
| Failure and recovery | **Covered:** denied authorization, logged-out access, and malformed/absent/empty/blank input are exercised. |
| Lifecycle, process, and concurrency | **N/A:** the modeled router is synchronous with fixed constructor state; no additional mutable session or restoration transition is represented. Both entry methods are exercised. |
| Critical persistence and integration | **Covered:** authorization and opening receive the exact canonical identifiers; debug compilation and assembly pass. No changed persistence or release-only contract requires additional proof. |
| Triggered platform quality | **Covered:** the modeled deep-link authentication, authorization, and identifier trust boundary has focused execution evidence. Broader URI/platform integration is outside this fixture’s contract. |

No E path is needed. This conclusion follows from the assertions’ coverage, not the task name or the evidence package’s “merge-readiness” label.

### False-blocker audit

No extra identifier grammar, live-session transition, logout/account-switch scenario, URI parser, device matrix, or new testing framework is required. The external automated checker qualifies without being committed into the reviewed repository.

### Proposed decision — not submitted

**APPROVE.**

---

All seven retained log checksums matched. No files or repository state were modified, and no builds, tests, fixture entry points, or application code were executed during this review.