# Exact-target Android merge-readiness evidence

Observed by the primary calibration runner on 2026-10-01 on macOS 27.0 arm64. Each repository was a clean detached checkout at the stated repaired SHA before and after execution. Gradle 9.5.0 (distribution SHA-256 `553c78f50dafcd54d65b9a444649057857469edf836431389695608536d6b746`), Android Gradle Plugin 9.3.2, JDK 17.0.14, and Android SDK platform 36 were used.

For each fixture, the exact target first passed `:app:assembleDebug` plus `:app:fixtureBaselineCheck` with `--rerun-tasks`. A second focused fixture task then executed the remaining behavior already required by the published B path. The task's historical name, `fixtureExcellenceCheck`, does not classify that behavior as optional; the assertions below are supplied here as merge-readiness evidence only.

| Fixture | Exact repaired SHA | Build and first focused result | Additional B-path result | Covered B-path behavior |
|---|---|---|---|---|
| `profile-setup` | `3ec59ede43c81b313208100d52872340692f4b39` | `PASS` — build exit 0; 35/35 actionable tasks | `PASS` — focused exit 0; 2/2 actionable tasks | persistence ordering; failed-save recovery; permission-denial recovery; retry; one save for an in-flight reentrant submission |
| `order-upload` | `7ddac75b89545a0fd8fb6b6332c8724cfda080ce` | `PASS` — build exit 0; 35/35 actionable tasks | `PASS` — focused exit 0; 2/2 actionable tasks | stable key through transient failure and worker recreation; both interruption and explicit `CancellationException` remain cancellation; durable acknowledgement suppresses redelivery after worker recreation |
| `account-deeplink` | `e3d9feb58ce52fd49cc14e2ad7db42561c07bd0e` | `PASS` — build exit 0; 35/35 actionable tasks | `PASS` — focused task exit 0 plus boundary checker exit 0 | denied and logged-out cold/warm routes; malformed, absent, empty, and blank identifiers rejected before authorization/opening; authorized canonical cold/warm identifiers forwarded unchanged |

## Exact commands and retained output

The build/baseline command shape was:

```text
gradle --gradle-user-home <calibration-workspace>/gradle-home --no-daemon --no-watch-fs --console=plain --rerun-tasks -p <fixture-repository> :app:assembleDebug :app:fixtureBaselineCheck
```

The additional B-path command shape was:

```text
gradle --gradle-user-home <calibration-workspace>/gradle-home --no-daemon --no-watch-fs --console=plain --rerun-tasks -p <fixture-repository> :app:fixtureExcellenceCheck
```

Raw outputs:

- `logs/profile-setup-baseline.raw.log`
- `logs/profile-setup-excellence.raw.log`
- `logs/order-upload-baseline-v2.raw.log`
- `logs/order-upload-excellence-v2.raw.log`
- `logs/account-deeplink-baseline.raw.log`
- `logs/account-deeplink-excellence.raw.log`
- `logs/account-deeplink-empty-blank-focused.raw.log`

The account boundary checker was source-external: it compiled the exact tracked router from the clean target with JBR 17, called both handlers with empty and whitespace-only identifiers, and asserted that neither authorization nor account opening was reached. Its source is retained with the calibration artifacts; it did not modify the reviewed repository.

This packet establishes only the locked merge-readiness B path. It contains no optional 5.0 evidence. Evaluate a finite E path only after reaching 4.5, and do not add or tighten it after later evidence is revealed.
