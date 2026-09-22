# Security policy

## Supported versions

Security fixes are applied to the latest release and the default branch.

## Report a vulnerability

Do not open a public issue for a vulnerability that could expose credentials, execute untrusted code unexpectedly, overwrite user files, or weaken the review skill's authorization boundaries.

Use GitHub's private vulnerability reporting feature on this repository. Include:

- the affected version or commit;
- reproduction steps;
- the expected and actual behavior;
- impact and prerequisites; and
- a suggested mitigation, if available.

Please avoid accessing data that is not yours, disrupting third-party systems, or publishing details before a fix is available. The maintainer will acknowledge a complete report as soon as practical and coordinate disclosure based on severity and fix readiness.

## Installer trust model

The `npx` command executes `bin/install.mjs` from the selected Git revision. Pin a release tag or commit for reproducible installation, inspect the installer before use, and rely on `--dry-run` when evaluating destinations. The installer does not change shell configuration and refuses to replace an existing installation unless `--force` is supplied.
