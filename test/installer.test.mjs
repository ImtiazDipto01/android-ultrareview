import assert from "node:assert/strict";
import { mkdtemp, readdir, stat, writeFile } from "node:fs/promises";
import { tmpdir } from "node:os";
import { join, resolve } from "node:path";
import { spawnSync } from "node:child_process";
import test from "node:test";
import { fileURLToPath } from "node:url";

const ROOT = resolve(fileURLToPath(new URL("..", import.meta.url)));
const INSTALLER = join(ROOT, "bin", "install.mjs");

async function temporaryHome() {
  return mkdtemp(join(tmpdir(), "android-ultrareview-test-"));
}

function run(args, home, cwd = ROOT) {
  return spawnSync(process.execPath, [INSTALLER, ...args], {
    cwd,
    env: { ...process.env, HOME: home, USERPROFILE: home },
    encoding: "utf8",
  });
}

async function isFile(path) {
  return (await stat(path)).isFile();
}

test("requires an explicit agent", async () => {
  const home = await temporaryHome();
  const result = run([], home);
  assert.equal(result.status, 1);
  assert.match(result.stderr, /--agent is required/);
});

test("installs Codex globally in the Agent Skills standard directory", async () => {
  const home = await temporaryHome();
  const result = run(["--agent", "codex"], home);
  assert.equal(result.status, 0, result.stderr);
  assert.equal(await isFile(join(home, ".agents", "skills", "android-ultrareview", "SKILL.md")), true);
});

test("installs Claude Code globally", async () => {
  const home = await temporaryHome();
  const result = run(["--agent", "claude"], home);
  assert.equal(result.status, 0, result.stderr);
  assert.equal(await isFile(join(home, ".claude", "skills", "android-ultrareview", "SKILL.md")), true);
});

test("installs Cursor into one project", async () => {
  const home = await temporaryHome();
  const project = join(home, "project");
  await import("node:fs/promises").then(({ mkdir }) => mkdir(project));
  const result = run(["--agent", "cursor", "--scope", "project", "--project", project], home);
  assert.equal(result.status, 0, result.stderr);
  assert.equal(await isFile(join(project, ".cursor", "skills", "android-ultrareview", "SKILL.md")), true);
});

test("all installs one shared Codex/Cursor copy plus one Claude copy", async () => {
  const home = await temporaryHome();
  const result = run(["--agent", "all"], home);
  assert.equal(result.status, 0, result.stderr);
  assert.equal(await isFile(join(home, ".agents", "skills", "android-ultrareview", "SKILL.md")), true);
  assert.equal(await isFile(join(home, ".claude", "skills", "android-ultrareview", "SKILL.md")), true);
  await assert.rejects(stat(join(home, ".cursor", "skills", "android-ultrareview")), { code: "ENOENT" });
});

test("refuses overwrite by default and preserves a backup with --force", async () => {
  const home = await temporaryHome();
  const destination = join(home, ".agents", "skills", "android-ultrareview");
  assert.equal(run(["--agent", "codex"], home).status, 0);
  await writeFile(join(destination, "owner-note.txt"), "keep me\n");

  const refused = run(["--agent", "codex"], home);
  assert.equal(refused.status, 1);
  assert.match(refused.stderr, /already exists/);

  const replaced = run(["--agent", "codex", "--force"], home);
  assert.equal(replaced.status, 0, replaced.stderr);
  const siblings = await readdir(join(home, ".agents", "skills"));
  const backup = siblings.find((name) => name.startsWith(".android-ultrareview.backup-"));
  assert.ok(backup);
  assert.equal(await isFile(join(home, ".agents", "skills", backup, "owner-note.txt")), true);
});

test("dry-run writes nothing", async () => {
  const home = await temporaryHome();
  const result = run(["--agent", "cursor", "--dry-run"], home);
  assert.equal(result.status, 0, result.stderr);
  assert.match(result.stdout, /\[dry-run\]/);
  await assert.rejects(stat(join(home, ".cursor")), { code: "ENOENT" });
});
