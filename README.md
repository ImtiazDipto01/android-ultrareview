<div align="center">

# Android UltraReview

### A rigorous, evidence-based Android pull-request reviewer for coding agents

Understand the feature. Respect the repository. Review the exact target. Give developers a finite path to merge.

[![CI](https://github.com/ImtiazDipto01/android-ultrareview/actions/workflows/ci.yml/badge.svg)](https://github.com/ImtiazDipto01/android-ultrareview/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/ImtiazDipto01/android-ultrareview?display_name=tag&sort=semver)](https://github.com/ImtiazDipto01/android-ultrareview/releases/latest)
[![License](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](LICENSE)
[![Agent Skills](https://img.shields.io/badge/Agent%20Skills-compatible-6f42c1)](https://agentskills.io)
[![Android](https://img.shields.io/badge/Android-PR%20review-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)

[Install](#install-in-60-seconds) · [How it reviews](#the-review-in-eight-phases) · [Scoring](#a-strict-but-attainable-score) · [Contribute](CONTRIBUTING.md)

</div>

Android UltraReview is an open, portable [Agent Skill](https://agentskills.io) for reviewing and re-reviewing Android pull requests. It combines product intent, project-specific architecture, Android platform behavior, and exact-target build or runtime evidence into an actionable PR review.

It is strict about demonstrated defects and honest evidence. It is deliberately not dogmatic about Compose vs. Views, MVVM vs. MVI, DI frameworks, Clean Architecture, modularization, or adopting the newest toolchain.

## What you get

- Concrete P0–P2 findings with tight inline anchors and a trigger-to-consequence explanation.
- A discrete `0.5/5`–`5.0/5` merge-confidence score.
- A finite, stable path to merge readiness at `4.5/5`.
- An attainable, evidence-based path to `5.0/5`—no rarity quota and no moving goalposts.
- Feature-level review grounded in PR descriptions, plans, PRDs, tickets, architecture docs, investigations, and repository rules.
- Android-aware review of lifecycle, process death, coroutines and Flow, Compose and Views, background work, storage, security, Gradle, release behavior, and more—only when the diff triggers those concerns.
- Review and re-review continuity, including stable `B` and `E` IDs.
- Safe defaults: draft-only review unless the user separately authorizes posting a provider review.

## Install in 60 seconds

Requirements: Node.js 22 or newer, Git, and at least one supported coding agent.

### Codex

```bash
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent codex
```

Installs to `~/.agents/skills/android-ultrareview`.

### Claude Code

```bash
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent claude
```

Installs to `~/.claude/skills/android-ultrareview`.

### Cursor

```bash
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent cursor
```

Installs to `~/.cursor/skills/android-ultrareview` so it can also be synced for Cursor Cloud Agents when that feature is enabled.

### All three

```bash
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent all
```

This installs one shared local Agent Skills copy for Codex and Cursor, plus one Claude Code copy. It intentionally avoids registering duplicate copies in Cursor. Cursor Cloud sync copies only `~/.cursor/skills`; use the individual Cursor command when that sync matters.

> These commands pin the stable `v1.0.0` tag. `npx` executes the installer from this GitHub repository; review [`bin/install.mjs`](bin/install.mjs) first if your environment requires source approval. The installer has no runtime dependencies and does not modify shell configuration.

### Install for one repository

Run this from the repository root:

```bash
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent codex --scope project
```

Or name the repository explicitly:

```bash
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 \
  --agent cursor \
  --scope project \
  --project /path/to/android-project
```

Project installs use the agent's official project directory, such as `.agents/skills`, `.claude/skills`, or `.cursor/skills`.

### Preview, update, or replace

```bash
# See the destination without writing anything
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent all --dry-run

# Replace an existing installation
npx --yes github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent all --force
```

The installer refuses to overwrite by default. `--force` preserves the previous directory beside the new installation as a timestamped backup.

| Option | Meaning |
|---|---|
| `--agent codex\|claude\|cursor\|all` | Required installation target. |
| `--scope user\|project` | User-wide or repository-local install; defaults to `user`. |
| `--project <path>` | Project root; defaults to the current directory. |
| `--dry-run` | Print destinations without writing files. |
| `--force` | Replace an existing copy and retain a backup. |
| `--help` | Show CLI help. |

### Host compatibility

| Host | User install | Project install | Invoke |
|---|---|---|---|
| Codex | `~/.agents/skills/android-ultrareview` | `.agents/skills/android-ultrareview` | `$android-ultrareview` |
| Claude Code | `~/.claude/skills/android-ultrareview` | `.claude/skills/android-ultrareview` | `/android-ultrareview` |
| Cursor | `~/.cursor/skills/android-ultrareview` | `.cursor/skills/android-ultrareview` | `/android-ultrareview` |

The locations follow the current [Codex skill discovery](https://developers.openai.com/codex/skills), [Claude Code skill](https://docs.claude.com/en/docs/claude-code/skills), and [Cursor Agent Skills](https://cursor.com/docs/skills) documentation. Other hosts can use the repository as a standard `SKILL.md` bundle.

## Run your first review

Start the agent inside the target repository, then invoke the skill with a PR URL or an exact local target.

**Codex**

```text
$android-ultrareview Review https://github.com/example/app/pull/123
```

**Claude Code or Cursor**

```text
/android-ultrareview Review https://github.com/example/app/pull/123
```

Natural-language requests can also trigger the skill when the agent supports automatic skill selection:

```text
Review PR #123 with Android UltraReview. Draft the review only; do not post it.
```

For a follow-up:

```text
Re-review PR #123. Verify the previous B and E items against the new head and keep the published pass conditions stable.
```

A plain “review” request produces a draft. If you want comments submitted to GitHub, say so explicitly. Approval or request-changes decisions require separate authorization and must be allowed for the authenticated account.

Draft and snapshot review are provider-neutral. The bundled live-submission procedure is GitHub-specific; another provider needs an equivalent authorized integration.

## The review in eight phases

| Phase | What the reviewer does | Output |
|---|---|---|
| 1. Freeze | Acquires the exact base, merge base, head, and merge-result identity when available. | A review tied to one immutable target. |
| 2. Understand | Reads applicable `AGENTS.md`, `CLAUDE.md`, architecture docs, plans, PRDs, tickets, feature docs, and PR context. | A stated intent and architecture basis. |
| 3. Cover | Classifies every changed path and traces the smallest relevant impact radius. | A complete behavior-relevant coverage ledger. |
| 4. Apply lenses | Selects only Android domains actually triggered by the change. | Proportional platform and engineering analysis. |
| 5. Validate | Uses safe, repository-native exact-target builds, tests, CI, or runtime checks when available. | Explicit commands, environments, SHAs, and results. |
| 6. Falsify | Challenges every proposed finding, searches for guards or tests that defeat it, deduplicates root causes, and scores. | Concrete P0–P2 findings without speculative noise. |
| 7. Prove excellence | After the `4.5` baseline, evaluates the finite evidence gates for `5.0`. | Optional locked `E` items or a completed `5.0` case. |
| 8. Draft or submit | Produces the standard summary and inline comments; writes externally only when authorized. | A draft or one verified provider review. |

For the full contract, start with [`SKILL.md`](SKILL.md). Detailed policy lives in [`references/`](references/).

## A strict but attainable score

The score represents confidence in merging the exact reviewed target. It is not a grade for the developer.

| Score | Meaning |
|---|---|
| `0.5–2.5` | Catastrophic, severe, or multiple central failures remain. |
| `3.0` | An isolated P1 remains; most structure is sound. |
| `3.5` | No P1, but multiple material P2s or a major intent/evidence gap remain. |
| `4.0` | Close, but a P2 or material validation gap remains. |
| `4.5` | Merge-ready: no P0–P2 remains, acceptance criteria are met, and relevant exact-target checks pass. |
| `5.0` | The `4.5` baseline holds and every applicable proportional evidence gate is complete. |

At `4.5`, the PR is approved. Work listed on the path to `5.0` is optional and cannot justify a request-changes decision. A strong implementation can earn `5.0` on the first review or close a finite published path in later rounds.

## Android expertise without framework dogma

Android UltraReview can reason about:

- process/app lifecycle, state restoration, task and back-stack behavior;
- coroutines, cancellation, exception propagation, Flow, and lifecycle-aware collection;
- Compose recomposition, stability, snapshots, layout, and hybrid View interoperability;
- navigation, deep links, App Links, permissions, exported components, and IPC;
- background execution, WorkManager, foreground services, alarms, Doze, and retries;
- Room, DataStore, migrations, offline-first synchronization, and conflict resolution;
- Gradle, convention plugins, variants, R8, app bundles, baseline profiles, and CI;
- accessibility, localization, RTL, font scaling, large screens, and foldables;
- authentication, storage, WebView, network security, privacy, and SDK auditing; and
- API contracts, DI scopes, modular boundaries, tests, lint, and release operations.

These are review lenses, not universal rules. The target project's supported SDKs, architecture, dependencies, conventions, and product requirements remain authoritative.

## What it will not do

- Block a PR for personal style, naming, or architecture taste.
- Demand Compose, Clean Architecture, Hilt, modularization, or the newest Android APIs.
- Manufacture a low score when the PR target cannot be acquired; inaccessible targets receive no score.
- Call an unexecuted check “passing.”
- Treat an optional `5.0` improvement as required for merge.
- Change branches, edit contributor code, push commits, merge, or post a review without the corresponding user authorization.

## Package map

```text
android-ultrareview/
├── SKILL.md                     # Entrypoint and operating contract
├── agents/openai.yaml           # OpenAI UI metadata
├── references/                  # Review, scoring, Android, and provider policy
├── evals/                       # Scenarios, deterministic fixtures, result record
├── scripts/materialize_benchmarks.py
├── bin/install.mjs              # Zero-dependency multi-agent installer
└── test/                        # Installer and package tests
```

[`references/sources.md`](references/sources.md) records design lineage, Android research provenance, primary technical sources, and deliberate departures from framework dogma. Consulted Android skill collections informed domain coverage; their code and templates are not redistributed here.

## Evaluation status

The v1.0.0 calibration is complete. Two isolated Codex CLI reviewers evaluated three deterministic Android fixtures through defective, repaired-without-evidence, merge-readiness-evidence, and optional-excellence phases. Independent adjudicators resolved every score or pass-condition disagreement.

- Both reviewers produced validation-limited `4.0` results after static repair with no execution evidence.
- The run produced two independently reviewed `4.5` outcomes with finite optional paths.
- After exact-target excellence evidence, both reviewers converged to `5.0` for all three fixtures.
- Real Android debug builds and focused checks passed at every repaired SHA used for scoring.

The full reports, adjudications, raw logs, checker sources, checksums, model settings, and limitations are published in [`evals/results/v1.0.0/`](evals/results/v1.0.0/). These synthetic results demonstrate the contract's behavior; they do not promise identical scores for every real-world PR.

## Develop locally

```bash
git clone https://github.com/ImtiazDipto01/android-ultrareview.git
cd android-ultrareview

npm test
npm run pack:check

python3 scripts/materialize_benchmarks.py --output .tmp/benchmarks
```

To test a local install without touching an existing copy:

```bash
node bin/install.mjs --agent codex --scope project --project /tmp/sample-project --dry-run
```

See [`CONTRIBUTING.md`](CONTRIBUTING.md) before changing review policy, score semantics, or benchmark expectations.

## Open-source project files

- [`CONTRIBUTING.md`](CONTRIBUTING.md) — contribution workflow and policy-change expectations.
- [`SECURITY.md`](SECURITY.md) — private vulnerability reporting guidance.
- [`CODE_OF_CONDUCT.md`](CODE_OF_CONDUCT.md) — community participation expectations.
- [`CHANGELOG.md`](CHANGELOG.md) — release history.

## License

Licensed under the [Apache License 2.0](LICENSE).

Copyright 2026 Imtiaz Uddin Ahmed.

---

<div align="center">

Built for reviews that are demanding, explainable, and finishable.

</div>
