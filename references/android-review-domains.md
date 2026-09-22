# Android review domains

Apply only the lenses triggered by the PR. First read product intent, scoped repository authority, architecture/build sources, actual module graph, supported form factors, variants, and platform/toolchain targets. These checks identify questions, evidence needs, and failure modes; they are not automatic findings or a mandate to adopt the newest stack.

For every candidate issue, require a concrete trigger or precondition, trace it through the current code/configuration to an observable consequence, identify the governing product/project/platform invariant when non-obvious, and calibrate severity under [scoring-and-severity.md](scoring-and-severity.md). Safe established Java, Views, callbacks, RxJava, services, manual DI, monoliths, or other legacy technology remains valid when it fits the project and changed behavior.

## Quick routing

Read the opening product lens plus only the sections activated by the diff or acceptance scope. Search the listed signals when routing a large change:

| Section | Typical routing signals |
|---|---|
| [Product and domain correctness](#product-and-domain-correctness) | acceptance criteria, completion, recovery, time/location, entitlement, transaction |
| [Project, Gradle, build, and distribution](#project-gradle-build-and-distribution) | `build.gradle`, `settings.gradle`, plugins, variants, manifests, resources, dependencies, packaging |
| [Kotlin, Java, APIs, and code generation](#kotlin-java-apis-and-code-generation) | public API, Java interop, generics, reflection, KSP/kapt, compiler plugin, generated code |
| [Android lifecycle, process death, tasks, and state restoration](#android-lifecycle-process-death-tasks-and-state-restoration) | Activity/Fragment lifecycle, `ViewModel`, saved state, process recreation, tasks/back stack, multiprocess |
| [Coroutines, Flow, threading, Binder, and ANRs](#coroutines-flow-threading-binder-and-anrs) | suspend, scope/job, Flow, callback bridge, shared state, dispatcher, IPC, main-thread work |
| [Jetpack Compose, navigation, and UI behavior](#jetpack-compose-navigation-and-ui-behavior) | composable, effect, snapshot state, lazy layout, Compose navigation, semantics, adaptive UI |
| [Views, Fragments, and hybrid UI](#views-fragments-and-hybrid-ui) | XML/View, binding, adapter, Fragment transaction, `AndroidView`, `ComposeView` |
| [Navigation, intents, deep links, and App Links](#navigation-intents-deep-links-and-app-links) | route, intent filter, URI, App Link, `onNewIntent`, `PendingIntent`, external entry |
| [Architecture, modularization, and dependency injection](#architecture-modularization-and-dependency-injection) | module edge, source-set boundary, state owner, Hilt/Dagger/Koin/manual DI, scope |
| [Data, persistence, offline behavior, and synchronization](#data-persistence-offline-behavior-and-synchronization) | Room/SQLite, DataStore, file, cache, migration, sync/outbox, offline, paging |
| [Networking and backend contracts](#networking-and-backend-contracts) | HTTP/API, DTO, pagination, auth refresh, retry, WebSocket/gRPC, compatibility |
| [Background execution, alarms, notifications, and OEM behavior](#background-execution-alarms-notifications-and-oem-behavior) | Worker, job, service, alarm, receiver, Doze, notification, retry |
| [Components, permissions, storage, and platform boundaries](#components-permissions-storage-and-platform-boundaries) | component/export, runtime permission, storage/media/URI, Direct Boot, parcel, package visibility |
| [Security, privacy, authentication, and WebView](#security-privacy-authentication-and-webview) | credential/token, OAuth/biometric, Keystore, sensitive data, SDK collection, WebView, trust boundary |
| [Performance, ART, R8, dex, and baseline profiles](#performance-art-r8-dex-and-baseline-profiles) | ANR/jank/startup/memory/size, minification, keep rules, JNI, profile, benchmark |
| [Tests and quality evidence](#tests-and-quality-evidence) | test changes, validation claims, migration/UI/runtime evidence, lint/detekt, flakes |
| [Release safety and platform migration](#release-safety-and-platform-migration) | target SDK, Android behavior change, signing, Play track, feature flag, rollout, SDK upgrade |
| [Agentic and generated-change integrity](#agentic-and-generated-change-integrity) | generated patch/tests, plan reconciliation, invented API, mechanical edit, placeholder |
| [Additional triggered platform lenses](#additional-triggered-platform-lenses) | billing, FCM, media/camera, sensors/health, widgets/Wear/TV/Auto/XR, native/JNI |

## Product and domain correctness

- Compare copy, constants, ordering, navigation, completion criteria, persistence, negative requirements, rollout rules, and failure/recovery behavior with authoritative product evidence.
- Trace first run, upgrade, relaunch, partial completion, interruption, background/foreground, configuration change, process recreation, cancellation, permission denial/revocation, retry, offline, duplicate delivery, and partial service failure where relevant.
- Verify that work delivered for one step does not prematurely complete, persist, navigate past, or bypass a later step.
- Confirm downstream consumers can obtain the state or event the current task promises to produce, including after the owning screen or process disappears.
- For time-, schedule-, or location-dependent features, distinguish device, user-selected, server, and event/location time zones. Check daylight-saving transitions, locale/calendar semantics, day rollover, reboot/time-zone/time-change events, and background scheduling only when the feature depends on them.
- Treat domain semantics as correctness: a displayed status, notification, entitlement, transaction, boundary, or sync result must match its real product meaning, not merely fit the data model.

## Project, Gradle, build, and distribution

- Establish the repository's supported wrapper/Gradle, Android Gradle Plugin, JDK/JVM target/toolchain, Kotlin/Java, Compose compiler/plugin, KSP/kapt, compile SDK, min SDK, target SDK, NDK, and dependency versions before applying version-specific advice.
- Review the build representation the repository actually uses: settings scripts, module build files, convention plugins, included/composite builds, `buildSrc`, version catalogs, dependency locks/verification, source sets, flavors, build types, and CI commands. A different safe organization is not a defect.
- Trace semantic changes across affected variants and source-set overlays. Check application IDs, namespaces, versioning, manifest placeholders, resources, generated constants, signing selection, debug/release separation, test fixtures, and feature/dynamic-feature wiring.
- Inspect manifest-merger consequences rather than only the edited manifest: component exposure, permissions, features, providers, authorities, metadata, backup/data-extraction rules, network security, app links, queries/package visibility, services, receivers, and foreground-service declarations.
- Verify that assets, resources, translations, schemas, baseline/startup profiles, JNI libraries, test files, generated sources, and new code enter only intended modules/variants and are packaged once. For changed resources, check required defaults and qualifier fallback rather than validating only one locale, density, theme, or configuration.
- For convention plugins and custom tasks, check configuration-time I/O/process/network work, eager task realization, correct Provider API/lazy wiring, declared inputs/outputs, reproducibility, variant awareness, worker isolation, and build/configuration-cache contracts the repository actually supports.
- For dependency changes, inspect direct and transitive intent, repositories, verification/lock state, dynamic/SNAPSHOT/local artifacts, licensing/policy where the project governs it, SDK size/privacy/stability, duplicate classes/resources, native ABI impact, and unrelated drift.
- Inspect wrapper, plugin, repository, code-generation, and dependency-verification changes as supply-chain/execution boundaries before running them.
- Verify app bundle/dynamic-delivery, Play feature conditions, asset packs, splits/ABI/density/language behavior, consumer ProGuard rules for libraries, and published API/metadata only when changed.
- Do not block solely because configuration cache, build cache, version catalogs, convention plugins, KSP, Compose, a multi-module layout, or a newer toolchain is absent. A build-performance concern needs a broken project contract or credible measured consequence.
- Compile/build evidence must come from the exact reviewed head or its qualifying current merge result. IDE sync, parsing, or a different variant does not establish compiler, manifest, resource, packaging, shrinking, or linkage correctness.

## Kotlin, Java, APIs, and code generation

- Inspect unsafe null assertions/casts, platform types, integer/collection bounds, value/reference equality, mutable aliasing, default-argument and overload behavior, delegated state, exhaustiveness, reflection assumptions, and swallowed failures where a concrete path exists.
- Verify public/internal/module contracts, visibility, sealed hierarchies, generics/variance, inline/reified behavior, annotations, JVM names/signatures, default-interface/desugaring requirements, and binary/source compatibility for consumed libraries or mixed-version modules.
- For Java interop, inspect nullability annotations, checked/unchecked exceptions, SAM/overload ambiguity, wildcard exposure, static/default members, lifecycle ownership, and thread expectations. Do not demand a Kotlin rewrite.
- Treat `@Stable`, `@Immutable`, opt-ins, suppressions, unchecked casts, and thread/concurrency annotations as proof obligations, not automatic fixes or automatic defects.
- For KSP/kapt/annotation processing/compiler plugins, verify supported toolchain combinations, processor incrementality/isolation where promised, generated API/package names, variant/source-set wiring, deterministic output, duplicate generation, cache inputs, and clean-build behavior.
- Search for an existing project abstraction before accepting duplicated clients, storage, formatters, navigation contracts, or domain logic, but report duplication only when the PR creates a concrete correctness/ownership problem or violates an enforced boundary.
- Do not flag older safe language/library APIs merely because newer syntax exists. Tie modernization to a concrete correctness, compatibility, security, accessibility, or maintenance consequence in scope.

## Android lifecycle, process death, tasks, and state restoration

- Separate configuration change, destination recreation, task/background transitions, activity finish, and process death. A `ViewModel` survives some recreation but not process death; in-memory singletons/static state are not durable state.
- Decide state semantics first: ephemeral rendering state, navigation/session state, saveable user input, durable domain data, or remote truth. Verify the chosen owner and restoration mechanism match the required lifetime and size.
- Inspect `savedInstanceState`, `SavedStateHandle`, `rememberSaveable`, persistence, and custom savers only when restoration is required. Avoid persisting large/complex object graphs or assuming every state belongs in saved state.
- Check Activity/Fragment/View lifecycle ordering, `viewLifecycleOwner` versus Fragment lifetime, retained callbacks/listeners, `onNewIntent`, configuration changes, multi-window/resizing, and teardown paths. Do not rely on `onDestroy()` for guaranteed cleanup or persistence.
- Trace tasks and back stack: launch modes, intent flags, document mode, deep-link cold/warm start, Back versus Up, predictive/system back integration where applicable, multiple activities/tasks, finish behavior, and authentication/logout stack invalidation.
- App components may be recreated from manifest/OS entry points without the UI path that normally initializes dependencies. Verify process-start and direct-entry assumptions.
- For multi-process code, do not assume application singletons, in-memory locks, DataStore instances, or initialization run once across all processes. Inspect provider/service/process declarations and cross-process storage/IPC semantics.
- State restoration gaps are findings only when a controlling requirement or normal platform event loses/duplicates material user work, breaks navigation, or violates a data/security invariant.

## Coroutines, Flow, threading, Binder, and ANRs

- Identify every coroutine's owner, parent job, dispatcher/context, cancellation trigger, failure policy, and result consumer. Prefer structured lifetimes; an external/application scope is valid when the work intentionally outlives the caller and ownership is explicit.
- Check `GlobalScope`, ad-hoc scopes, stored/untracked jobs, `launch` used where completion/errors matter, and nested work for leaks, lost errors, premature cancellation, or work surviving logout/navigation/process state.
- Cancellation is cooperative. Inspect CPU loops, blocking calls, callback bridges, retry/backoff, `NonCancellable`, cleanup, broad `catch`, `runCatching`, and custom result wrappers so `CancellationException` is not converted into ordinary failure when cancellation must propagate.
- Verify exception propagation and supervisor boundaries: a child failure should cancel or not cancel siblings according to the feature contract, and `CoroutineExceptionHandler` is not a substitute for awaiting a result.
- Require main safety at ownership boundaries. Trace disk/network/crypto/image/database/Binder/serialization/large-collection work that can reach the main looper; switching dispatchers repeatedly is not itself correctness.
- Inspect stale read-after-suspension, concurrent read-modify-write, and non-atomic transforms such as `flow.value = flow.value.copy(...)`; distinguish these from a single atomic `.value` assignment, and verify `update` or explicit synchronization when an atomic transformation is required. Also check mutex/actor/channel ownership, request identity, and late success/failure overwriting newer state.
- Distinguish cold `Flow` execution per collector from hot `StateFlow`/`SharedFlow` lifetime. Check duplicate upstream work, sharing scope/policy, replay, conflation, buffer/overflow behavior, delivery guarantees, terminal behavior, and memory retention against the actual semantics.
- `StateFlow` is state, not guaranteed delivery of every transition. `SharedFlow`, `Channel`, callbacks, or explicit state can all be valid; demonstrate a lost, duplicated, or replayed user-visible outcome before finding fault.
- Collect UI flows with the repository's lifecycle-aware pattern. Check collectors that remain active off-screen, restart side effects, collect multiple times, or miss required events—not the mere absence of one favored API.
- A cold `callbackFlow` bridge needs exactly one registration per active collection and matching unregister/`awaitClose` cleanup, plus thread-safe send and correct close/failure mapping. If the product requires one upstream subscription shared by many collectors, verify that sharing is explicit and owned at the intended lifetime.
- Trace Binder/IPC payload size, synchronous calls, death/disconnect, identity/permission checks, parcelable/version compatibility, and callbacks onto the main thread when IPC is changed.
- ANRs can arise from input dispatch, broadcast/service/provider work, lock contention, slow Binder calls, or main-thread I/O/CPU. Require a credible blocking path or evidence; do not label any coroutine or allocation as an ANR by intuition.

## Jetpack Compose, navigation, and UI behavior

- Decide ownership first: local ephemeral state, saveable state, parent/route state, `ViewModel`/state holder, or durable repository state. Flag mirrored/derived state only when it can drift or cause duplicate truth.
- Treat composition as repeatable. Keep externally visible side effects, subscriptions, navigation, permission launches, analytics, and service work in appropriately keyed effect APIs with cleanup/ownership. Check missing/excess keys and stale captures (`rememberUpdatedState` or equivalent when needed).
- Inspect `remember`/`rememberSaveable` dependencies, snapshot-state observability, mutations during composition or after a state read in the same frame, derived-state semantics, and snapshot/thread access.
- Check composable identity and stable keys where item movement/reuse can transfer state, effects, animations, or focus. A key is not mandatory when identity cannot affect behavior.
- Recomposition/stability annotations are performance contracts. Incorrect annotations can produce stale UI; unstable parameters or lambdas are not blocking defects without a demonstrated hot-path consequence. Use compiler metrics/tracing rather than folklore for material performance claims.
- Understand composition, layout, and draw phases. Move repeated expensive work, high-frequency state reads, image decoding, sorting/filtering, subcomposition/intrinsics, or custom layout work only when the affected path can cause measurable jank, allocation, battery, or correctness impact.
- Modifier order changes measurement, placement, drawing, clipping, pointer input, semantics, and hit area. Inspect it when behavior changes; do not enforce one stylistic ordering.
- For lazy layouts/paging, inspect item identity/content types where relevant, pager/source lifetime, refresh loops, load states, cancellation, retry, empty/error semantics, and scroll restoration. `LazyColumn`, paging, or cached flows are not universally required.
- Verify navigation route/argument compatibility, durable identifiers versus oversized/stale objects, destination ownership, result delivery lifetime, multiple back stacks, `singleTop`/restore behavior, nested graph popping, auth gates, and process restoration against the library/version actually used.
- For animations, verify state-driven completion, cancellation/re-entry, off-screen work, and reduced-motion behavior where material. Avoid fixed delays as coordination when they can race actual completion.
- For material UI changes, check project-supported localization, plurals/formatting, RTL, font scaling, insets/system bars/IME, orientation/window resizing, large screens/foldables, keyboard/mouse/D-pad, and representative theme/dark/high-contrast behavior only when supported or affected.
- Accessibility checks include meaningful versus decorative descriptions, roles/actions/state, semantics grouping without hiding child actions, traversal/focus, headings, error association/announcement, touch targets, contrast, and custom control behavior. Material components may supply semantics; do not add duplicate descriptions blindly.
- When a project owns a design system, verify that changed tokens, themes, and shared components preserve their documented contract across consumers; do not require a design-system layer in projects that do not have one.
- For changed image loading, inspect requested decode size, aspect/crop behavior, lifecycle/cancellation, placeholder/error state, cache semantics, sensitive-content handling, and list reuse only when the affected path makes them material. Do not mandate a particular image library or confuse a cache with durable offline storage.

## Views, Fragments, and hybrid UI

- Inspect Activity/Fragment/view lifecycle ownership, view-binding cleanup, listener/observer registration, adapters, view holders, recycling reset, diff identity/payloads, saved state, and asynchronous work attached to a destroyed view.
- Verify main-thread UI access, `LifecycleOwner`/`ViewTree*Owner` propagation, Fragment transactions/state loss, child Fragment ownership, Activity Result registration, and back handling where changed.
- Check layout constraints, measurement loops, overdraw, nested scrolling, insets, transitions/animations, focus, accessibility, RTL, localization expansion, and font scaling proportionally.
- In Compose/View interop, keep composition disposal, saved-state/lifecycle owners, `AndroidView` reuse/update/release, coordinator/listener identity, nested scrolling, and state ownership explicit. Do not redo one-time setup on every recomposition or retain stale closures/views.
- An incremental Views-to-Compose migration is valid. Do not require wholesale migration, one activity, Fragments, or Compose unless authoritative scope does.

## Navigation, intents, deep links, and App Links

- Treat every external intent/deep link as untrusted input. Validate scheme/host/path/query, type/range/encoding, absent/repeated parameters, canonicalization, and destination authorization—not just route parsing.
- Apply authentication and object-level authorization at the operation/data boundary, not only by hiding a destination. Revalidate warm-start `onNewIntent`, restored stacks, logout/account switch, and notification/pending-intent entries.
- Check manifest intent-filter breadth, browsable/exported consequences, verified App Link domain/path coverage, certificate/asset-links ownership, variant signing/application ID, web fallback, and Back/Up/task-stack behavior.
- Pass durable identifiers when process recreation or freshness requires reloading; passing richer objects can be correct within a bounded in-process contract. Do not universalize either approach.
- For multi-back-stack/adaptive navigation, verify independent state, destination uniqueness, save/restore, pop behavior, and selected pane/tab after recreation. Follow the project's Navigation 2, Navigation 3, custom router, Fragment, or multi-activity semantics and resolved versions.
- For `PendingIntent`, inspect mutability, explicit target/package, uniqueness/update flags, extras, replay/cross-user implications, and privilege carried by the creator when changed.

## Architecture, modularization, and dependency injection

- Infer the actual architecture from accepted docs, dependency graph, build enforcement, nearby code, and tests. MVVM, MVI, UDF, reducer/state machine, Clean Architecture, feature/core, API/impl, and package-by-feature are options with trade-offs, not universal gates.
- Check concrete responsibility/ownership failures: UI tied to an unstable transport/storage representation, business rules split across conflicting sources, domain state duplicated, feature cycles, internal implementation exposed across a promised boundary, or a dependency direction that violates enforced rules.
- A domain/use-case layer can remove duplication or coordinate complex logic; in a small feature it can be overhead. Pass-through layers, interfaces, or repositories are not inherently good or bad.
- For modularization, inspect Gradle dependency direction, public API surface, resource visibility, transitive leakage, variant compatibility, dynamic-feature boundaries, navigation contracts, DI entry points, test fixtures, and build-time impact. Do not prescribe module count or layout.
- For Hilt/Dagger/Koin/manual DI/service locators, verify the conceptual lifetime and owner—process/application, retained or `ViewModel`, activity, fragment, view/composition, worker/service, account/session, or task—and map it to the actual framework's available scopes/components. Also inspect qualifiers, multibindings, assisted construction, framework-created types, test replacements, and logout/account teardown; do not imply every framework exposes every named scope.
- Watch for scope inversion, duplicate instances of stateful dependencies, captured short-lived context/view, missing worker/startup factory wiring, cyclic initialization, hidden global state, or provider work on the main thread.
- Generated DI correctness may require clean compilation and release/minified proof. Do not mandate a DI framework or an interface solely to enable a mock.

## Data, persistence, offline behavior, and synchronization

- Honor the project's source of truth. Define local/remote/cache ownership, freshness, invalidation, optimistic update, rollback, and reconnect/conflict behavior before judging a repository pattern.
- Persist correctness-critical state before navigation, process loss, worker completion, or a completion marker can make recovery impossible. Surface or deliberately handle write failures; do not claim success before the authoritative transaction commits.
- Choose storage by semantics: transient cache, small settings/state, structured relational data, user-visible files/media, credentials, or cross-process data. Do not require Room/DataStore/offline-first merely because they are available.
- For Room/SQLite, review schema/version/export state, ordered migration paths from every supported checkpoint, destructive fallback/downgrade behavior, defaults/nullability, data transforms, indices/unique/foreign keys, triggers/views, transactions, WAL/concurrency, and rollback/partial failure.
- Treat destructive migration as a data-loss operation requiring explicit product/release acceptance for disposable data. Compilation or fresh-install tests do not prove upgrade safety; use realistic old schemas/data for material migrations.
- For DataStore/preferences/files, inspect single-versus-multi-process access, one active instance per file where required, immutable values/serializer behavior, corruption handling, migration/atomicity, update races, and I/O/error exposure.
- For offline/sync/outbox work, verify atomic local mutation plus queueing, unique/idempotent work, operation IDs/idempotency keys, ordering/dependencies, retry/backoff, partial batch failure, tombstones/deletes, conflict/version semantics, clock assumptions, account changes, and duplicate delivery.
- For paging/caching, inspect key computation, invalidation, mediator transactionality, stale/empty/error semantics, refresh replacement, partial results, cursor/version compatibility, and user action during refresh.
- Secrets and durable auth tokens belong in a project-approved protected store with lifecycle/logout/backup behavior defined; “encrypted” is not enough without key and failure semantics.

## Networking and backend contracts

- Verify base URL/environment selection, path/query encoding, headers, status semantics, body/error decoding, null/default/unknown-enum behavior, cancellation, timeout, retry/backoff, idempotency, pagination, caching, connectivity assumptions, and offline behavior.
- Separate transport/DTO/domain forms only where project evolution or contracts require it. Direct SDK/DTO use is not a defect by itself; show how it breaks compatibility, ownership, or behavior.
- Do not treat every non-2xx response as success because a body decoded. Preserve structured server errors and distinguish transient transport failure, auth expiry, conflict/rate limit, malformed data, and intentional empty results.
- For authentication, trace concurrent refresh, token rotation/revocation, replay, logout/account switch, clock skew, refresh failure, request retry, and storage/redaction. Avoid refresh storms and replaying non-idempotent requests.
- For Retrofit/OkHttp/WebSockets/gRPC/backend SDKs or custom clients, inspect interceptor order, lifecycle/cancellation, dispatcher/threading, connection/listener cleanup, pagination/realtime duplication, certificate/network configuration, and release logging.
- Treat the app as untrusted. Authorization and sensitive business invariants belong on the server; privileged/admin/service credentials must not ship in the app. Client checks still matter for UX and defense in depth.
- Preserve backward compatibility with deployed clients/servers and staged rollouts. A schema/API change may need tolerant readers, version negotiation, dual-write/read, flag sequencing, or rollback support based on the product contract.

## Background execution, alarms, notifications, and OEM behavior

- Classify the work: immediate user-bound async work, persistent/reliable deferrable work, exact user-visible scheduling, ongoing user-noticeable work, or a platform-specific API. Choose among coroutine ownership, WorkManager/JobScheduler, alarm, foreground service, or specialized API based on repository/platform requirements—not slogans. WorkManager does not promise exact timing or exactly-once execution; correctness still needs idempotency and retry-safe boundaries.
- For WorkManager, inspect unique-work names/policies, constraints, input/output limits, expedited/quota behavior, idempotency, retry result/backoff, cancellation, progress, chaining, account/logout changes, process restart, boot/reschedule, and database/source-of-truth transaction boundaries.
- For foreground services, verify start eligibility, timely foreground promotion, declared service type and permissions, user-visible notification, stop behavior, task/removal/process handling, and current target-SDK restrictions. A service runs on the main thread unless work is explicitly moved.
- For alarms, distinguish exact versus inexact need, permission/policy, idle/Doze behavior, repeating drift, reboot/time/time-zone changes, duplicate scheduling/cancellation, and user-visible contract.
- For broadcasts/providers/services, keep callback work within platform time limits or hand off safely; inspect `goAsync`/pending-result completion, cold-start initialization, exported access, and duplicate delivery.
- Doze, standby buckets, background restrictions, quotas, and OEM battery management make timing nondeterministic. Do not promise exact background timing without an applicable supported mechanism, or paper over OEM behavior with unsupported keep-alive hacks.
- Notifications need channel/permission/version behavior, stable IDs/grouping, privacy on lockscreen, correct pending intents/back stack, deduplication, update/cancel behavior, and stale-account/content handling when changed.

## Components, permissions, storage, and platform boundaries

- Inspect all changed Activities, Services, Receivers, Providers, aliases, intent filters, and permissions through the merged manifest. Explicitly verify exported state, protection level, caller validation, URI grants, authority uniqueness, and direct-entry behavior.
- Request permissions in product context, minimize scope, support denial/revocation/“don't ask again” and approximate/limited access where applicable, and re-check at use time. Permission groups and prior grants are not stable assumptions across releases.
- When platform behavior varies by runtime/target SDK, guard or branch using the repository's supported API range and verify the relevant behavior-change documentation. Do not hard-code today's latest SDK as a universal review rule.
- For shared files/media, distinguish app-specific storage, MediaStore, Storage Access Framework, photo picker, and broad file access. Verify persistable URI grants, lifetime, MIME/type/path validation, ownership, deletion, and user control.
- When code can run before user unlock, distinguish device-protected from credential-protected storage, verify Direct Boot component awareness and locked-user behavior, and define any migration or reconciliation after unlock. Apply this only to direct-boot-triggered features.
- Check parcel/bundle/intent size, serializable/parcelable/version compatibility, mutable extras, class-loader behavior, and untrusted deserialization where changed.
- For package visibility and cross-app interaction, request only needed visibility, use explicit intents when appropriate, verify chooser/fallback behavior, and authenticate IPC callers/targets.

## Security, privacy, authentication, and WebView

- Minimize sensitive data, permissions, exported surface, and SDK collection. Confirm collection/use/sharing matches project consent, privacy policy, Data Safety declarations, retention, deletion, and regional requirements in scope.
- Search changed logs, analytics, crash breadcrumbs, URLs, intents, notifications, clipboard, screenshots, backups, storage, and network payloads for tokens, credentials, identifiers, location, health/financial/contact data, and other sensitive values. Debug-only logging still needs reliable release exclusion.
- For Android Keystore/encryption, inspect key generation/authentication policy, hardware assumptions, rotation/invalidation, backup/restore, device credential/biometric changes, failure recovery, nonce/IV use, authenticated encryption, and plaintext lifecycle. Never invent cryptography.
- For OAuth/OIDC/passkeys/biometrics, verify PKCE/state/nonce and redirect ownership where applicable, browser/custom-tab trust, token audience/expiry/refresh/logout, replay, account switching, biometric prompt/key binding, and server-side authorization.
- For network security configuration and certificate pinning, inspect cleartext/debug overrides, trust anchors, hostname/certificate validation, rotation/backup pins, outage/recovery plan, and project threat model. Pinning is not universally required and can harm availability when unmanaged.
- For WebView, minimize JavaScript/file/content access, restrict navigation and schemes/hosts, validate external input, isolate or avoid unsafe JS bridges, handle SSL errors safely, keep mixed content/download/file chooser behavior scoped, and account for cookie/session/data clearing.
- For exported components, deep links, intents, providers, Binder, files, or `PendingIntent`, validate caller/input and apply permission/authorization at the sensitive operation. UI gating alone is not security.
- Play Integrity/root/tamper signals are risk inputs, not sole authorization. Verify server-side token binding/replay protection, tiered enforcement, availability/fallback, privacy, and false-positive recovery when used.
- Audit third-party SDK initialization, permissions, manifest merging, network/data collection, consent sequencing, dynamic code, native libraries, update provenance, size/startup impact, and failure isolation proportionally.
- If a finding involves an exposed credential, active exploit, or sensitive user data, do not publish operational details in a public review. State the blocker minimally and use an authorized private disclosure path; ask before creating a separate private message or incident action.

## Performance, ART, R8, dex, and baseline profiles

- Prioritize user-visible, release-relevant risks: main-thread blocking/ANRs, startup, frame jank, excessive recomposition/layout, bitmap/media memory, leaks, GC/allocation churn, repeated I/O/network, wakeups, battery, large IPC, and APK/AAB/dex/native size.
- Require a credible hot path, trace, metric, or clear bounded causal path before blocking on performance. Debug builds, anecdotal recomposition counts, or micro-optimizations do not establish release impact.
- Understand the toolchain: Kotlin/Java bytecode, desugaring, D8/dexing, R8 shrinking/optimization/obfuscation/resource optimization, ART compilation/profiles, and split packaging. Review the stage the PR actually changes.
- For R8/ProGuard, inspect reflection/serialization/JNI/service loaders/generated adapters, annotation/signature retention, keep attributes, missing-class handling, resource shrinking, mapping/retrace, library consumer rules, and overly broad keep rules. Validate a representative minified build/run when release-only failure is credible.
- For multidex/method or size changes, trace min SDK/startup class placement and packaging rather than applying obsolete rules mechanically.
- For Baseline/Startup Profiles and Macrobenchmark, verify representative critical journeys, correct non-debuggable/profileable setup, package/variant linkage, generated profile packaging/merge, stability, and before/after comparable evidence. Do not require profiles for every PR.
- For build-performance claims, compare identical clean/warm conditions and tasks; distinguish configuration, execution, dependency resolution, local/remote cache, and CI noise. Build scans can expose metadata and require authorization before upload.

## Tests and quality evidence

- Tests should assert authoritative behavior and contracts, not private implementation constants or mocks that cannot fail meaningfully. Choose the lowest-cost test level with sufficient fidelity.
- Use repository-standard local JVM, Kotlin Multiplatform/JVM, Robolectric, instrumentation, Compose/View UI, screenshot, contract, integration, managed-device/device-farm, benchmark, lint, detekt, and custom architecture checks proportionally.
- Design for determinism: control dispatchers/schedulers, time, randomness, UUIDs, network, storage, WorkManager, permissions, locale/time zone, and backend responses through project-approved seams. Fakes often clarify behavior, but do not ban mocks.
- Coroutine tests must use a coherent test scheduler/dispatcher model and prove completion, cancellation, exception, ordering, and stale-result behavior implicated by the change. Advancing time until green without asserting outcomes is weak evidence.
- Migration tests should start from realistic committed old schemas/data and traverse supported paths; fresh-database DAO tests are not migration proof.
- UI/runtime evidence should use semantics or user-observable behavior, not fragile implementation hierarchy, and cover representative font/window/API/accessibility states only when changed risk requires them.
- Treat flaky-test retries as evidence, not erasure. Report the initial failure and known flake status; do not loop until green. Quarantine or device-farm strategy follows repository policy.
- Lint/custom rules can enforce architecture/security contracts, but suppressions and baselines require causal inspection. Do not make all warnings blocking without repository authority and material consequence.
- Authoritative CI may replace local evidence only with exact SHA/merge-result, task/variant/environment, underlying command, and result provenance.

## Release safety and platform migration

- Track behavior changes for both all apps on a new Android release and apps targeting a newer SDK. Compare them to actual min/compile/target SDK, distribution channel, form factors, and rollout deadlines; verify current primary documentation instead of relying on a frozen checklist.
- Inspect API availability/desugaring guards, edge-to-edge/window behavior, notification/permission changes, foreground-service and alarm limits, background starts, storage/media, exported components, pending intents, package visibility, predictive back, large-screen/resizability, and native/runtime requirements only when the upgrade or feature triggers them.
- Verify signing identity, Play App Signing assumptions, upload versus app certificates, App Link fingerprints, key rotation, version codes, tracks, staged rollout, app bundles/splits, dynamic delivery, pre-launch/vitals signals, and policy declarations when changed.
- Feature flags, remote config, kill switches, and forced-update flows need safe defaults, targeting/version compatibility, caching/offline behavior, observability, rollback, stale-client/backend sequencing, and failure modes. A flag is not a substitute for correctness or authorization.
- Dependency/SDK upgrades need release notes and migration/behavior review proportional to impact; inspect privacy, permissions, initialization, manifest merge, binary/native size, minimum SDK, R8, startup, and rollback. Do not demand “latest everything.”

## Agentic and generated-change integrity

- Do not penalize code merely because an agent generated it. Judge the resulting behavior and evidence.
- Reconcile implementation with the approved plan, task, PRD, memory bank, architecture, and actual repository APIs. Look for invented symbols/tasks/flags, stale library APIs, missing module/variant/manifest/resource wiring, placeholder data, and comments that claim behavior the code does not provide.
- Check broad mechanical edits for duplicated abstractions, lost source-set/platform conditions, overwritten hand-written changes, inconsistent contracts, dependency drift, and scope outside the task.
- Treat generated tests as independent evidence only when they assert authoritative outcomes and can fail for the seeded defect—not when they mirror implementation constants or over-mocked paths.
- Inspect agent-authored wrapper/dependency/build-script/codegen/migration/manifest/security/release changes with the same execution and supply-chain caution as human-authored changes.

## Additional triggered platform lenses

- **Billing and entitlements:** server/Play verification, acknowledgement/consumption, pending/cancelled/refunded/revoked states, restore/account changes, offline grace, duplicate callbacks, and test-environment separation.
- **FCM and push:** token rotation, permission/channel state, payload validation, duplicate/out-of-order delivery, direct boot/background limits, notification/deep-link routing, and sensitive lock-screen content.
- **Media/audio/camera:** lifecycle/resource release, audio focus/routes/interruption, media session/foreground service, camera permission/lens/orientation/aspect ratio, storage, codecs/DRM, and hardware failure.
- **Location/Bluetooth/sensors/health:** approximate/background/while-in-use permission, service/background rules, scan limits, lifecycle/cancellation, calibration/availability, data minimization, and settings-change recovery.
- **Widgets, tiles, shortcuts, Wear/TV/Auto/XR:** host process/lifecycle and update budgets, shared storage/schema, remote views/Glance semantics, form-factor input/navigation, deployment modules, and API/device constraints.
- **Native/JNI:** ABI coverage, page size/alignment when required, ownership/lifetime, thread attachment, exception boundaries, memory safety, symbol/packaging, and minified name assumptions.

Apply these only when the diff or acceptance scope activates them. Absence of an optional technology, migration, audit, or evidence framework is never by itself a P0–P2 finding.
