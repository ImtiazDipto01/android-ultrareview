# Benchmark results

## Harness integrity

| Check | Result | Evidence |
|---|---|---|
| Deterministic materialization | PASS | `python3 scripts/materialize_benchmarks.py --output <new-empty-dir>` reproduced every pinned SHA on 2026-10-01. |
| Three repositories / nine commits | PASS | The generated `resolved-manifest.json` contained three clean detached repositories and nine verified commits. |
| Repaired fixture contract checks | PASS | All three exact repaired targets passed real Android debug assembly and focused behavior checks. Raw evidence is published under [`results/v1.0.0/evidence/`](results/v1.0.0/evidence/). |
| Materializer isolation | PASS | The materializer itself invokes Git only; Android execution was a separate, explicit calibration step. |

## Blind behavioral runs

| Fixture | Reviewer/settings | Defective | Repaired, no evidence | Baseline evidence | Locked optional evidence | Path stable | Raw result |
|---|---|---:|---:|---:|---:|---|---|
| Profile setup | H — Codex CLI 0.159.2, `gpt-6-astra`, high | 2.5 / request changes | 4.0 / comment | 4.5 / approve | 5.0 / approve | Yes; one over-broad clause explicitly withdrawn | [`reviewer-h`](results/v1.0.0/reviewer-h/) |
| Profile setup | I — Codex CLI 0.159.2, `gpt-6-astra`, high | 2.5 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve | Yes; correction adopted, empty E path preserved | [`reviewer-i`](results/v1.0.0/reviewer-i/) |
| Order upload | H — Codex CLI 0.159.2, `gpt-6-astra`, high | 2.0 / request changes | 4.0 / comment | 4.5 / approve | 5.0 / approve | Yes; cancellation scope corrected through adjudication | [`reviewer-h`](results/v1.0.0/reviewer-h/) |
| Order upload | I — Codex CLI 0.159.2, `gpt-6-astra`, high | 2.5 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve | Yes; retained B path closed | [`reviewer-i`](results/v1.0.0/reviewer-i/) |
| Account deep link | H — Codex CLI 0.159.2, `gpt-6-astra`, high | 2.0 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve | Yes; no E item required | [`reviewer-h`](results/v1.0.0/reviewer-h/) |
| Account deep link | I — Codex CLI 0.159.2, `gpt-6-astra`, high | 2.0 / request changes | 4.0 / comment | 5.0 / approve | 5.0 / approve | Yes; no E item required | [`reviewer-i`](results/v1.0.0/reviewer-i/) |

The independent phase-3 adjudicator selected 4.5 for profile and order because the exact boundary values reaching `Repository.save` and `Gateway.upload` were material integration outcomes not asserted by the merge-readiness packet. Those finite E1 checks then passed without changing the reviewed SHAs. The defective order score disagreement was adjudicated to 2.0. See the complete [v1.0.0 calibration record](results/v1.0.0/).

This benchmark uses three small synthetic repositories. It demonstrates the review contract's correction, evidence, scoring, and convergence behavior; it is not a universal guarantee for every repository or model run.
