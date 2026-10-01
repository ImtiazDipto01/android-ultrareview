# Reproducible behavioral benchmark

These fixtures test review behavior and convergence; they are not product templates or Android architecture policy. The materializer creates three ordinary Git repositories with deterministic base, defective, and repaired commits. It never executes fixture code, Gradle, or network operations.

## Materialize and verify

From the skill directory, run:

```bash
python3 scripts/materialize_benchmarks.py --output /new/empty/directory
```

The command fails on any SHA mismatch and writes `resolved-manifest.json` with exact repository and request paths plus refs. Each repository is clean and detached at `repaired`; the immutable `base`, `defective`, and `repaired` tags identify its states. The request remains outside the repository as provider-style intent and is not part of the reviewed diff.

The repaired fixtures expose `:app:fixtureBaselineCheck` and `:app:fixtureExcellenceCheck` alongside the Android build. They are candidate focused evidence only when genuinely executed on the pinned target with adequate provenance. Their presence or a Java-only self-check does not itself earn 4.5/5 or 5.0/5, replace a relevant Android build, or prove every triggered gate.

## Blind review protocol

Use the same explicitly named reviewer model and reasoning setting for every run. When the host does not expose an internal build identifier, record that limitation rather than inventing one. Give the reviewer only the skill, one materialized repository, the request text, and the exact state/evidence currently under review—never this guide, `scenarios.md`, expected scores, prior results, conversation history, or a suspected defect.

Prefer a physically separate evaluator bundle containing `SKILL.md` and its required policy references but excluding this guide, `scenarios.md`, `benchmark-results.md`, prior reviewer output, and the optional worked example. Record a deterministic content-manifest digest and enumerate the files the reviewer actually loaded. A prompt-only prohibition is a weaker fallback and must be disclosed as such.

For each fixture:

1. Review `base..defective` with no qualifying build/test evidence.
2. Re-review `base..repaired` with the earlier draft but no qualifying exact-target evidence.
3. Re-review the same repaired SHA after supplying genuine baseline build/test evidence tied to that SHA.
4. Re-review the same repaired SHA after supplying the previously locked optional evidence.

Run two blind reviewers per state and a third only when the first two disagree on an exact score, decision, admitted finding/root-cause grouping, threshold-path state, or false blocker. Do not label an integrity-only materializer run as Android build evidence or a model result. A real 4.5/5 or 5.0/5 benchmark result requires the reviewer output plus the genuine evidence it evaluated. Retain the exact command output or linked test report behind an evidence summary; a bare summary is not a self-contained benchmark record.

## Recording results

Record model/version, reasoning setting, skill tree hash, fixture SHAs, evidence provenance, review text, score, decision, stable IDs, false blockers, and whether the locked path changed. Use [benchmark-results.md](benchmark-results.md) for the public summary and retain raw outputs separately when publishing measurements.
