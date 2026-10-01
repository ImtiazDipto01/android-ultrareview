#!/usr/bin/env node

import { cp, mkdir, rename, rm, stat } from "node:fs/promises";
import { homedir } from "node:os";
import { dirname, join, resolve } from "node:path";
import { fileURLToPath } from "node:url";

const SKILL_NAME = "android-ultrareview";
const PACKAGE_ROOT = resolve(dirname(fileURLToPath(import.meta.url)), "..");
const SKILL_ENTRIES = [
  "SKILL.md",
  "LICENSE",
  "agents",
  "evals",
  "references",
  "scripts",
];

const AGENTS = new Set(["codex", "claude", "cursor", "all"]);
const SCOPES = new Set(["user", "project"]);

function printHelp() {
  console.log(`Android UltraReview installer

Usage:
  npx github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent <agent> [options]

Required:
  --agent <codex|claude|cursor|all>

Options:
  --scope <user|project>   Install for the current user or one project (default: user)
  --project <path>        Project root for project scope (default: current directory)
  --force                 Replace an existing installation and preserve a backup
  --dry-run               Print destinations without writing files
  -h, --help              Show this help

Examples:
  npx github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent codex
  npx github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent claude --scope project
  npx github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent cursor --scope project --project ./my-app
  npx github:ImtiazDipto01/android-ultrareview#v1.0.0 --agent all --force
`);
}

function fail(message) {
  console.error(`Error: ${message}`);
  process.exitCode = 1;
}

function parseArgs(argv) {
  const options = {
    agent: undefined,
    scope: "user",
    project: process.cwd(),
    force: false,
    dryRun: false,
    help: false,
  };

  for (let index = 0; index < argv.length; index += 1) {
    const argument = argv[index];
    if (argument === "-h" || argument === "--help") {
      options.help = true;
    } else if (argument === "--") {
      // Tolerate an npm-style separator even though npx does not require it here.
      continue;
    } else if (argument === "--force") {
      options.force = true;
    } else if (argument === "--dry-run") {
      options.dryRun = true;
    } else if (argument === "--agent" || argument === "--scope" || argument === "--project") {
      const value = argv[index + 1];
      if (!value || value.startsWith("--")) {
        throw new Error(`${argument} requires a value`);
      }
      options[argument.slice(2)] = value;
      index += 1;
    } else {
      throw new Error(`unknown option: ${argument}`);
    }
  }

  return options;
}

function userHome() {
  return process.env.HOME || process.env.USERPROFILE || homedir();
}

function rootsFor(agent, scope, project) {
  const base = scope === "user" ? userHome() : resolve(project);
  const roots = {
    codex: join(base, ".agents", "skills"),
    claude: join(base, ".claude", "skills"),
    cursor: join(base, ".cursor", "skills"),
  };

  if (agent === "all") {
    // Cursor discovers .agents/skills, so a second Cursor copy would be a duplicate.
    return [roots.codex, roots.claude];
  }
  return [roots[agent]];
}

async function exists(path) {
  try {
    await stat(path);
    return true;
  } catch (error) {
    if (error.code === "ENOENT") return false;
    throw error;
  }
}

async function assertDirectory(path, label) {
  let info;
  try {
    info = await stat(path);
  } catch (error) {
    if (error.code === "ENOENT") {
      throw new Error(`${label} does not exist: ${path}`);
    }
    throw error;
  }
  if (!info.isDirectory()) {
    throw new Error(`${label} is not a directory: ${path}`);
  }
}

async function copySkill(destination, { force, dryRun }) {
  const parent = dirname(destination);
  const temporary = join(parent, `.${SKILL_NAME}.tmp-${process.pid}-${Date.now()}`);
  const backup = join(parent, `.${SKILL_NAME}.backup-${new Date().toISOString().replaceAll(":", "-")}`);
  const destinationExists = await exists(destination);

  if (destinationExists && !force) {
    throw new Error(
      `installation already exists at ${destination}; rerun with --force to replace it and keep a backup`,
    );
  }

  if (dryRun) {
    console.log(`[dry-run] install ${SKILL_NAME} -> ${destination}`);
    if (destinationExists && force) console.log(`[dry-run] preserve existing installation -> ${backup}`);
    return;
  }

  await mkdir(parent, { recursive: true });
  await rm(temporary, { recursive: true, force: true });
  await mkdir(temporary);

  try {
    for (const entry of SKILL_ENTRIES) {
      await cp(join(PACKAGE_ROOT, entry), join(temporary, entry), {
        recursive: true,
        errorOnExist: true,
        force: false,
      });
    }

    if (destinationExists) await rename(destination, backup);
    try {
      await rename(temporary, destination);
    } catch (error) {
      if (destinationExists && !(await exists(destination)) && (await exists(backup))) {
        await rename(backup, destination);
      }
      throw error;
    }
  } finally {
    await rm(temporary, { recursive: true, force: true });
  }

  console.log(`Installed ${SKILL_NAME} -> ${destination}`);
  if (destinationExists) console.log(`Previous installation preserved -> ${backup}`);
}

async function preflightDestinations(destinations, { force }) {
  for (const destination of destinations) {
    if ((await exists(destination)) && !force) {
      throw new Error(
        `installation already exists at ${destination}; rerun with --force to replace it and keep a backup`,
      );
    }
  }
}

async function main() {
  let options;
  try {
    options = parseArgs(process.argv.slice(2));
  } catch (error) {
    fail(error.message);
    printHelp();
    return;
  }

  if (options.help) {
    printHelp();
    return;
  }
  if (!options.agent) {
    fail("--agent is required; choose codex, claude, cursor, or all");
    printHelp();
    return;
  }
  if (!AGENTS.has(options.agent)) {
    fail(`unsupported agent '${options.agent}'`);
    return;
  }
  if (!SCOPES.has(options.scope)) {
    fail(`unsupported scope '${options.scope}'; choose user or project`);
    return;
  }

  try {
    await assertDirectory(PACKAGE_ROOT, "package root");
    if (options.scope === "project") await assertDirectory(resolve(options.project), "project root");

    const roots = rootsFor(options.agent, options.scope, options.project);
    const destinations = roots.map((root) => join(root, SKILL_NAME));
    await preflightDestinations(destinations, options);
    for (const destination of destinations) await copySkill(destination, options);

    if (!options.dryRun) {
      console.log("Restart the agent if the skill does not appear immediately.");
    }
  } catch (error) {
    fail(error.message);
  }
}

await main();
