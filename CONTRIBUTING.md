# Contributing to Android UltraReview

Thanks for helping make Android PR review more useful, fair, and evidence-driven.

## Before opening a change

1. Open or find an issue for material changes to scoring, authorization, review output, Android policy, or benchmark expectations.
2. Explain the concrete review failure the change addresses.
3. Prefer repository and Android primary sources over opinion or framework fashion.
4. Keep the skill generic. Project-specific architecture belongs in the reviewed project, not in this package.

Small documentation corrections and narrowly scoped bug fixes may go directly to a pull request.

## Development setup

Requirements: Node.js 18+, Python 3, and Git.

```bash
npm test
npm run pack:check
python3 scripts/materialize_benchmarks.py --output .tmp/benchmarks
```

The benchmark materializer creates detached Git repositories in the output directory and verifies their pinned commit identities. It does not run the fixture code.

## Pull-request expectations

- Describe the user-visible review behavior before and after the change.
- Add or update a focused test when changing installer behavior.
- Update scenarios or fixtures when changing a score gate or re-review rule.
- Preserve the authorization boundary: read-only review is the default; external writes require explicit authority.
- Preserve attainable scoring: `4.5` is merge-ready, and `5.0` must remain finite and evidence-based.
- Do not report benchmark or blind-run results that were not actually observed.
- Keep relative links valid and avoid copied third-party text or code without compatible attribution.

## Review-policy changes

Changes to `SKILL.md`, scoring, severity, evidence gates, or provider behavior need stronger justification than editorial changes. Include:

1. the failure mode or ambiguity;
2. at least one positive and one adversarial example;
3. the expected score/decision effect;
4. re-review compatibility and goalpost-stability impact; and
5. evidence that the change does not turn optional excellence work into a merge blocker.

## Commit and pull-request scope

Keep pull requests focused. Separate installer/tooling work from review-policy changes when they can be evaluated independently. Maintainers may ask to split broad changes.

By contributing, you agree that your contribution is provided under the repository's selected license.
