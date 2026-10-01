# Exact-target optional excellence evidence

Observed by the primary calibration runner on 2026-10-01 after both blind reviewers had completed phase 3 and frozen any optional path. The reviewed repositories remained clean detached checkouts at the stated repaired SHAs. Both source-external Java checkers used JBR 17.0.14 and compiled the exact tracked production source together with a retained checker; neither modified the target repository.

| Fixture | Exact repaired SHA | Locked optional condition | Result | Raw log |
|---|---|---|---|---|
| `profile-setup` | `3ec59ede43c81b313208100d52872340692f4b39` | Reviewer H E1: capture the `Repository.save` argument and prove it equals the city supplied to `submit`, unchanged | `PASS` — compile exit 0; run exit 0 | `logs/profile-setup-optional-e1.raw.log` |
| `order-upload` | `7ddac75b89545a0fd8fb6b6332c8724cfda080ce` | Reviewer H E1: capture `Gateway.upload` order identifiers on initial attempt and recreated-worker retry and prove both equal the requested order identifier, unchanged | `PASS` — compile exit 0; run exit 0 | `logs/order-upload-optional-e1.raw.log` |
| `account-deeplink` | `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e` | No E item was published by either reviewer | No new evidence required | — |

Reviewer I had already classified all applicable gates as covered at phase 3 and published no E path. This packet does not create a new condition for that reviewer; it is additional corroborating evidence only. Reviewers must preserve their phase-3 paths and may not add, split, replace, or tighten optional items after seeing this packet.
