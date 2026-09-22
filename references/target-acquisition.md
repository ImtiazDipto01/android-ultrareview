# PR target acquisition and isolated checkout

Read this reference when the review starts from a PR URL, an empty/non-matching folder, or a normal developer checkout rather than a verified dedicated checkout of the exact PR target.

## Authorization boundary

A review request authorizes read-only retrieval of the requested PR and creation/removal of review-owned temporary clones, worktrees, Gradle homes, build outputs, reports, and emulator/device artifacts needed to inspect and validate it. Host filesystem/network approvals still apply. It does not authorize source edits, commits, pushes, merges, authentication changes, credential collection, provider writes, build-scan publication, or changes to a shared device.

Use existing credentials without printing them. When private access is missing, ask the user to connect/authenticate the provider or supply a checkout/bundle; never request that a token, signing key, keystore password, or service credential be pasted into chat.

## Access ladder

Identify the provider from the URL, then combine the richest safe routes available:

1. native provider connector/MCP for PR metadata, refs, checks, reviews, threads, and authorized task links;
2. authenticated provider CLI or documented API plus Git;
3. ordinary Git/HTTPS and public provider APIs or advertised PR/MR refs; or
4. a user-supplied repository checkout, Git bundle, or exact base/head artifacts.

Computer-use/browser permission is not required when terminal Git/API access works. Request narrowly scoped network/filesystem permission when the host requires it. Do not install tooling or begin an interactive login merely because a route is missing.

Classify acquisition before analysis:

| State | Minimum acquired evidence | Allowed result |
|---|---|---|
| **Live target ready** | authoritative current base/head, complete behavior-relevant diff/repository context, and provider state needed for the requested mode; target identity can be refreshed | full live or draft review; posting still requires separate authority and provider mechanics |
| **Snapshot ready** | exact base/merge base/head and complete behavior-relevant diff/repository context, but live currentness cannot be established or refreshed | scored draft snapshot with the required visible snapshot label; never post it as a current live review |
| **Patch-only** | reviewable patch content exists, exact-target prerequisites are unavailable, and the user requested or accepted a limited inspection | preliminary findings-only inspection; no merge-confidence score, decision, `B`/`E` IDs, build claim, or posting |
| **Target inaccessible** | material is insufficient even for patch inspection, or exact-target prerequisites for the requested full review remain unavailable | stop with the unscored outcome defined in [github-review-format.md](github-review-format.md) |

**Complete behavior-relevant diff** means that every changed path and relevant metadata entry is enumerated and complete content is available for every potentially behavior-relevant change. Name known binary, generated, LFS, or non-renderable omissions and inspect their metadata and integration impact. Include build logic, dependency catalogs/locks, manifests, resources, generated-source inputs, baselines/profiles, schemas, and configuration when changed. An unknown or material omission prevents a scored review. Patch-only never takes precedence over a requested full review unless the user accepts the limited scope.

Operationalize that requirement with a changed-path coverage ledger before scoring. Record every provider-reported path exactly once with one status:

| Status | Meaning |
|---|---|
| `Inspected` | Behavior-relevant content and the necessary surrounding impact radius were read. |
| `Assessed` | Generated, binary, lock, metadata, or mechanical content was classified and its integration consequence was checked without pretending its internals were reviewed. |
| `Omitted` | Content or required context could not be obtained; state whether the omission is material. |

Reconcile the ledger count with the provider and Git changed-path counts, including renames, deletions, submodules, LFS pointers, and generated inputs. The public review may summarize rather than enumerate it, for example: `Coverage: 60/60 changed paths classified; 41 inspected, 19 assessed, 0 omitted.` Coverage is an acquisition/inspection statement, not a `PASS` validation result. Any material `Omitted` entry, count mismatch, or truncated response prevents a score until resolved; a known immaterial omission must be named in the coverage summary.

Missing provider discussions or checks may limit confidence, but missing MCP alone does not. Never silently substitute the default branch, a similarly named local branch, or a stale clone for the PR target.

This acquisition flow is provider-neutral; live submission is GitHub-only in this skill. Use another provider's write mechanics only when separate governing instructions explicitly support them; otherwise keep the result draft-only.

## State 1: no local repository

