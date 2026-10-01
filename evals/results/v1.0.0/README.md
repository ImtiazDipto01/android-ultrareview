# Android UltraReview v1.0.0 calibration record

This directory contains the public, self-contained record supporting the v1.0.0 behavioral calibration. Three deterministic synthetic Android repositories were reviewed longitudinally by two isolated Codex CLI reviewers, with independent adjudication wherever the reviewers disagreed on score or pass-condition scope.

## Evaluator configuration

- Codex CLI `0.159.2`
- model `gpt-6-astra`
- reasoning effort `high`
- internal model build identifier: unavailable from the host
- isolated evaluator bundle digest: `e3772db1964652621ed4603f2ecc4c12b695b86637f8e336ad2d4dbc2eaed3fe`
- evaluation date: 2026-10-01

The evaluator bundle physically excluded `evals/benchmark.md`, `evals/benchmark-results.md`, `evals/scenarios.md`, prior results, and the worked example. Reviewers received only the isolated skill policy, fixture repository, external request, and evidence appropriate to the current phase.

## State progression

| Fixture | Reviewer | Defective | Repaired, no execution evidence | Merge-readiness evidence | Optional excellence evidence |
|---|---|---:|---:|---:|---:|
| Profile setup | H | 2.5 / request changes | 4.0 / comment | 4.5 / approve | 5.0 / approve |
| Profile setup | I | 2.5 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve |
| Order upload | H | 2.0 / request changes | 4.0 / comment | 4.5 / approve | 5.0 / approve |
| Order upload | I | 2.5 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve |
| Account deep link | H | 2.0 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve |
| Account deep link | I | 2.0 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve |

The phase-3 profile/order score disagreement was adjudicated to 4.5: both boundary-value E1 items were valid, finite excellence gaps. The phase-4 evidence then closed those exact locked items. Reviewer I had already awarded 5.0 and correctly preserved its empty E ledger when the corroborating packet arrived.

## Important adjudications

- The profile condition forbidding another submission after successful completion was withdrawn as over-broad. The supported in-flight duplicate-suppression condition remained under the same B ID.
- The order `CancellationException` condition was retained because the modeled contract says cancellation must not become an ordinary retry. The repaired fixture and its executed regression check were updated accordingly.
- The defective order score disagreement was adjudicated to 2.0 because two independent P1 defects compromised central upload guarantees; the reviewers otherwise agreed on findings, root-cause grouping, decision, and bounded outcomes.

See [`adjudication/`](adjudication/) for the full reasoning.

## Evidence

The exact repaired targets passed real Android debug assembly and focused Java behavior tasks under Gradle 9.5.0, AGP 9.3.2, JDK 17.0.14, and Android SDK 36 on macOS arm64. Source-external checks closed the missing account identifier cases and the two locked optional boundary-value items. Raw task output, checker sources, and SHA-256 manifests are in [`evidence/`](evidence/).

Local absolute paths in published logs and reports were replaced with `<calibration-workspace>` and `<jdk17>`. The published checksum manifests cover these sanitized artifacts.

## Limitations

These are three small synthetic fixtures, not a claim that every Android PR or every model run will receive an identical score. The benchmark demonstrates bounded findings, correction of false blockers, exact-target evidence handling, stable re-review paths, attainable 4.5 outcomes, and convergence to 5.0. It does not replace evaluation on varied real-world repositories.
