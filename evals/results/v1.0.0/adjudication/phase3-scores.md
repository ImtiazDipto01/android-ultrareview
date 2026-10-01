**Both fixtures adjudicate to `4.5/5`; H’s E1 is `VALID GAP` for each. Both remain approved, with every B item Done.**

The isolated policy requires direct evidence for material acceptance outcomes, focused regression protection, and the core integration contract. Covering every repaired defect does not automatically cover every applicable 5.0 gate. Both production classes are newly added in their base-to-head diffs, so the disputed forwarding behavior is part of the changed scope. See the [proportional gates](../../../../references/five-point-evidence.md).

The supplied manifest and four relevant raw logs qualify as traceable exact-target evidence. Their checksums match; compilation, debug assembly, and both focused tasks succeeded. Those results establish execution of the assertions actually present.

**Profile setup — `4.5/5`; E1: `VALID GAP`**

The explicit contract is to persist the **selected city** before completion. The [executed checks](../../../fixtures/profile-setup/repaired/app/src/main/java/fixture/profile/FixtureCheck.java) assert ordering, recovery, and save count, but never compare the repository’s received city with the submitted city.

| Gate | Adjudication |
|---|---|
| Product proof | **Gap:** successful completion after a save is proven; saving the selected value is not directly asserted. |
| Regression protection | **Gap:** callbacks ignore the city’s value. The reentrant check forwards its received argument into a suppressed nested submission, which does not verify identity. |
| Failure and recovery | **Covered:** failed save leaves completion/navigation false; retry succeeds; denial clears locating state and manual submission succeeds. |
| Lifecycle, process, concurrency | **Covered within scope:** state is checked inside saving, and reentrant submission produces one save with successful outer completion. |
| Critical persistence and integration | **Gap:** the repository receives the authoritative data to persist. Correct payload identity is a core contract alongside persistence ordering. |
| Triggered platform quality | **Covered within scope:** the modeled denial/manual-entry transition is exercised. Additional device or UI requirements are not triggered. |

H’s successful-submission value assertion is a finite, applicable check using the existing seam. The implementation visibly passes `city` correctly, but inspection alone does not satisfy this behavioral evidence gate.

**Order upload — `4.5/5`; E1: `VALID GAP`**

The contract concerns uploading the **requested order** with stable retry identity. The [executed checks](../../../fixtures/order-upload/repaired/app/src/main/java/fixture/orders/FixtureCheck.java) capture the gateway’s second argument, `key`; they never assert its first argument, `order`.

| Gate | Adjudication |
|---|---|
| Product proof | **Gap:** expected key, retry, and success are proven; requested-order identity at upload is not. |
| Regression protection | **Gap:** both attempt callbacks ignore `order`; the redelivery check observes upload count. Neither protects the requested-order outcome. |
| Failure and recovery | **Covered:** transient failure returns `RETRY`, recreated retry succeeds, and both cancellation cases return `CANCELLED`. |
| Lifecycle, process, concurrency | **Covered within scope:** retry and acknowledged redelivery use recreated workers sharing the accepted ledger boundary. |
| Critical persistence and integration | **Gap:** a correct key does not establish which order was uploaded. Uploading the requested order before acknowledging it is a high-impact core integration invariant. |
| Triggered platform quality | **Covered within scope:** modeled cancellation and background-delivery outcomes are exercised. Physical ledger durability and scheduler integration remain outside this fixture’s implementation scope. |

H’s assertion across the initial attempt and recreated retry directly addresses that contract. It requires no additional failure family or platform matrix.

**Reviewer rubric correction:** I should change gates 1, 2, and 5 to **Gap** for each fixture and adopt H’s existing optional E1 conditions. H needs no substantive correction. The evidence package’s “merge-readiness” label neither prevents nor establishes 5.0; actual assertion coverage controls.

No fixture code was executed or files modified, and no later-phase evidence was accessed or inferred.