1. Parse the PR URL and resolve repository, PR/MR number, base SHA, current head SHA, merge base, and provider merge-result SHA when available.
2. Create a uniquely named temporary review root rather than populating or deleting the user's arbitrary empty folder.
3. Clone/fetch objects without checkout and only through an available authorized route. Public repositories may use anonymous read access; private repositories require existing authorized credentials. Suppress automatic LFS materialization and recursive submodule initialization during preflight.
4. Before checkout, inspect repository-local Git configuration plus the target's `.gitattributes`, `.gitmodules`, Gradle wrapper and verification files, settings/build scripts, version catalogs, dependency declarations, included builds, convention plugins, code-generation configuration, and other executable infrastructure from the object database. If hooks, filters, submodules, wrappers, plugins, or materialization cannot be trusted, keep inspection object-only or use trusted secrets-free CI instead of executing them.
5. Once preflight permits it, materialize the head as a detached checkout at the immutable SHA. Fetch the base and merge result as separate refs/objects when needed; do not create or update a developer branch.
6. Verify the remote URL and `HEAD`, verify both base and head objects, compute the merge base, and construct the complete merge-base-to-head diff before analysis. Record whether provider-only metadata, checks, or threads remain unavailable.
7. Apply the execution trust gate before Gradle configuration, dependency resolution, builds, tests, scripts, plugins, code generators, emulator/device interaction, or launching the app.
8. Review and validate the exact target. Refresh every target component at the end for live mode; if currentness cannot be refreshed, downgrade the output to a visibly labeled snapshot and do not post it as current. Then apply the cleanup rules below.

If access fails, report the failed capability—not guessed repository facts—and name the smallest next step: enable network, authenticate an existing CLI/connector, or provide an exact checkout/bundle.

## State 2: repository already exists locally

First inspect the repository root, remotes, current branch/`HEAD`, working-tree and in-progress Git-operation status, submodules/LFS requirements, repository-local hooks/filters, and `git worktree list`. Record the ordinary checkout's initial branch, SHA, and status so they can be verified unchanged. Resolve the provider's current base/head before deciding where to run. Fetch and inspect untrusted `.gitattributes`, `.gitmodules`, dependency, Gradle/build, and executable-infrastructure changes without checkout first; do not trigger LFS, submodule recursion, filters, hooks, the Gradle wrapper, or plugins until the trust preflight permits materialization and execution.

### Object-only snapshot exception

An explicitly named local/historical snapshot may be inspected directly from immutable Git objects without creating or changing a worktree when all potentially behavior-relevant text, metadata, history, and surrounding context can be obtained completely through exact refs, no binary/generated/LFS materialization is needed, and no local build, test, script, app, or worktree-based tool will run. Record the base/merge-base/head tuple and that inspection was object-only. This exception does not qualify incomplete patches, does not bypass the build/test evidence caps, and ends as soon as materialization or execution is needed; then use the isolated-worktree rules below.

### Reuse versus create

Reuse the current checkout only when all of these are true:

- it is already a dedicated review-tool-managed worktree rather than the developer's ordinary checkout;
- it is clean;
- its detached or checked-out `HEAD` exactly equals the provider-reported PR head;
- it has no merge, rebase, cherry-pick, revert, or bisect in progress; and
- no other user/task ownership or active process makes reuse unsafe.

When any condition is false or uncertain, create a review-owned detached worktree at the exact head SHA. This is the default for a normal checkout even when it currently has the same PR branch name. A matching branch name is not proof of a matching commit.

Fetch objects/refs without `git pull`, without switching the user's current branch, and without force-updating a developer's local branch. Never resolve dependencies, initialize submodules, create `local.properties`, build, test, stash, clean, reset, or merge in the ordinary checkout. Place the review worktree outside it in a uniquely named temporary or review-tool-managed location. Detached SHA checkout avoids branch/worktree ownership conflicts.

### Review and validation

Inside the selected review checkout:

