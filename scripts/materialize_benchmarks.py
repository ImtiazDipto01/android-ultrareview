#!/usr/bin/env python3
"""Materialize deterministic Android UltraReview Git fixtures without executing them."""

from __future__ import annotations

import argparse
import json
import os
from pathlib import Path
import shutil
import subprocess
import sys


SKILL_ROOT = Path(__file__).resolve().parents[1]
FIXTURES_ROOT = SKILL_ROOT / "evals" / "fixtures"
MANIFEST_PATH = FIXTURES_ROOT / "manifest.json"


def run_git(repo: Path, *args: str, env: dict[str, str] | None = None) -> str:
    command = ["git", "-C", str(repo), *args]
    result = subprocess.run(
        command,
        check=False,
        capture_output=True,
        text=True,
        env=env,
    )
    if result.returncode != 0:
        raise RuntimeError(
            f"git command failed ({result.returncode}): {' '.join(command)}\n{result.stderr.strip()}"
        )
    return result.stdout.strip()


def overlay(source: Path, destination: Path) -> None:
    if not source.is_dir():
        raise RuntimeError(f"missing fixture stage: {source}")
    shutil.copytree(source, destination, dirs_exist_ok=True)


def commit(repo: Path, message: str, date: str, identity: dict[str, str]) -> str:
    run_git(repo, "add", "-A")
    commit_env = os.environ.copy()
    commit_env.update(
        {
            "GIT_AUTHOR_NAME": identity["name"],
            "GIT_AUTHOR_EMAIL": identity["email"],
            "GIT_COMMITTER_NAME": identity["name"],
            "GIT_COMMITTER_EMAIL": identity["email"],
            "GIT_AUTHOR_DATE": date,
            "GIT_COMMITTER_DATE": date,
            "LC_ALL": "C",
            "TZ": "UTC",
        }
    )
    run_git(repo, "commit", "--no-gpg-sign", "-m", message, env=commit_env)
    return run_git(repo, "rev-parse", "HEAD")


def materialize_fixture(
    name: str,
    destination: Path,
    identity: dict[str, str],
) -> dict[str, str]:
    fixture_source = FIXTURES_ROOT / name
    repo = destination / name
    repo.mkdir(parents=True)
    overlay(FIXTURES_ROOT / "common", repo)
    overlay(fixture_source / "base", repo)

    subprocess.run(
        ["git", "init", "--initial-branch=main", str(repo)],
        check=True,
        capture_output=True,
        text=True,
    )
    run_git(repo, "config", "core.autocrlf", "false")
    run_git(repo, "config", "commit.gpgsign", "false")

    shas: dict[str, str] = {}
    shas["base"] = commit(repo, "fixture: base", identity["base_date"], identity)
    overlay(fixture_source / "defective", repo)
    shas["defective"] = commit(
        repo, "fixture: defective pull request", identity["defective_date"], identity
    )
    overlay(fixture_source / "repaired", repo)
    shas["repaired"] = commit(
        repo, "fixture: repaired pull request", identity["repaired_date"], identity
    )

    for state, sha in shas.items():
        run_git(repo, "tag", state, sha)
    run_git(repo, "checkout", "--detach", shas["repaired"])
    return shas


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--output",
        required=True,
        type=Path,
        help="New or empty directory that will receive materialized repositories.",
    )
    parser.add_argument(
        "--allow-unpinned",
        action="store_true",
        help="Skip expected-SHA comparison while intentionally repinning fixtures.",
    )
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    output = args.output.resolve()
    if output.exists() and any(output.iterdir()):
        raise RuntimeError(f"refusing to use non-empty output directory: {output}")
    output.mkdir(parents=True, exist_ok=True)

    manifest = json.loads(MANIFEST_PATH.read_text(encoding="utf-8"))
    identity = manifest["commit_identity"]
    resolved: dict[str, object] = {"format_version": manifest["format_version"], "fixtures": {}}
    mismatches: list[str] = []

    for name in sorted(manifest["fixtures"]):
        spec = manifest["fixtures"][name]
        shas = materialize_fixture(name, output, identity)
        resolved["fixtures"][name] = {
            "repository": str((output / name).resolve()),
            "request": str((FIXTURES_ROOT / name / "request.md").resolve()),
            "fixture_main_class": spec["fixture_main_class"],
            "shas": shas,
        }
        for state, actual in shas.items():
            expected = spec["expected_shas"][state]
            if args.allow_unpinned:
                continue
            if actual != expected:
                mismatches.append(f"{name}.{state}: expected {expected}, got {actual}")

    resolved_path = output / "resolved-manifest.json"
    resolved_path.write_text(json.dumps(resolved, indent=2) + "\n", encoding="utf-8")
    if mismatches:
        raise RuntimeError("fixture SHA verification failed:\n" + "\n".join(mismatches))

    print(f"fixture integrity: PASS ({len(resolved['fixtures'])} repositories, 9 pinned commits)")
    print(resolved_path)
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, RuntimeError, subprocess.SubprocessError) as error:
        print(f"fixture integrity: FAIL — {error}", file=sys.stderr)
        raise SystemExit(1)
