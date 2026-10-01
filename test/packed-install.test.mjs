import assert from "node:assert/strict";
import { access, mkdir, mkdtemp, readFile, readdir, rm } from "node:fs/promises";
import { tmpdir } from "node:os";
import { dirname, join, resolve } from "node:path";
import { spawnSync } from "node:child_process";
import test from "node:test";
import { fileURLToPath } from "node:url";

const ROOT = resolve(fileURLToPath(new URL("..", import.meta.url)));
const NPM = process.platform === "win32" ? "npm.cmd" : "npm";

function runNpm(args, cwd) {
  return spawnSync(NPM, args, { cwd, encoding: "utf8" });
}

async function markdownFiles(directory) {
  const output = [];
  for (const entry of await readdir(directory, { withFileTypes: true })) {
    const path = join(directory, entry.name);
    if (entry.isDirectory()) output.push(...(await markdownFiles(path)));
    else if (entry.name.endsWith(".md")) output.push(path);
  }
  return output;
}

async function brokenRelativeMarkdownLinks(directory) {
  const failures = [];
  for (const file of await markdownFiles(directory)) {
    const source = await readFile(file, "utf8");
    for (const match of source.matchAll(/\[[^\]]*\]\(([^)]+)\)/g)) {
      const raw = match[1].trim().split(/\s+['\"]/)[0];
      if (!raw || raw.startsWith("#") || /^[a-z][a-z0-9+.-]*:/i.test(raw)) continue;
      const path = resolve(dirname(file), decodeURIComponent(raw.split("#")[0]));
      try {
        await access(path);
      } catch {
        failures.push(`${file.slice(directory.length + 1)} -> ${raw}`);
      }
    }
  }
  return failures;
}

test("the packed artifact installs a complete self-contained skill", async () => {
  const workspace = await mkdtemp(join(tmpdir(), "android-ultrareview-packed-test-"));
  const packDirectory = join(workspace, "pack");
  const project = join(workspace, "project");
  const cache = join(workspace, "npm-cache");
  await mkdir(packDirectory);
  await mkdir(project);

  try {
    const packed = runNpm(
      ["pack", "--json", "--pack-destination", packDirectory, "--cache", cache],
      ROOT,
    );
    assert.equal(packed.status, 0, packed.stderr);
    const [{ filename }] = JSON.parse(packed.stdout);
    const tarball = join(packDirectory, filename);

    const installed = runNpm(
      [
        "exec",
        "--yes",
        "--cache",
        cache,
        "--package",
        tarball,
        "--",
        "android-ultrareview",
        "--agent",
        "codex",
        "--scope",
        "project",
        "--project",
        project,
      ],
      ROOT,
    );
    assert.equal(installed.status, 0, installed.stderr);

    const destination = join(project, ".agents", "skills", "android-ultrareview");
    await access(join(destination, "SKILL.md"));
    await access(join(destination, "LICENSE"));
    assert.deepEqual(await brokenRelativeMarkdownLinks(destination), []);
  } finally {
    await rm(workspace, { recursive: true, force: true });
  }
});
