import assert from "node:assert/strict";
import { access, readFile, readdir, stat } from "node:fs/promises";
import { dirname, join, resolve } from "node:path";
import test from "node:test";
import { fileURLToPath } from "node:url";

const ROOT = resolve(fileURLToPath(new URL("..", import.meta.url)));

async function markdownFiles(directory) {
  const output = [];
  for (const entry of await readdir(directory, { withFileTypes: true })) {
    if ([".git", ".tmp", "node_modules"].includes(entry.name)) continue;
    const path = join(directory, entry.name);
    if (entry.isDirectory()) output.push(...(await markdownFiles(path)));
    else if (entry.name.endsWith(".md")) output.push(path);
  }
  return output;
}

test("SKILL.md has portable required frontmatter", async () => {
  const source = await readFile(join(ROOT, "SKILL.md"), "utf8");
  const frontmatter = source.match(/^---\n([\s\S]*?)\n---\n/);
  assert.ok(frontmatter, "SKILL.md must begin with YAML frontmatter");
  assert.match(frontmatter[1], /^name: android-ultrareview$/m);
  assert.match(frontmatter[1], /^description: .+$/m);
});

test("package metadata points to the executable installer", async () => {
  const packageJson = JSON.parse(await readFile(join(ROOT, "package.json"), "utf8"));
  assert.equal(packageJson.type, "module");
  assert.equal(packageJson.bin["android-ultrareview"], "bin/install.mjs");
  assert.match(await readFile(join(ROOT, "bin", "install.mjs"), "utf8"), /^#!\/usr\/bin\/env node/);
});

test("all relative Markdown links resolve", async () => {
  const failures = [];
  for (const file of await markdownFiles(ROOT)) {
    const source = await readFile(file, "utf8");
    const links = source.matchAll(/\[[^\]]*\]\(([^)]+)\)/g);
    for (const match of links) {
      const raw = match[1].trim().split(/\s+['\"]/)[0];
      if (!raw || raw.startsWith("#") || /^[a-z][a-z0-9+.-]*:/i.test(raw)) continue;
      const path = resolve(dirname(file), decodeURIComponent(raw.split("#")[0]));
      try {
        await access(path);
      } catch {
        failures.push(`${file.slice(ROOT.length + 1)} -> ${raw}`);
      }
    }
  }
  assert.deepEqual(failures, []);
});

test("benchmark manifest pins three fixtures and nine Git commits", async () => {
  const manifest = JSON.parse(
    await readFile(join(ROOT, "evals", "fixtures", "manifest.json"), "utf8"),
  );
  const fixtures = Object.values(manifest.fixtures);
  assert.equal(fixtures.length, 3);
  assert.equal(fixtures.flatMap((fixture) => Object.values(fixture.expected_shas)).length, 9);
  for (const fixture of fixtures) {
    for (const sha of Object.values(fixture.expected_shas)) assert.match(sha, /^[0-9a-f]{40}$/);
  }
});

test("required public project files exist", async () => {
  const required = [
    "README.md",
    "CONTRIBUTING.md",
    "SECURITY.md",
    "CODE_OF_CONDUCT.md",
    "CHANGELOG.md",
    ".github/workflows/ci.yml",
  ];
  for (const path of required) assert.equal((await stat(join(ROOT, path))).isFile(), true, path);
});
