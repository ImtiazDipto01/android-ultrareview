# GitHub review format

This file is the single presentation authority for public review titles, labels, headings, threshold sections, inline findings, and AI disclosure.

## Acquisition failure or patch-only input

When [target-acquisition.md](target-acquisition.md) classifies the target as inaccessible, do not use the scored review template. Return:

```markdown
## Android UltraReview — not started: target inaccessible

**Review type: AI-assisted.**

### Access blocker

State which exact target facts or repository content could not be obtained and which routes were tried.

### Required next step

Name the smallest access action that would make the target reviewable.

### Outcome

No score or provider decision — the PR target was not acquired.
```

For user-requested or user-accepted patch-only inspection, use `## Limited patch inspection — no merge-confidence score`, state the inspected scope and missing context/evidence, and report only directly demonstrated preliminary issues. Do not emit a decision or `B`/`E` path. Never submit either output as a live provider review.

Place `**Review type: AI-assisted.**` directly below every public review or patch-inspection title. This preserves reviewer disclosure while keeping the product name in the title.

## Summary template

```markdown
## Android UltraReview — confidence X.X/5

**Review type: AI-assisted.**

Target: base `<ref>@<sha>`, merge base `<sha>`, head `<sha>`; merge result `<sha>` or `not used`.

Intent basis: accepted source(s), or `PR-authored description only; no independent product source was accessible`.

Coverage: `<N>/<N>` changed paths classified; `<N>` behavior-relevant paths inspected; `<N>` generated, binary, or metadata paths assessed; no material omissions.

### Summary

What the PR changes, the overall result, and the reason for the score.

### Previous findings

Re-review only: Fixed / Still open / No longer applies / Withdrawn / New.

### What's good

- Specific verified strengths.

### Blocking themes

- Independent P0–P2 root causes and consequences without duplicating inline text.

Use `### No blocking findings` when appropriate.

### Validation performed

- `PASS` / `FAIL` — executed command/job or observed runtime action, module/variant and device/API or scope when applicable, tested SHA, provenance, and result.

If no qualifying check was executed or directly observed, say so. Put each required `NOT RUN` check and its reason in the summary and applicable `B` item; do not present it as performed evidence.

### Non-blocking notes

- Optional; at most three high-value observations that do not affect the score, decision, blocking themes, or `B`/`E` paths.

### Path to 4.5/5

Stable `B` items when below 4.5.

### Proposed decision — not submitted

**REQUEST CHANGES**, **COMMENT**, or **APPROVE**.

### Draft inline findings

Unsubmitted actionable findings only.
```

Replace the last decision heading with `### Decision` only after successful provider submission. Omit `### Draft inline findings` when no actionable inline finding exists or after those findings were successfully submitted. When exact-target validation is the sole baseline gap, use `## Android UltraReview — confidence 4.0/5 (validation-limited)` and state that no source change is requested.

Use exactly one threshold section:

- below 4.5: `Path to 4.5/5`, rendering the canonical `B` schema and statuses from [scoring-and-severity.md](scoring-and-severity.md);
- at 4.5: `Path to 5.0/5 — optional, non-blocking`, rendering only the locked `E` items from [five-point-evidence.md](five-point-evidence.md); or
- at 5.0: `5.0 evidence — complete`, briefly naming covered and justified `N/A` gates.

At 4.5, say plainly that the PR is approved and the `E` items are optional. Do not reproduce the lock rules in the review.

Omit `### Non-blocking notes` when there is nothing worth preserving. Never use it as an overflow area for speculative findings, mandatory work, or a second path to a higher score.

For snapshot mode, place this visible line directly below the AI-assistance disclosure, substituting the exact reason when useful:

```markdown
**Review mode: Snapshot — exact refs were reviewed, but live currentness could not be refreshed; not submitted.**
```

Then render the standard target tuple and draft template. Do not imply draft findings were posted. Use [worked-example.md](worked-example.md) only for calibration.

## Inline findings

Each actionable inline finding contains:

1. an imperative title with severity and stable ID, such as `[P1][B1]`;
2. a compact evidence-basis tag: `Static trace`, `Executed test`, `Runtime reproduction`, `CI evidence`, or `Contract/configuration evidence` (combine only when genuinely needed);
3. the concrete trigger or reproduction path;
4. the observable consequence;
5. the violated requirement or invariant when non-obvious;
6. the smallest credible fix direction without prescribing an unnecessary rewrite; and
7. focused regression evidence when the repository can reasonably provide it.

Example:

```markdown
[P1][B1] Ignore the stale lookup after manual navigation

Evidence basis: `Static trace`.

If the user leaves while the location lookup is pending, its late result still replaces the manually selected destination. Cancel or identify the request so late success and failure cannot mutate the new route, and add a deterministic coroutine test that completes the old lookup after navigation.
```

Anchor to the smallest relevant changed-line range on the reviewed commit. Use `RIGHT` for added/modified code and `LEFT` for deleted behavior. Keep one root cause per comment. Publish only findings that passed the falsification pass in [context-and-workflow.md](context-and-workflow.md). Missing files/scope, cross-cutting gaps, positives, questions, and validation limits belong in the summary. If no accurate changed-line anchor exists, keep the finding in the summary with path and symbol.

Use the same compact evidence-basis tag for a summary-only P0–P2 finding. Validation gaps, infrastructure failures, merge conflicts, and unresolved product questions are path or summary items rather than findings and do not need a fabricated finding-basis tag.

For every unsubmitted review that has actionable findings, list them under `### Draft inline findings` in this form:

```markdown
1. **[P1][B1] Short title** — `feature/src/main/.../FeatureViewModel.kt:42 (RIGHT)`
   Evidence basis: `Static trace`. Trigger, consequence, invariant, smallest fix direction, and focused regression evidence.
```

Do not call a requested-change disposition permission to modify the contributor's branch. `REQUEST_CHANGES` is a provider review event; edits, commits, and pushes require separate authorization.

## Non-blocking boundary

Keep these outside findings, blocking themes, score reductions, and `B`/`E` paths unless a concrete changed trigger produces a P0–P2 consequence:

- formatting, naming, imports, package layout, and personal architecture/framework preferences;
- newest-API adoption, Compose/View migration, modularization, DI replacement, or broad rewrites without a correctness need;
- speculative performance, blanket “best practice” enforcement, coverage percentages, and unrelated legacy debt;
- unresolved product questions without authoritative answers; and
- new testing/tooling frameworks, screenshots, recordings, exhaustive device/API/OEM matrices, unsupported languages/form factors, or optional polish pursued only for a higher score.

At most three high-value observations may appear under `### Non-blocking notes`. They must remain explicitly optional and cannot change the score, decision, blocking themes, or stable paths.

## Posting behavior

When the user authorizes a live GitHub review:

1. validate every comment and target identity before the first write;
2. submit one batched review pinned to the inspected head;
3. choose the event from the canonical decision mapping and actual authority/capability;
4. keep optional `E` work non-blocking;
5. verify the created review's URL, state, SHA, body, and comment count; and
6. report the result without implying the PR was merged or the contributor branch changed.

Use [github-api.md](github-api.md) for pagination, commit pinning, ambiguous-write handling, and verification. If the authenticated author cannot self-approve or only comment authority exists, submit `COMMENT` and state the recommended disposition in the body.
