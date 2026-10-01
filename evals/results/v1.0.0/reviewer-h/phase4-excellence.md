# Profile setup

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/profile-setup`  
Base: `base@4a1359633c723bd539a81664038da19c9783c4c7`  
Merge base: `4a1359633c723bd539a81664038da19c9783c4c7`  
Head: `3ec59ede43c81b313208100d52872340692f4b39`  
Merge result: not used.

Intent basis: the request, accepted feature/architecture documents, and adjudicated profile correction.

Coverage: **2/2 changed paths previously inspected; no omissions.** The unchanged code assessment was carried forward without rescanning. Target identity and clean working-tree state were confirmed.

### Summary

The new evidence satisfies the exact locked E1 condition. All B items remain Done, all applicable proportional gates are covered, and the policy requires **5.0/5**. Approval remains unchanged.

### Evidence provenance and validation performed

Accepted as **traceable supplied evidence**, not reviewer-executed testing.

The optional evidence report and raw log identify the exact head above, clean detached state before and after execution, and JBR `17.0.14+1-1367.22-nomod`.

- **PASS — compilation:** Recorded `javac` command compiles the exact tracked `ProfileSetupController.java` together with the source-external `ProfileRepositoryBoundaryCheck.java`; exit **0**.
- **PASS — execution:** Recorded `java` command runs `fixture.profile.ProfileRepositoryBoundaryCheck`; exit **0**.
- The inspected checker calls `submit("Dhaka")`, captures repository arguments, and asserts `savedCities.equals(List.of("Dhaka"))`. It also asserts successful completion/navigation.
- SHA-256 checksums for both the retained checker source and `profile-setup-optional-e1.raw.log` match `optional-excellence-checksums.txt`.

Previously accepted phase-3 build and regression evidence remains applicable to this unchanged head.

### Previous findings and stable path resolution

| ID | Resolution | Status |
|---|---|---|
| B1 — persistence ordering and failure retry | Fixed; phase-3 evidence retained | **Done** |
| B2 — in-flight duplicate suppression | Fixed; phase-3 evidence retained | **Done** |
| B3 — permission-denial recovery | Fixed; phase-3 evidence retained | **Done** |
| B4 — exact-target compilation and baseline execution | Fixed; phase-3 evidence retained | **Done** |
| E1 — selected-city repository-boundary proof | New evidence satisfies the unchanged condition: **“The captured city equals the city supplied to `submit`, unchanged.”** | **Done** |

**Still open:** None.  
**Withdrawn:** Only B2’s adjudicated post-success-repeat clause and associated evidence requirement.  
**New:** None.

### No blocking findings

No code defect or required evidence gap remains.

### 5.0 evidence — complete

| Proportional gate | Final assessment |
|---|---|
| Product proof | **Covered:** retained behavioral evidence plus executed selected-city value assertion. |
| Regression protection | **Covered:** focused automated checks protect all material modeled outcomes. |
| Failure and recovery | **Covered:** failed save, retry, and denial recovery. |
| Lifecycle, process, and concurrency | **Covered:** modeled ordering and in-flight reentrant submission. |
| Critical persistence and integration | **Covered:** save-before-completion ordering and exact repository argument. |
| Triggered platform quality | **Covered:** modeled denial/manual-entry boundary; no additional platform subcheck is triggered. |

### Confidence, limitations, and correction continuity

Confidence is high within the modeled coordinator/repository contract. Evidence establishes interface behavior, not physical-storage durability or device-level UI execution. The adjudicated withdrawal remains intact. E1 was neither expanded nor replaced, and no further optional condition is introduced.

### Proposed decision — not submitted

**APPROVE.** Score advances from 4.5 to **5.0/5**; approval is unchanged.

---

# Order upload

## Android UltraReview — confidence 5.0/5

**Review type: AI-assisted.**

**Review mode: Snapshot — exact local target confirmed; not submitted.**

Target: `fixtures/order-upload`  
Base: `base@8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Merge base: `8e2f382cf95ca5a4f4e9fe899c4c006bf1ad66f8`  
Head: `7ddac75b89545a0fd8fb6b6332c8724cfda080ce`  
Merge result: not used.

Intent basis: the request, accepted feature/architecture documents, and adjudication retaining `CancellationException` within B3.

Coverage: **2/2 changed paths previously inspected; no omissions.** The unchanged code assessment was carried forward without rescanning. Target identity and clean working-tree state were confirmed.

### Summary

The new checker proves the requested order identifier reaches the gateway unchanged on both the initial attempt and recreated-worker retry. This closes the exact locked E1. All B items remain Done, requiring **5.0/5** with unchanged approval.

### Evidence provenance and validation performed

Accepted as **traceable supplied evidence**, not reviewer-executed testing.

The optional evidence report and raw log identify the exact head above, clean detached state before and after execution, and JBR `17.0.14+1-1367.22-nomod`.

