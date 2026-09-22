# Attainable 5.0 confidence

`5.0/5` means the defined PR scope has unusually complete, direct evidence. It does not claim that bugs are impossible or require a rewrite, new framework, exhaustive device matrix, or reviewer-preferred architecture. First require the `4.5` baseline: no P0–P2 findings, material acceptance criteria met, and relevant build/tests passing for the exact target.

Classify each triggered gate below as `Covered`, `Gap`, or `N/A`, with one concrete reason. Award `5.0` whenever every applicable gate is covered and any locked `E` path is complete. If this is already true on the first review, award `5.0` immediately. A competent team must be able to close a finite published path with direct evidence; do not move the goalposts or impose a rarity quota.

Evidence closes a gate only when its provenance, exact target, scope, environment, and result are sufficient for the claimed risk. Reviewer-executed exact-target evidence and authoritative CI qualify when their underlying work is visible. Complete, credible contributor-supplied evidence may also qualify when it identifies the SHA or qualifying merge result, action/command/job, relevant variant/environment/device, outcome, and inspectable output or artifact. Incomplete screenshots or log excerpts and bare assertions do not close a gate by themselves. Apply the evidence trust tiers in [context-and-workflow.md](context-and-workflow.md) proportionally; do not require the reviewer personally to rerun equivalent trustworthy evidence.

## Proportional gates

1. **Product proof.** Every material acceptance row marked met has row-specific evidence on the reviewed target. Behavior requires an executed focused test, qualifying CI check, or observed emulator/device/runtime result that exercises it. A static criterion may use a compiler, lint, build, manifest/resource/schema/configuration, artifact, or contract check. Inspection alone does not prove behavior.
2. **Regression protection.** Every material changed behavior or repaired defect that the repository's existing test harness can reasonably exercise has a focused automated test of the authoritative outcome passing on the exact reviewed target. Existing tests count. A generic green suite, coverage percentage, or manual check does not replace focused protection. Mark `N/A` when no candidate is test-eligible; a new UI, screenshot, accessibility, benchmark, managed-device, or architecture-test framework is not required merely to create eligibility.
3. **Failure and recovery.** When the changed feature has a credible fallible path, cover the relevant denial, malformed input/data, error, cancellation, timeout, retry, offline, partial result, duplicate delivery, rollback, or recovery behavior. Cover the actual changed risks, not every theoretical combination or OEM.
4. **Lifecycle, process, and concurrency.** When async work, mutable shared state, navigation ownership, Android components, background execution, or ordering changes, cover relevant interruption, cancellation, repeated action, stale result, configuration change, background/foreground, process recreation, task/back-stack restoration, reboot/time change, worker retry, or race behavior. Synchronous/stateless changes are `N/A`.
5. **Critical persistence and integration.** When persisted data, migrations, network/API contracts, dependency/DI wiring, shared state, generated contracts, manifest components, build variants, or critical consumers change, prove only the high-impact invariants: no credible data loss/corruption, required compatibility, correct core contract, and correct critical consumer behavior. Release/minified proof applies when reflection, serialization, JNI, resources, consumer rules, or shrinking make debug-only evidence insufficient.
6. **Triggered platform quality.** Evaluate only subchecks activated by the diff and repository requirements:
   - material UI: verify the core affected flow on a representative supported device/window/API configuration;
   - localization/adaptive layout: check project-supported localizations and relevant font scale/window size; check RTL, fold posture, keyboard/D-pad, or another form factor only when supported, required, or plausibly affected;
   - changed interactive UI: check relevant semantics, labels/roles/state, traversal/focus, touch target, font scaling, contrast, or input behavior proportionally;
   - changed lifecycle, permissions, deep links, exported components, background work, or target-SDK behavior: verify the materially affected platform state and denial/recovery or compatibility boundary;
   - changed credentials, authentication, storage, sensitive data, telemetry, WebView/IPC, or trust boundaries: verify secure handling and project-approved consent, disclosure, minimization, validation, and redaction;
   - a credible changed hot path or build-performance claim: verify responsiveness, jank/ANR, memory, startup, size, repeated-I/O, or build-time/cache behavior with comparable evidence.

Project-standard tests or a focused runtime check may cover UI/platform behavior; screenshots, recordings, exhaustive API/device/OEM matrices, device farms, Macrobenchmark, Baseline Profiles, and unsupported languages/form factors are not universal requirements. Approved analytics/crash telemetry counts as direct evidence only when its build/version provenance covers the reviewed head or qualifying merge result and the affected path; it must also follow project policy and exclude secrets or prohibited sensitive values.

## Locked path to 5.0

When the score first reaches `4.5` with one or more gaps, publish `Path to 5.0/5 — optional, non-blocking` as the complete list for the current authorized scope. Use the smallest finite list; there is no fixed maximum or minimum.

| ID | Gate and current gap | Minimum sufficient evidence | Objective pass condition | Status |
|---|---|---|---|---|
| `E1` | named triggered risk and what is missing | outcome-based action; equivalent evidence allowed | binary observable condition | `Open` |

Assign stable `E1`, `E2`, and so on. Use only `Open`, `Done`, `N/A`, or `Withdrawn`. Keep an external block `Open` and name the unavailable evidence event, service, credential, device, hardware, or decision.

Combine gates in one item when the same action and objective pass condition close the same underlying gap; do not split work merely to create one item per gate.

Once published, the list is locked for unchanged scope:

- preserve IDs and written pass conditions;
- accept equivalent evidence that exercises the same risk;
- do not add, split, replace, or tighten items after fresh sampling;
- reopen the same ID only when later code touches its basis or concrete evidence disproves its pass condition; and
- when an authorized material scope change creates a new applicable gap, append the next unused `E` ID and state one sentence identifying the scope-change basis.

A new SHA, base update, reviewer preference, new Android/library release, or stricter interpretation is not a scope-change basis. A genuine P0–P2 belongs in the `B` baseline path, temporarily lowers the score, and freezes `E` evaluation until 4.5 is restored; the late-finding admission rule still applies.

Award `5.0` when the 4.5 baseline holds and every published `E` item is `Done`, justified `N/A`, or `Withdrawn`. If no `E` item is needed because all gates are covered, remaining at `4.5` is inconsistent—award `5.0`.
