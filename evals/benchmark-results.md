# Benchmark results

## Harness integrity

| Check | Result | Evidence |
|---|---|---|
| Deterministic materialization | PASS | `python3 scripts/materialize_benchmarks.py --output <new-empty-dir>` reproduced every pinned SHA on 2026-09-22. |
| Three repositories / nine commits | PASS | The generated `resolved-manifest.json` contained three clean detached repositories and nine verified commits. |
| Repaired fixture contract checks | PASS | The three repaired Java fixtures compiled with `javac`; their separate baseline and excellence checks exited successfully. This checks fixture quality only and is not Android build or reviewer evidence. |
| Network or fixture-code execution | Not performed by design | The materializer invokes Git only. |

## Blind behavioral runs

No post-update blind reviewer run has been recorded yet. Do not infer 4.5/5 or 5.0/5 behavior from harness integrity.

| Fixture | Reviewer/settings | Defective | Repaired, no evidence | Baseline evidence | Locked optional evidence | Path stable | Raw result |
|---|---|---:|---:|---:|---:|---|---|
| Profile setup | Pending | — | — | — | — | — | — |
| Order upload | Pending | — | — | — | — | — | — |
| Account deep link | Pending | — | — | — | — | — | — |
