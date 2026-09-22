# Sources and provenance

Android UltraReview is an independently written Android adaptation of the iOS UltraReview review contract used as its reference design. This lineage is intentional and explicit: it preserves the exact-target, evidence, scoring, re-review, GitHub submission, and convergence model while replacing iOS platform guidance with an original Android review synthesis.

Two Android skill collections were consulted as non-normative research material. Their instructions, architectural preferences, fixed versions, and code samples are neither bundled nor treated as policy. The Android domain guide was written anew and cross-checked against the primary sources below.

## Consulted source projects

- [Android Skills](https://github.com/android/skills) by Google — version-sensitive Android recipes, especially Navigation 3, used as research at pinned commit `725364add95396448b0c91c585265dbaf1c36987`. The consulted Navigation 3 material is published under the [Apache License 2.0](https://github.com/android/skills/blob/725364add95396448b0c91c585265dbaf1c36987/LICENSE.txt).
- [compose-skill](https://github.com/aldefy/compose-skill) by Adit Lal — Compose review signals used as research at pinned commit `954ef54ea32288fbc90745f012d09d7b791f0d8a`. The consulted revision identified the project as MIT-licensed.

No source-project file, template, or code sample is redistributed here. Before redistributing copied or substantially adapted upstream material in a future revision, re-check the upstream license and retain all required notices.

## Primary Android and Kotlin guidance

- [Android: Guide to app architecture](https://developer.android.com/topic/architecture) and [architecture recommendations](https://developer.android.com/topic/architecture/recommendations) — architecture is contextual guidance; boundaries, state ownership, data flow, concurrency policy, and testability.
- [Android: Processes and app lifecycle](https://developer.android.com/guide/components/activities/process-lifecycle) and [Activity lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle) — process death, component lifetime, and restoration boundaries.
- [Android: Background work](https://developer.android.com/develop/background-work) and [background tasks overview](https://developer.android.com/develop/background-work/background-tasks) — async work, WorkManager, services, alarms, restrictions, and power trade-offs.
- [Android: ANRs](https://developer.android.com/topic/performance/vitals/anr) — main-thread responsiveness and component timeout failure modes.
- [Android: Request runtime permissions](https://developer.android.com/training/permissions/requesting), [shared storage](https://developer.android.com/training/data-storage/shared), and [Storage Access Framework](https://developer.android.com/training/data-storage/shared/documents-files) — denial/recovery, minimization, scoped user-controlled storage, and URI access.
- [Android: App Links](https://developer.android.com/training/app-links/about) and [verification](https://developer.android.com/training/app-links/verify-applinks) — external entry, domain association, signing, routing, and test evidence.
- [Android: Build an offline-first app](https://developer.android.com/topic/architecture/data-layer/offline-first), [DataStore](https://developer.android.com/topic/libraries/architecture/datastore), and [Room migrations](https://developer.android.com/training/data-storage/room/migrating-db-versions) — sources of truth, synchronization/conflict handling, transactional preferences, schema evolution, and migration tests.
- [Android: Jetpack Compose performance](https://developer.android.com/develop/ui/compose/performance), [Compose stability](https://developer.android.com/develop/ui/compose/performance/stability), [Compose phases](https://developer.android.com/develop/ui/compose/phases), and [Compose semantics](https://developer.android.com/develop/ui/compose/accessibility/semantics) — triggered recomposition, phase, identity, performance, and accessibility checks without automatic findings.
- [Android: Adaptive apps](https://developer.android.com/develop/adaptive-apps) — window-size, multi-window, foldable, and input/form-factor considerations.
- [Android: Testing strategies](https://developer.android.com/training/testing/fundamentals/strategies) — proportional test levels and the lowest-cost test with sufficient fidelity.
- [Android: Security risks](https://developer.android.com/privacy-and-security/risks), [security checklist](https://developer.android.com/privacy-and-security/security-tips), and [network security configuration](https://developer.android.com/privacy-and-security/security-config) — exported components, IPC/intents, WebView, logs, storage, and transport trust.
- [Android: Play Integrity](https://developer.android.com/google/play/integrity/overview) — attestation as one risk signal, content binding, server verification, tiered enforcement, and recovery.
- [Android: R8 app optimization](https://developer.android.com/topic/performance/app-optimization/enable-app-optimization) and [Baseline Profiles](https://developer.android.com/topic/performance/baselineprofiles/overview) — release shrinking/optimization and representative profile evidence.
- [Android: Platform versions and behavior changes](https://developer.android.com/about/versions) and [Google Play target API requirements](https://support.google.com/googleplay/android-developer/answer/11926878) — current migration behavior and distribution deadlines must be verified at review time, not frozen into this skill.
- [Kotlin: Coroutines guide](https://kotlinlang.org/docs/coroutines-guide.html), [coroutine basics](https://kotlinlang.org/docs/coroutines-basics.html), [Flow API](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/), and [StateFlow API](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/) — structured concurrency, cancellation, exception propagation, cold/hot streams, state, and conflation.

## Build, Git, and review operations

- [Gradle: Configuration Cache](https://docs.gradle.org/current/userguide/configuration_cache.html), [Build Cache](https://docs.gradle.org/current/userguide/build_cache.html), and [directory layout](https://docs.gradle.org/current/userguide/directory_layout.html) — build phases, cache contracts, project/Gradle-home artifacts, isolation, and credential risks.
- [Git: git-worktree](https://git-scm.com/docs/git-worktree) — detached worktree creation, ownership, listing, and conservative removal.
- [GitHub REST: Pull request reviews](https://docs.github.com/en/rest/pulls/reviews) — commit-pinned batched reviews and provider states.
- [Google Engineering Practices: The Standard of Code Review](https://google.github.io/eng-practices/review/reviewer/standard.html) — evidence over preference, forward progress, and avoiding perfection-driven loops.

## Deliberate departures

- Do not assume the newest Android, Kotlin, Compose, AGP, Gradle, JDK, or library release. Repository settings and supported distribution targets are authoritative; consult current behavior-change and compatibility sources only when triggered.
- Do not make Compose, Navigation 3, Hilt/Dagger/Koin, Room, DataStore, Retrofit, WorkManager, offline-first, Clean Architecture, MVVM/MVI/UDF, feature/core modules, KSP, version catalogs, configuration cache, R8 tuning, or Baseline Profiles mandatory.
- Do not enforce one activity, one module, one `StateFlow`, one event primitive, one dispatcher-injection policy, one DTO/domain split, one package layout, or one test-double style universally.
- Do not ban Views, Fragments, Java, callbacks, RxJava, services, manual DI, safe legacy APIs, or incremental migration when ownership and behavior are correct.
- Do not turn Compose stability, missing lazy-list keys, lambdas, modifier order, image-loader options, architecture layering, or performance folklore into findings without a concrete trigger-to-consequence path.
- Do not require exhaustive API/device/OEM matrices, device farms, new test frameworks, screenshots, benchmarks, migrations, or optional polish for merge readiness or 5.0.
- Do not confuse per-finding reviewer certainty with this skill's overall PR merge-confidence score.