1. verify repository remote, `pwd`, clean status, and `git rev-parse HEAD == <provider head SHA>`;
2. construct the complete PR diff from the verified merge base to head rather than reviewing only the last commit;
3. inspect repository instructions and the execution trust gate before running code;
4. record a clean pre-validation baseline, including `HEAD`, index/tracked state, untracked and ignored paths, submodule commits, running review-owned processes, and any pre-existing project `.gradle/` or module `build/` paths;
5. use a unique review-owned `GRADLE_USER_HOME` outside both the ordinary checkout and review worktree when practical. Do not inject output relocation or flags that change repository build semantics merely to isolate output. Track known session-created project `.gradle/`, module `build/`, report, test-result, APK/AAB, profile, and emulator artifacts relative to the baseline;
6. use the repository wrapper, pinned toolchain, documented tasks, and exact affected module/variant. Do not guess `assembleDebug` or another task when flavors, build types, custom plugins, or CI define the qualifying target. Do not supply signing or production secrets just to make a review build pass;
7. after every build/test, reverify the same `HEAD`, unchanged index/tracked tree, expected submodule commits, and only understood session-generated inputs. Unexpected local mutation means the run does not qualify as exact-SHA evidence;
8. record exact SHA, wrapper/Gradle/JDK/AGP and relevant Kotlin versions when material, modules, tasks, build type/flavor/variant, test scope, device/emulator/API level/ABI when applicable, command/job, exit status, generated-input provenance, and result; and
9. refresh the provider target after validation and immediately before any post.

Building the developer's default branch, another branch, or an earlier commit never qualifies. When validating a provider merge result, prove that it contains the recorded head and current base; use a separate clean detached checkout or authoritative CI rather than mutating the head checkout.

If any base, head, merge-base, or provider merge-result identity changes, reassess every affected diff, anchor, check, and validation claim. A changed head always requires a fresh detached environment; mark prior evidence stale for the new live target, clean up the old review-owned environment when safe, and never reset the old worktree or transplant its score or inline anchors. If only the base or merge result changes, recompute the target and rerun every affected merge-result validation before claiming current coverage.

Worktree and Gradle-home separation protect checkout/cache state, not secrets or code execution. Forks and executable-infrastructure changes still require the trust gate and may require secrets-free CI instead of local execution. Do not copy ignored credentials, signing material, `local.properties`, service-account files, or production configuration into a temporary checkout automatically. Do not publish a Gradle Build Scan or upload diagnostic artifacts without separate authorization.

## Cleanup

Track which clone, worktree, Gradle home/daemon, build/report/result, emulator/device, generated, and temporary paths and processes this review created. Record the initial clean baseline and each session-created disposable path. Clean them after successful, failed, or aborted review work once evidence/reporting is preserved and any authorized submission attempt is resolved:

1. wait for or stop only processes and Gradle daemons started by the review; never terminate by a broad process name. Recheck for foreign processes using a target, daemon, emulator, or device: do not stop them or remove that target—retain and report it;
2. change to a neutral directory outside every removal target, then canonicalize and confirm each target is the exact recorded review-owned path, not the user's checkout, repository root, home directory, shared Gradle cache, arbitrary starting folder, or reused/pre-existing worktree;
3. confirm the checkout still points at the expected SHA and compare tracked, untracked, ignored, generated, and submodule state with the recorded baseline. Delete only exact disposable paths created by this session and absent at baseline; never use `git clean`, `reset`, or `stash` to manufacture cleanliness. Preserve and report anything unexplained;
4. for a clean linked worktree, run `git worktree remove -- <exact-path>` without force. For a standalone State 1 clone, remove only the canonical session root after the same ownership, process, identity, and dirty-state checks; never delete the user's originally empty folder. Then remove only explicitly recorded review-owned Gradle/build/temp paths;
5. reverse only review-owned, documented emulator/device state when safe—for example uninstalling a review-installed test package—without wiping, resetting, or altering a shared device; and
6. verify a linked worktree is no longer registered, session-owned artifacts are gone, and the ordinary checkout's recorded branch/SHA/status are unchanged. Report cleanup success in the user handoff, or report the retained path and reason when cleanup is unsafe or fails; keep local paths out of the provider review unless needed to explain evidence.

Never remove a reused checkout or pre-existing worktree. Never force-remove a dirty worktree. If the user asks to keep the environment, retain it and report its exact path. A dirty/unexpected worktree is a cleanup warning, not permission to discard files.