- **PASS — compilation:** Recorded `javac` command compiles the exact tracked `OrderUploadWorker.java` together with the source-external `OrderGatewayBoundaryCheck.java`; exit **0**.
- **PASS — execution:** Recorded `java` command runs `fixture.orders.OrderGatewayBoundaryCheck`; exit **0**.
- The inspected checker captures the first gateway order argument, throws a transient failure, and asserts `RETRY`. It creates another worker sharing the ledger, retries, and asserts `SUCCESS`.
- It then asserts `receivedOrderIds.equals(List.of("order-42", "order-42"))`.
- SHA-256 checksums for both the retained checker source and `order-upload-optional-e1.raw.log` match the manifest.

Previously accepted phase-3 build, retry, acknowledgement, and cancellation evidence remains applicable.

### Previous findings and stable path resolution

| ID | Resolution | Status |
|---|---|---|
| B1 — stable key across retry and worker recreation | Fixed; phase-3 evidence retained | **Done** |
| B2 — acknowledged redelivery suppression | Fixed; phase-3 evidence retained | **Done** |
| B3 — cancellation preservation | Fixed; interruption and `CancellationException` evidence retained | **Done** |
| B4 — exact-target compilation and baseline execution | Fixed; phase-3 evidence retained | **Done** |
| E1 — gateway order-identity proof | New evidence satisfies the unchanged condition: **“Both captured order identifiers equal the requested order identifier, unchanged.”** | **Done** |

**Still open:** None.  
**Withdrawn:** No supported B condition or E item.  
**New:** None.

### No blocking findings

No code defect or required evidence gap remains.

### 5.0 evidence — complete

| Proportional gate | Final assessment |
|---|---|
| Product proof | **Covered:** retained delivery outcomes plus exact gateway order-identity assertions. |
| Regression protection | **Covered:** focused automated checks protect keys, acknowledgement, cancellation, and order forwarding. |
| Failure and recovery | **Covered:** transient retry, interruption, explicit cancellation, and acknowledged redelivery. |
| Lifecycle, process, and concurrency | **Covered:** retry and redelivery through recreated workers sharing the supplied ledger. |
| Critical persistence and integration | **Covered:** stable operation identity, acknowledgement handling, and unchanged gateway order arguments. |
| Triggered platform quality | **Covered:** modeled background-delivery and cancellation boundaries; no additional platform subcheck is triggered. |

### Confidence, limitations, and correction continuity

Confidence is high within the worker/gateway/ledger contract. Ledger durability remains an accepted interface guarantee; these checks do not claim physical process-kill or real-server validation.

The adjudicated `CancellationException` case remains part of B3. E1 closes without adding cancellation categories, concurrent-worker requirements, or crash-window protocols. No further optional condition is introduced.

### Proposed decision — not submitted

**APPROVE.** Score advances from 4.5 to **5.0/5**; approval is unchanged.

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

Intent basis: the request and accepted feature/architecture documents.

Coverage: **2/2 changed paths previously inspected; no omissions.** Target identity and clean working-tree state were confirmed. No unchanged code was rescanned.

### Summary

**No state change.** The optional packet supplies no new account evidence, and no E item existed. The phase-3 **5.0/5 / APPROVE** assessment is carried forward.

### Evidence provenance and validation performed

No new account validation was supplied or performed. Previously accepted traceable evidence remains applicable to this exact head:

- Successful debug assembly, fixture compilation, and baseline execution.
- Successful focused authorization, identifier, and exact-account forwarding checks.
- Successful source-external empty/blank boundary checks.
- Previously verified retained-log checksums and exact-target provenance.

### Previous findings and stable path resolution

| ID | Resolution | Status |
|---|---|---|
| B1 — account-level authorization | Fixed; evidence unchanged | **Done** |
| B2 — cold/warm login enforcement | Fixed; evidence unchanged | **Done** |
| B3 — invalid-identifier rejection | Fixed; evidence unchanged | **Done** |
| B4 — exact-target compilation and baseline execution | Fixed; evidence unchanged | **Done** |

**Still open:** None.  
**Withdrawn:** None.  
**New:** None.  
**E ledger:** No items; none added.

### No blocking findings

The prior merge-ready assessment remains unchanged.

### 5.0 evidence — complete

| Proportional gate | Unchanged assessment |
|---|---|
| Product proof | **Covered:** login, authorization, invalid-input rejection, and exact permitted-account opening. |
| Regression protection | **Covered:** focused fixture checks and automated boundary checker. |
| Failure and recovery | **Covered:** denied/logged-out access and malformed, absent, empty, and blank input. |
| Lifecycle, process, and concurrency | **N/A:** synchronous router with fixed constructor state; both entry methods are exercised. |
| Critical persistence and integration | **Covered:** exact authorized identifiers and successful debug build. |
| Triggered platform quality | **Covered:** modeled authentication, authorization, and identifier trust boundary. |

### Confidence and limitations

Confidence remains high for the modeled router boundary. External URI parsing, deployed backend behavior, and device-level App Link integration remain outside the established scope. No new condition or evidence requirement is introduced.

### Proposed decision — not submitted

**APPROVE — unchanged, 5.0/5.**

---

Both optional logs and both retained checker-source checksums matched their manifest. No application, fixture, or checker code was executed during this review. No files or repository state were modified.